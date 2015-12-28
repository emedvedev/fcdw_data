/*
	This file is part of the FUNcube Data Warehouse

	Copyright 2013,2014 (c) David A.Johnson, G4DPZ, AMSAT-UK

    The FUNcube Data Warehouse is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 2 of the License, or
    (at your option) any later version.

    The FUNcube Data Warehouse is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with The FUNcube Data Warehouse.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.org.funcube.fcdw.server.processor;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.org.funcube.fcdw.dao.DTMFCommandDao;
import uk.org.funcube.fcdw.dao.EpochDao;
import uk.org.funcube.fcdw.dao.FrameTypeFortyDao;
import uk.org.funcube.fcdw.dao.HexFrameDao;
import uk.org.funcube.fcdw.dao.MinMaxDao;
import uk.org.funcube.fcdw.dao.RealTimeDao;
import uk.org.funcube.fcdw.dao.SatelliteStatusDao;
import uk.org.funcube.fcdw.dao.UserDao;
import uk.org.funcube.fcdw.dao.UserRankingDao;
import uk.org.funcube.fcdw.domain.DTMFCommandEntity;
import uk.org.funcube.fcdw.domain.EpochEntity;
import uk.org.funcube.fcdw.domain.FrameTypeFortyEntity;
import uk.org.funcube.fcdw.domain.HexFrameEntity;
import uk.org.funcube.fcdw.domain.MinMaxEntity;
import uk.org.funcube.fcdw.domain.RealTimeEntity;
import uk.org.funcube.fcdw.domain.SatelliteStatusEntity;
import uk.org.funcube.fcdw.domain.UserEntity;
import uk.org.funcube.fcdw.domain.UserRankingEntity;
import uk.org.funcube.fcdw.satellite.GroundStationPosition;
import uk.org.funcube.fcdw.server.shared.RealTime;
import uk.org.funcube.fcdw.server.shared.RealTimeFC2;
import uk.org.funcube.fcdw.server.shared.RealTimeNayif1;
import uk.org.funcube.fcdw.server.shared.SatellitePosition;
import uk.org.funcube.fcdw.server.util.Cache;
import uk.org.funcube.fcdw.server.util.Clock;
import uk.org.funcube.fcdw.server.util.UTCClock;
import uk.org.funcube.fcdw.service.MailService;
import uk.org.funcube.fcdw.service.PredictorService;

@Service
@RequestMapping("/api/data/hex")
public class DataProcessor {

    private final Lock lock = new ReentrantLock();

    static final long TWO_DAYS_SEQ_COUNT = 1440;

    @Autowired
    UserDao userDao;

    @Autowired
    HexFrameDao hexFrameDao;

    @Autowired
    RealTimeDao realTimeDao;

    @Autowired
    MinMaxDao minMaxDao;

    @Autowired
    Clock clock;

    @Autowired
    EpochDao epochDao;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    @Autowired
    UserRankingDao userRankingDao;

    @Autowired
    PredictorService predictor;

    @Autowired
    DTMFCommandDao dtmfCommandDao;

    @Autowired
    FrameTypeFortyDao frameTypeFortyDao;

    @Autowired
    MailService mailService;

    private static Logger LOG = Logger.getLogger(DataProcessor.class.getName());

    public DataProcessor() {
        super();
    }

    public DataProcessor(EpochDao epochDaoMock) {
        epochDao = epochDaoMock;
    }

    @RequestMapping(value = "/{siteId}/", method = RequestMethod.POST)
    public ResponseEntity<String> uploadData(@PathVariable final String siteId,
            @RequestParam("digest") final String digest,
            @RequestBody final String body) {

        lock.lock();

        try {
            return processUpload(siteId, digest, body);
        }
        finally {
            lock.unlock();
        }

    }

    @Transactional(readOnly = false)
    private ResponseEntity<String> processUpload(final String siteId, final String digest,
            final String body) {
        // get the user from the repository
        final List<UserEntity> users = userDao.findBySiteId(siteId);

        if (users.size() != 0) {

            String hexString = StringUtils.substringBetween(body, "=", "&");
            hexString = hexString.replace("+", " ");

            String authKey = userAuthKeys.get(siteId);
            final UserEntity user = users.get(0);

            try {

                if (authKey == null) {
                    if (user != null) {
                        authKey = user.getAuthKey();
                        userAuthKeys.put(siteId, authKey);
                    }
                    else {
                        LOG.error(USER_WITH_SITE_ID + siteId + NOT_FOUND);
                        return new ResponseEntity<String>("UNAUTHORIZED",
                                HttpStatus.UNAUTHORIZED);
                    }
                }

                final String calculatedDigest = DataProcessor.calculateDigest(hexString,
                        authKey, null);
                final String calculatedDigestUTF8 = DataProcessor.calculateDigest(hexString,
                        authKey, Integer.valueOf(8));
                final String calculatedDigestUTF16 = DataProcessor.calculateDigest(hexString,
                        authKey, Integer.valueOf(16));

                if (null != digest
                        && (digest.equals(calculatedDigest)
                                || digest.equals(calculatedDigestUTF8) || digest
                                    .equals(calculatedDigestUTF16))) {

                    hexString = StringUtils.deleteWhitespace(hexString);

                    final Date now = new Date(5000 * (clock.currentDate().getTime() / 5000));

                    return processHexFrame(new UserHexString(user,
                            StringUtils.deleteWhitespace(hexString), now));
                }
                else {
                    LOG.error(USER_WITH_SITE_ID + siteId + HAD_INCORRECT_DIGEST
                            + ", received: " + digest + ", calculated: "
                            + calculatedDigest);
                    return new ResponseEntity<String>("UNAUTHORIZED",
                            HttpStatus.UNAUTHORIZED);
                }
            }
            catch (final Exception e) {
                LOG.error(e.getMessage());
                return new ResponseEntity<String>(e.getMessage(),
                        HttpStatus.BAD_REQUEST);
            }

        }
        else {
            LOG.error("Site id: " + siteId + " not found in database");
            return new ResponseEntity<String>("UNAUTHORIZED",
                    HttpStatus.UNAUTHORIZED);
        }
    }

    /** We make this protected so that the test framework can get hold of it. */
    protected ResponseEntity<String> processHexFrame(final UserHexString userHexString) {

        final Map<Long, EpochEntity> epochMap = new HashMap<Long, EpochEntity>();

        final Iterator<EpochEntity> iterator = epochDao.findAll().iterator();

        while (iterator.hasNext()) {
            final EpochEntity epoch = iterator.next();
            epochMap.put(epoch.getSatelliteId(), epoch);
        }

        final String hexString = userHexString.getHexString();
        final UserEntity user = userHexString.getUser();
        final Date createdDate = userHexString.getCreatedDate();
        final String digest = generateDigest(hexString);
        

        final int frameId = Integer.parseInt(hexString.substring(0, 2), 16);

        int frameType = frameId & 63;
        int satelliteId = (frameId & 128 + 64) >> 6;
        
        // we now need to look elsewhere if the satellite ID == 3
        if (satelliteId == 3) {
            final int idTemp = Integer.parseInt(hexString.substring(2, 4), 16);
            satelliteId = idTemp & 252;
            final int ftTemp = idTemp & 3;
            frameType = frameType + (ftTemp << 7);
        }
        
        final int sensorId = frameId % 2;
        
        final String binaryString = DataProcessor.convertHexBytePairToBinary(hexString
                .substring((satelliteId < 3) ? 2 : 4, hexString.length()));

        RealTime realTime;

        switch(satelliteId) {
            case 8: 
                /* nayif-1 */
                realTime = new RealTimeNayif1(satelliteId, frameType, createdDate,
                        binaryString);
                break;
            case 1:
                realTime = new RealTimeFC2(satelliteId, frameType, sensorId, createdDate,
                        binaryString);
                break;
            default:
                realTime = new RealTime(satelliteId, frameType, sensorId, createdDate,
                        binaryString);
                break;
        }

        final long sequenceNumber = realTime.getSequenceNumber();

        if (sequenceNumber != -1) {

            if (satelliteId != 1 && satelliteId != 8) {

                final Long maxSequenceNumber = hexFrameDao
                        .getMaxSequenceNumber(satelliteId);

                if (maxSequenceNumber != null
                        && Math.abs(maxSequenceNumber - sequenceNumber) > TWO_DAYS_SEQ_COUNT) {
                    final String message = String
                            .format("User %s loading sequence number %d is out of bounds for satelliteId %d",
                                    user.getSiteId(), sequenceNumber,
                                    satelliteId);
                    LOG.error(message);

                    return new ResponseEntity<String>("OK", HttpStatus.OK);
                }

            }

            final List<HexFrameEntity> frames = hexFrameDao
                    .findBySatelliteIdAndSequenceNumberAndFrameType(
                            satelliteId, sequenceNumber, frameType);

            if (frames.size() > 0 && satelliteId == 1 && frameType == 40) {
                return processFrameTypeForty(hexString, createdDate, sequenceNumber);
            }
            else {
                return saveUpdateHexFrame(hexString, user, frameType, satelliteId,
                        createdDate, realTime, sequenceNumber, frames,
                        epochMap.get(Long.valueOf(satelliteId)), digest);
            }
        }

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    private void sendNoShowEmail(final Timestamp lastUpdated) {
        final Map<String, Object> emailTags = new HashMap<String, Object>();
        emailTags.put("satelliteName", "FUNcube 1");
        emailTags.put("lastUpdated", lastUpdated);
        mailService.sendUsingTemplate("operations@funcube.org.uk", emailTags,
                "noshow");
    }

    private ResponseEntity<String> processFrameTypeForty(final String payload, final Date now,
            final long sequenceNumber) {

        frameTypeFortyDao.save(new FrameTypeFortyEntity(sequenceNumber, now,
                payload));

        return new ResponseEntity<String>("OK",
                HttpStatus.OK);

    }

    private ResponseEntity<String> saveUpdateHexFrame(final String hexString,
            final UserEntity user, final int frameType, final int satelliteId,
            final Date now, final RealTime realTime, final long sequenceNumber,
            final List<HexFrameEntity> frames, final EpochEntity epoch,
            final String digest) {
        HexFrameEntity hexFrame;
        Set<UserEntity> users;

        if (frames.size() == 0) {

            Timestamp satelliteTime;

            if (epoch == null || satelliteId == 1) {
                satelliteTime = new Timestamp(now.getTime());
            }
            else {
                satelliteTime = new Timestamp(
                        epoch.getReferenceTime().getTime()
                                + (sequenceNumber - epoch.getSequenceNumber()) * 2 * 60 * 1000
                                + frameType * 5 * 1000);
            }

            final long[] catalogNumbers = new long[] {39444, 40074, 39444, 39444, 39444, 40074, 39444, 39444, 39444};

            boolean outOfOrder = false;

            final List<HexFrameEntity> existingFrames = hexFrameDao
                    .findBySatelliteIdAndSequenceNumber(satelliteId,
                            sequenceNumber);

            if (!existingFrames.isEmpty()) {
                for (final HexFrameEntity existingFrame : existingFrames) {
                    if (existingFrame.getCreatedDate().before(now)
                            && existingFrame.getFrameType() > frameType) {
                        outOfOrder = false;
                        break;
                    }
                }
            }

            hexFrame = new HexFrameEntity((long)satelliteId, (long)frameType,
                    sequenceNumber, hexString, now, true, satelliteTime);

            hexFrame.setOutOfOrder(outOfOrder);

            final GroundStationPosition groundStationPosition = new GroundStationPosition(
                    Double.parseDouble(user.getLatitude()),
                    Double.parseDouble(user.getLongitude()), 100.0);

            final SatellitePosition satellitePosition = predictor.get(
                    catalogNumbers[satelliteId], now, groundStationPosition);

            if (satellitePosition != null && !satellitePosition.isAboveHorizon()) {
                LOG.error("User [" + user.getSiteId()
                        + "] is out of range of satellite[" + satelliteId
                        + "]!!!");
            }

            if (!outOfOrder) {

                if (satellitePosition != null) {
                    hexFrame.setEclipsed(satellitePosition.getEclipsed());
                    hexFrame.setEclipseDepth(satellitePosition
                            .getEclipseDepth());
                    latitude = satellitePosition.getLatitude();
                    hexFrame.setLatitude(latitude);
                    longitude = satellitePosition.getLongitude();
                    hexFrame.setLongitude(longitude);
                }

                final SatelliteStatusEntity satelliteStatus = satelliteStatusDao
                        .findBySatelliteId(satelliteId).get(0);
                satelliteStatus.setSequenceNumber(realTime.getSequenceNumber());
                satelliteStatus.setLastUpdated(new Timestamp(now.getTime()));
                satelliteStatus.setEclipsed(realTime.isEclipsed());

                if (satellitePosition != null) {
                    satelliteStatus.setEclipseDepth(Double
                            .parseDouble(satellitePosition.getEclipseDepth()));
                }

                satelliteStatusDao.save(satelliteStatus);
            }

            hexFrame.getUsers().add(user);

            RealTimeEntity realTimeEntity;

            if (realTime instanceof RealTimeFC2) {
                realTimeEntity = new RealTimeEntity((RealTimeFC2)realTime,
                        satelliteTime);
                realTimeEntity.setSatelliteName("FC2");
            }
            else if (realTime instanceof RealTimeNayif1) {
                realTimeEntity = new RealTimeEntity((RealTimeNayif1)realTime,
                        satelliteTime);
                realTimeEntity.setSatelliteName("Nayif-1");
            } 
            else {
                realTimeEntity = new RealTimeEntity(realTime, satelliteTime);
                realTimeEntity.setSatelliteName("FC1");
            }

            if (!outOfOrder) {
                final Long dtmfId = dtmfCommandDao.getMaxId(satelliteId);

                if (dtmfId != null) {
                    final List<DTMFCommandEntity> commandEntities = dtmfCommandDao
                            .findById(dtmfId);
                    if (!commandEntities.isEmpty()) {
                        final DTMFCommandEntity lastCommand = commandEntities.get(0);
                        final Long dtmfValue = realTimeEntity.getLastCommand();
                        if (dtmfValue.longValue() != lastCommand.getValue()
                                .longValue()) {
                            final DTMFCommandEntity newCommand = new DTMFCommandEntity(
                                    (long)satelliteId, sequenceNumber,
                                    (long)frameType, new Timestamp(
                                            now.getTime()), latitude,
                                    longitude, checkStatus(dtmfValue),
                                    dtmfValue);
                            dtmfCommandDao.save(newCommand);
                        }
                    }
                }
                else {
                    final Long dtmfValue = realTimeEntity.getLastCommand();
                    final DTMFCommandEntity newCommand = new DTMFCommandEntity(
                            (long)satelliteId, sequenceNumber,
                            (long)frameType, new Timestamp(now.getTime()),
                            latitude, longitude, checkStatus(dtmfValue),
                            dtmfValue);
                    dtmfCommandDao.save(newCommand);
                }
            }

            boolean valid = true;
            

            if (satelliteId == 1) {
                valid = realTimeEntity.getC53() && realTimeEntity
                        .getC54();
                hexFrame.setDigest(digest);
            }

            hexFrame.setValid(valid);
            hexFrame = hexFrameDao.save(hexFrame);
            realTimeEntity.setValid(valid);
            realTimeDao.save(realTimeEntity);
            hexFrame.setRealtimeProcessed(true);
            hexFrameDao.save(hexFrame);

            if (!outOfOrder) {
                checkMinMax(satelliteId, realTimeEntity);
            }

            incrementUploadRanking(satelliteId, user.getSiteId(), now);

        }
        else {

            hexFrame = frames.get(0);

            users = hexFrame.getUsers();

            boolean userFrameAlreadRegistered = false;

            for (final UserEntity existingUser : users) {
                final String message = String
                        .format("User %s attempted to add duplicate record for satId/seq/frame: %d, %d, %d",
                                existingUser.getSiteId(), satelliteId,
                                hexFrame.getSequenceNumber(),
                                hexFrame.getFrameType());
                if (satelliteId != 1
                        && existingUser.getId().longValue() == user.getId()
                                .longValue()) {
                    userFrameAlreadRegistered = true;
                    LOG.error(message);

                    return new ResponseEntity<String>("OK", HttpStatus.OK);

                }
                else if (existingUser.getId().longValue() == user.getId()
                        .longValue()
                        && hexFrame.getDigest() != null
                        && digest.equals(hexFrame.getDigest())) {
                    userFrameAlreadRegistered = true;
                    LOG.error(message);

                    return new ResponseEntity<String>("OK", HttpStatus.OK);
                }
            }

            if (!userFrameAlreadRegistered) {

                hexFrame.getUsers().add(user);

                hexFrameDao.save(hexFrame);

                incrementUploadRanking(satelliteId, user.getSiteId(), now);
            }
        }

        return new ResponseEntity<String>("OK",
                HttpStatus.OK);
    }

    private Boolean checkStatus(final Long dtmfValue) {
        switch (dtmfValue.intValue()) {
            case 0x00:
            case 0x02:
            case 0x04:
            case 0x08:
            case 0x09:
            case 0x0A:
            case 0x0C:
            case 0x0D:
            case 0x0E:
            case 0x11:
            case 0x12:
            case 0x13:
            case 0x14:
            case 0x15:
            case 0x16:
            case 0x17:
            case 0x18:
            case 0x19:
            case 0x1A:
            case 0x1B:
            case 0x1C:
            case 0x1D:
            case 0x1E:
            case 0x1F:
                return true;
            default:
                return false;
        }
    }

    private void incrementUploadRanking(final int satelliteId, final String siteId, final Date now) {

        final Timestamp latestUploadDate = new Timestamp(now.getTime());

        final List<UserRankingEntity> userRankings = userRankingDao
                .findBySatelliteIdAndSiteId(satelliteId, siteId);

        UserRankingEntity userRanking;

        if (userRankings.isEmpty()) {

            userRanking = new UserRankingEntity((long)satelliteId, siteId, 1L,
                    latestUploadDate, latestUploadDate);
        }
        else {
            userRanking = userRankings.get(0);
            userRanking.setLatestUploadDate(latestUploadDate);
            Long number = userRanking.getNumber();
            number++;
            userRanking.setNumber(number);
        }

        userRankingDao.save(userRanking);

    }

    @RequestMapping(value = "/{siteId}/{satelliteId}/{startSequenceNumber}/{endSequenceNumber}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> hexStrings(
            @PathVariable("siteId") final String siteId,
            @PathVariable("satelliteId") final long satelliteId,
            @PathVariable("startSequenceNumber") final long startSequenceNumber,
            @PathVariable("endSequenceNumber") final long endSequenceNumber,
            @RequestParam("digest") final String digest) {

        final List<String> hexStrings = new ArrayList<String>();

        return getHexStrings(siteId, satelliteId, startSequenceNumber,
                endSequenceNumber, digest, hexStrings);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private List<String> getHexStrings(final String siteId, final long satelliteId,
            final long startSequenceNumber, final long endSequenceNumber, final String digest,
            final List<String> hexStrings) {
        // get the user from the repository
        final List<UserEntity> users = userDao.findBySiteId(siteId);

        if (users.size() != 0) {

            final UserEntity user = users.get(0);

            String authKey = userAuthKeys.get(siteId);
            users.get(0).getAuthKey();

            if (authKey == null) {
                if (user != null) {
                    authKey = user.getAuthKey();
                    userAuthKeys.put(siteId, authKey);

                    final Long value = satelliteId * startSequenceNumber
                            * endSequenceNumber;

                    try {

                        final String calculatedDigest = DataProcessor.calculateDigest(
                                value.toString(), authKey, null);
                        final String calculatedDigestUTF8 = DataProcessor.calculateDigest(
                                value.toString(), authKey, Integer.valueOf(8));
                        final String calculatedDigestUTF16 = DataProcessor.calculateDigest(
                                value.toString(), authKey, Integer.valueOf(16));

                        if (null != digest
                                && (digest.equals(calculatedDigest)
                                        || digest.equals(calculatedDigestUTF8) || digest
                                            .equals(calculatedDigestUTF16))) {
                            return hexFrameDao
                                    .getHexStringBetweenSequenceNumbers(
                                            satelliteId, startSequenceNumber,
                                            endSequenceNumber);
                        }
                        else {
                            return hexStrings;
                        }
                    }
                    catch (final NoSuchAlgorithmException nsae) {
                        return hexStrings;
                    }

                }
                else {
                    return hexStrings;
                }
            }
            else {
                return hexStrings;
            }
        }
        else {
            return hexStrings;
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private void checkMinMax(final long satelliteId, final RealTimeEntity realTimeEntity) {

        final Long[] longValues = realTimeEntity.getLongValues();

        for (int channel = 1; channel <= 43; channel++) {

            boolean isDirty = false;

            final List<MinMaxEntity> channels = minMaxDao
                    .findBySatelliteIdAndChannel(satelliteId, channel);
            if (channels.isEmpty()) {
                LOG.error("Did not find any minmax data for channel: "
                        + channel);
                break;
            }
            MinMaxEntity minMaxEntity = channels.get(0);

            final Date refDate = minMaxEntity.getRefDate();

            final Date currentDate = clock.currentDate();

            final long ageInMillis = currentDate.getTime() - refDate.getTime();

            if (ageInMillis > 7 * 24 * 60 * 60 * 1000) {
                minMaxEntity.setEnabled(false);
                minMaxDao.save(minMaxEntity);
                minMaxEntity = new MinMaxEntity(satelliteId, (long)channel,
                        99999L, -99999L, currentDate, true);
                isDirty = true;
            }

            Long channelValue = longValues[channel - 1];

            switch (channel) {
                default:
                    if (channelValue == null) {
                        break;
                    }
                    if (channelValue < minMaxEntity.getMinimum()) {
                        minMaxEntity.setMinimum(channelValue);
                        isDirty = true;
                    }
                    else if (channelValue > minMaxEntity.getMaximum()) {
                        minMaxEntity.setMaximum(channelValue);
                        isDirty = true;
                    }
                    break;
                case 9:
                case 10:
                case 11:
                case 12:
                    // Boost converter temp 1
                    if (channelValue == null || channelValue == 0) {
                        break;
                    }

                    if (channelValue >= 128) {
                        channelValue = ~channelValue ^ 255;
                    }

                    if (channelValue < minMaxEntity.getMinimum()) {
                        minMaxEntity.setMinimum(channelValue);
                        isDirty = true;
                    }
                    else if (channelValue > minMaxEntity.getMaximum()) {
                        minMaxEntity.setMaximum(channelValue);
                        isDirty = true;
                    }
                    break;
            }

            if (isDirty) {
                minMaxDao.save(minMaxEntity);
            }
        }

    }

    private static String calculateDigest(final String hexString,
            final String authCode, final Integer utf)
            throws NoSuchAlgorithmException {

        String digest = null;

        final MessageDigest md5 = MessageDigest.getInstance("MD5");

        if (utf == null) {

            md5.update(hexString.getBytes());
            md5.update(":".getBytes());
            digest = DataProcessor.convertToHex(md5.digest(authCode.getBytes()));
        }
        else if (utf.intValue() == 8) {
            md5.update(hexString.getBytes(Charset.forName("UTF8")));
            md5.update(":".getBytes(Charset.forName("UTF8")));
            digest = DataProcessor.convertToHex(md5.digest(authCode.getBytes(Charset
                    .forName("UTF8"))));
        }
        else {
            md5.update(hexString.getBytes(Charset.forName("UTF16")));
            md5.update(":".getBytes(Charset.forName("UTF16")));
            digest = DataProcessor.convertToHex(md5.digest(authCode.getBytes(Charset
                    .forName("UTF16"))));
        }

        return digest;
    }

    private static String convertToHex(final byte[] data) {
        final StringBuffer buf = new StringBuffer();
        for (final byte element : data) {
            int halfbyte = (element >>> 4) & HEX_0X0F;
            int twoHalfs = 0;
            do {
                if (0 <= halfbyte && halfbyte <= 9) {
                    buf.append((char)('0' + halfbyte));
                }
                else {
                    buf.append((char)('a' + (halfbyte - 10)));
                }
                halfbyte = element & HEX_0X0F;
            }
            while (twoHalfs++ < 1);
        }
        return buf.toString();
    }

    public static String convertHexBytePairToBinary(final String hexString) {
        final StringBuffer sb = new StringBuffer();

        for (int i = 0; i < hexString.length(); i += 2) {
            final String hexByte = hexString.substring(i, i + 2);
            final int hexValue = Integer.parseInt(hexByte, 16);
            sb.append(StringUtils.leftPad(Integer.toBinaryString(hexValue), 8,
                    "0"));
        }
        return sb.toString();
    }

    private static final int HEX_0X0F = 0x0F;
    private static final Cache<String, String> userAuthKeys = new Cache<String, String>(
            new UTCClock(), 50, 10);
    private static final String HAD_INCORRECT_DIGEST = "] had incorrect digest";
    private static final String USER_WITH_SITE_ID = "User with site id [";

    private static final String NOT_FOUND = "] not found";

    private String latitude;

    private String longitude;

    public void processSha2() {

        do {
            final List<HexFrameEntity> hexFrames = getNullDigests();
            if (hexFrames.size() == 0) {
                break;
            }
            addDigests(hexFrames);
        }
        while (true);

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private void addDigests(final List<HexFrameEntity> hexFrames) {
        for (final HexFrameEntity hexFrameEntity : hexFrames) {
            try {
                final MessageDigest md = MessageDigest.getInstance("SHA-256");
                hexFrameEntity.setDigest(new String(md.digest(hexFrameEntity
                        .getHexString().getBytes())));
                hexFrameDao.save(hexFrameEntity);
            }
            catch (final NoSuchAlgorithmException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    private List<HexFrameEntity> getNullDigests() {
        return hexFrameDao
                .findBySatelliteIdAndDigest(1L, (String)null, new PageRequest(
                        0, 100));
    }

    private String generateDigest(final String string) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(string.getBytes());

            final byte[] byteData = md.digest();

            // convert the byte to hex format method 1
            final StringBuffer sb = new StringBuffer();
            for (final byte element : byteData) {
                sb.append(Integer.toString((element & 0xff) + 0x100, 16)
                        .substring(1));
            }

            return sb.toString();
        }
        catch (final NoSuchAlgorithmException e) {
            LOG.error(e.getMessage());
            return "";
        }

    }

    /**
     * @param l
     */
    public void fixRealTime(long satelliteId) {
        
        LOG.debug("fixRealTime called for satellite id: " + satelliteId);
        
        boolean data = true;
        int pageCount = 0;
        
        do {
            Page<HexFrameEntity> page = hexFrameDao.findBySatelliteIdAndRealtimeProcessed(satelliteId, false, new PageRequest(pageCount, 1000));
            data = page.hasContent();
            if (data) {
                LOG.debug(page.getNumberOfElements() + " elements found");
                Iterator<HexFrameEntity> iterator = page.iterator();
                while (iterator.hasNext()) {
                    HexFrameEntity hexFrameEntity = iterator.next();
                    processRealTime(hexFrameEntity);
                }
                pageCount++;
            }
        } while (data);
        
    }

    /**
     * @param hexFrameEntity
     */
    @Transactional(readOnly = false)
    private void processRealTime(HexFrameEntity hexFrameEntity) {
        
        final String hexString = hexFrameEntity.getHexString();
        
        final int frameId = Integer.parseInt(hexString.substring(0, 2), 16);

        int frameType = frameId & 63;
        int satelliteId = (frameId & 128 + 64) >> 6;
        
        // we now need to look elsewhere if the satellite ID == 3
        if (satelliteId == 3) {
            final int idTemp = Integer.parseInt(hexString.substring(2, 4), 16);
            satelliteId = idTemp & 252;
            final int ftTemp = idTemp & 3;
            frameType = frameType + (ftTemp << 7);
        }
        
        final int sensorId = frameId % 2;
        
        final String binaryString = DataProcessor.convertHexBytePairToBinary(hexString
                .substring((satelliteId < 3) ? 2 : 4, hexString.length()));
        
        RealTime realTime;

        switch(satelliteId) {
            case 8: 
                /* nayif-1 */
                realTime = new RealTimeNayif1(satelliteId, frameType, hexFrameEntity.getCreatedDate(),
                        binaryString);
                break;
            case 1:
                realTime = new RealTimeFC2(satelliteId, frameType, sensorId, hexFrameEntity.getCreatedDate(),
                        binaryString);
                break;
            default:
                realTime = new RealTime(satelliteId, frameType, sensorId, hexFrameEntity.getCreatedDate(),
                        binaryString);
                break;
        }

        
        RealTimeEntity realTimeEntity;
        
        if (realTime instanceof RealTimeFC2) {
            realTimeEntity = new RealTimeEntity((RealTimeFC2)realTime,
                    hexFrameEntity.getSatelliteTime());
            realTimeEntity.setSatelliteName("FC2");
        }
        else if (realTime instanceof RealTimeNayif1) {
            realTimeEntity = new RealTimeEntity((RealTimeNayif1)realTime,
                    hexFrameEntity.getSatelliteTime());
            realTimeEntity.setSatelliteName("Nayif-1");
        } 
        else {
            realTimeEntity = new RealTimeEntity(realTime, hexFrameEntity.getSatelliteTime());
            realTimeEntity.setSatelliteName("FC1");
        }
        
        realTimeDao.save(realTimeEntity);
        hexFrameEntity.setRealtimeProcessed(true);
        hexFrameDao.save(hexFrameEntity);
        
    }

}
