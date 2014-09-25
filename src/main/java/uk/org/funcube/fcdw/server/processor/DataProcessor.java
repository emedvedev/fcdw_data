// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

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
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import uk.org.funcube.fcdw.server.shared.RealTime;
import uk.org.funcube.fcdw.server.shared.RealTimeFC2;
import uk.org.funcube.fcdw.server.shared.SatellitePosition;
import uk.org.funcube.fcdw.server.util.Cache;
import uk.org.funcube.fcdw.server.util.Clock;
import uk.org.funcube.fcdw.server.util.UTCClock;
import uk.org.funcube.fcdw.service.MailService;
import uk.org.funcube.fcdw.service.PredictorService;

@Service
@RequestMapping("/api/data/hex")
public class DataProcessor {

	private static final TransferQueue<UserHexString> FIFO 
		= new LinkedTransferQueue<DataProcessor.UserHexString>();

	long TWO_DAYS_SEQ_COUNT = 1440;

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

	@RequestMapping(value = "/{siteId}/", method = RequestMethod.POST)
	public ResponseEntity<String> uploadData(@PathVariable String siteId,
			@RequestParam(value = "digest") String digest,
			@RequestBody String body) {

		return bufferData(siteId, digest, body);

	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	private ResponseEntity<String> bufferData(String siteId, String digest,
			String body) {
		// get the user from the repository
		List<UserEntity> users = userDao.findBySiteId(siteId);

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
					} else {
						LOG.error(USER_WITH_SITE_ID + siteId + NOT_FOUND);
						return new ResponseEntity<String>("UNAUTHORIZED",
								HttpStatus.UNAUTHORIZED);
					}
				}

				final String calculatedDigest = calculateDigest(hexString,
						authKey, null);
				final String calculatedDigestUTF8 = calculateDigest(hexString,
						authKey, new Integer(8));
				final String calculatedDigestUTF16 = calculateDigest(hexString,
						authKey, new Integer(16));

				if (null != digest
						&& (digest.equals(calculatedDigest)
								|| digest.equals(calculatedDigestUTF8) || digest
									.equals(calculatedDigestUTF16))) {

					hexString = StringUtils.deleteWhitespace(hexString);

					final Date now = clock.currentDate();

					FIFO.add(new UserHexString(user, StringUtils
							.deleteWhitespace(hexString), now));
					// LOG.debug("Writing fifo, size:" + FIFO.size() +
					// " , element count: " + FIFO.toArray().length);

					return new ResponseEntity<String>("OK", HttpStatus.OK);
				} else {
					LOG.error(USER_WITH_SITE_ID + siteId + HAD_INCORRECT_DIGEST
							+ ", received: " + digest + ", calculated: "
							+ calculatedDigest);
					return new ResponseEntity<String>("UNAUTHORIZED",
							HttpStatus.UNAUTHORIZED);
				}
			} catch (final Exception e) {
				LOG.error(e.getMessage());
				return new ResponseEntity<String>(e.getMessage(),
						HttpStatus.BAD_REQUEST);
			}

		} else {
			LOG.error("Site id: " + siteId + " not found in database");
			return new ResponseEntity<String>("UNAUTHORIZED",
					HttpStatus.UNAUTHORIZED);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void processHexFrame() {

		Map<Long, EpochEntity> epochMap = new HashMap<Long, EpochEntity>();

		Iterator<EpochEntity> iterator = epochDao.findAll().iterator();

		while (iterator.hasNext()) {
			EpochEntity epoch = iterator.next();
			epochMap.put(epoch.getSatelliteId(), epoch);
		}

		// LOG.debug("Reading fifo, size:" + FIFO.size() + " , element count: "
		// + FIFO.toArray().length);
		if (!FIFO.isEmpty()) {
			final Iterator<UserHexString> iter = FIFO.iterator();
			while (iter.hasNext()) {
				final UserHexString userHexString = (UserHexString) iter.next();
				iter.remove();

				final String hexString = userHexString.getHexString();
				final UserEntity user = userHexString.getUser();
				final Date createdDate = userHexString.getCreatedDate();

				final int frameId = Integer.parseInt(hexString.substring(0, 2),
						16);

				final int frameType = frameId & 63;
				final int satelliteId = (frameId & (128 + 64)) >> 6;
				final int sensorId = frameId % 2;
				final String binaryString = convertHexBytePairToBinary(hexString
						.substring(2, hexString.length()));
				final Date now = clock.currentDate();

				RealTime realTime;
				
				if (satelliteId == 1) {
					realTime = new RealTimeFC2(satelliteId, frameType,
							sensorId, now, binaryString);
				} else {
					realTime = new RealTime(satelliteId, frameType,
							sensorId, now, binaryString);
				}
				
				final long sequenceNumber = realTime.getSequenceNumber();

				if (sequenceNumber != -1) {
					
					if (satelliteId != 1) {

						Long maxSequenceNumber = hexFrameDao
								.getMaxSequenceNumber(satelliteId);
	
						if (maxSequenceNumber != null
								&& (maxSequenceNumber - sequenceNumber) > TWO_DAYS_SEQ_COUNT) {
							LOG.error(String
									.format("User %s loading sequence number %d is out of bounds for satelliteId %d",
											user.getSiteId(), sequenceNumber, satelliteId));
							return;
						}
					
					}

					final List<HexFrameEntity> frames = hexFrameDao
							.findBySatelliteIdAndSequenceNumberAndFrameType(
									satelliteId, sequenceNumber, frameType);

					if (frames != null) {
						
						if (frames.size() > 0 && satelliteId == 1 && frameType == 40) {
							processFrameTypeForty(hexString, now, sequenceNumber);
						} else {
							saveUpdateHexFrame(hexString, user, frameType,
								satelliteId, now, realTime, sequenceNumber,
								frames, epochMap.get(new Long(satelliteId)));
						}
					}
				}
			}
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void processFrameTypeForty(String payload, Date now,
			long sequenceNumber) {
		
		frameTypeFortyDao.save(new FrameTypeFortyEntity(sequenceNumber, now, payload));
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void saveUpdateHexFrame(final String hexString,
			final UserEntity user, final int frameType, final int satelliteId,
			final Date now, final RealTime realTime, final long sequenceNumber,
			final List<HexFrameEntity> frames, final EpochEntity epoch) {
		HexFrameEntity hexFrame;
		Set<UserEntity> users;
		
		
		if (frames.size() == 0) {
			

			Timestamp satelliteTime;

			if (epoch == null || satelliteId == 1) {
				satelliteTime = new Timestamp(now.getTime());
			} else {
				satelliteTime = new Timestamp(
						epoch.getReferenceTime().getTime()
								+ ((sequenceNumber - epoch.getSequenceNumber()) * 2 * 60 * 1000)
								+ (frameType * 5 * 1000));
			}

			long[] catalogNumbers = new long[] {39444, 40074, 39444, 39444};
			
			boolean outOfOrder = false;
			
			List<HexFrameEntity> existingFrames 
				= hexFrameDao.findBySatelliteIdAndSequenceNumber(satelliteId, sequenceNumber);
			
			if (!existingFrames.isEmpty()) {
				for (HexFrameEntity existingFrame : existingFrames) {
					if (existingFrame.getCreatedDate().before(now) && existingFrame.getFrameType() > frameType) {
						outOfOrder = false;
						break;
					}
				}
			}

			hexFrame = new HexFrameEntity((long) satelliteId, (long) frameType,
					sequenceNumber, hexString, now, true, satelliteTime);
			
			hexFrame.setOutOfOrder(outOfOrder);
			
			if (!outOfOrder) {
				SatellitePosition satellitePosition = predictor.get(catalogNumbers[satelliteId], now);
				
				if (satellitePosition != null) {
					hexFrame.setEclipsed(satellitePosition.getEclipsed());
					hexFrame.setEclipseDepth(satellitePosition.getEclipseDepth());
					latitude = satellitePosition.getLatitude();
					hexFrame.setLatitude(latitude);
					longitude = satellitePosition.getLongitude();
					hexFrame.setLongitude(longitude);
				}
				
				SatelliteStatusEntity satelliteStatus = satelliteStatusDao.findBySatelliteId(satelliteId).get(0);
				satelliteStatus.setSequenceNumber(realTime.getSequenceNumber());
				satelliteStatus.setLastUpdated(new Timestamp(now.getTime()));
				satelliteStatus.setEclipsed(realTime.isEclipsed());
				
				if (satellitePosition != null) {
					satelliteStatus.setEclipseDepth(Double.parseDouble(satellitePosition.getEclipseDepth()));
				}
				
				satelliteStatusDao.save(satelliteStatus);
			}

			users = hexFrame.getUsers();

			users.add(user);

			RealTimeEntity realTimeEntity;
			
			if (realTime instanceof RealTimeFC2) {
				realTimeEntity = new RealTimeEntity((RealTimeFC2) realTime, satelliteTime);
			} else {
				realTimeEntity = new RealTimeEntity(realTime, satelliteTime);
			}
			
			if (!outOfOrder) {
				final Long dtmfId = dtmfCommandDao.getMaxId(satelliteId);
				
				if (dtmfId != null) {
					final List<DTMFCommandEntity> commandEntities = dtmfCommandDao.findById(dtmfId);
					if (!commandEntities.isEmpty()) {
						DTMFCommandEntity lastCommand = commandEntities.get(0);
						Long dtmfValue = realTimeEntity.getLastCommand();
						if (dtmfValue.longValue() != lastCommand.getValue().longValue()) {
							DTMFCommandEntity newCommand
								= new DTMFCommandEntity(
										(long)satelliteId, 
										(long)sequenceNumber, 
										(long)frameType, 
										new Timestamp(now.getTime()), 
										latitude, 
										longitude, 
										checkStatus(dtmfValue), 
										dtmfValue);
							dtmfCommandDao.save(newCommand);
						}
					}
				} else {
					Long dtmfValue = realTimeEntity.getLastCommand();
					DTMFCommandEntity newCommand
					= new DTMFCommandEntity(
							(long)satelliteId, 
							(long)sequenceNumber, 
							(long)frameType, 
							new Timestamp(now.getTime()), 
							latitude, 
							longitude, 
							checkStatus(dtmfValue), 
							dtmfValue);
					dtmfCommandDao.save(newCommand);
				}
			}
			
			if (satelliteId == 1) {
				boolean valid = 
						(realTimeEntity.getC53() && realTimeEntity.getC54());
				hexFrame.setValid(valid);
				hexFrameDao.save(hexFrame);
				if (valid) {
					realTimeDao.save(realTimeEntity);
				}
			} else {
				hexFrameDao.save(hexFrame);
				realTimeDao.save(realTimeEntity);
			}

			

			if (!outOfOrder) {
				checkMinMax(satelliteId, realTimeEntity);
			}
			
			incrementUploadRanking(satelliteId, user.getSiteId(), now);

		} else {
			
			hexFrame = frames.get(0);

			users = hexFrame.getUsers();
			
			boolean userFrameAlreadRegistered = false;
			
			for (UserEntity existingUser : users) {
				if (existingUser.getId().longValue() == user.getId().longValue()) {
					userFrameAlreadRegistered = true;
					LOG.error(String.format("User %s attempted to add duplicate record for seq/frame: %d, %d", 
							existingUser.getSiteId(), hexFrame.getSequenceNumber(), hexFrame.getFrameType()));
					break;
				}
			}
			
			if (!userFrameAlreadRegistered) {

				users.add(user);
	
				hexFrameDao.save(hexFrame);
				
				incrementUploadRanking(satelliteId, user.getSiteId(), now);
			}
		}
	}

	/**
	 * @param dtmfValue
	 * @return
	 */
	private Boolean checkStatus(Long dtmfValue) {
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

	private void incrementUploadRanking(int satelliteId, String siteId, Date now) {
		
		final Timestamp latestUploadDate = new Timestamp(now.getTime());
		
		List<UserRankingEntity> userRankings 
			= userRankingDao.findBySatelliteIdAndSiteId(satelliteId, siteId);
		
		UserRankingEntity userRanking;
		
		if (userRankings.isEmpty()) {
			
			userRanking 
				= new UserRankingEntity((long) satelliteId, siteId, 1L, latestUploadDate);
		} else {
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
			@PathVariable(value = "siteId") String siteId,
			@PathVariable(value = "satelliteId") long satelliteId,
			@PathVariable(value = "startSequenceNumber") long startSequenceNumber,
			@PathVariable(value = "endSequenceNumber") long endSequenceNumber,
			@RequestParam(value = "digest") String digest) {

		List<String> hexStrings = new ArrayList<String>();

		return getHexStrings(siteId, satelliteId, startSequenceNumber,
				endSequenceNumber, digest, hexStrings);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private List<String> getHexStrings(String siteId, long satelliteId,
			long startSequenceNumber, long endSequenceNumber, String digest,
			List<String> hexStrings) {
		// get the user from the repository
		List<UserEntity> users = userDao.findBySiteId(siteId);

		if (users.size() != 0) {

			UserEntity user = users.get(0);

			String authKey = userAuthKeys.get(siteId);
			users.get(0).getAuthKey();

			if (authKey == null) {
				if (user != null) {
					authKey = user.getAuthKey();
					userAuthKeys.put(siteId, authKey);

					Long value = satelliteId * startSequenceNumber
							* endSequenceNumber;

					try {

						final String calculatedDigest = calculateDigest(
								value.toString(), authKey, null);
						final String calculatedDigestUTF8 = calculateDigest(
								value.toString(), authKey, new Integer(8));
						final String calculatedDigestUTF16 = calculateDigest(
								value.toString(), authKey, new Integer(16));

						if (null != digest
								&& (digest.equals(calculatedDigest)
										|| digest.equals(calculatedDigestUTF8) || digest
											.equals(calculatedDigestUTF16))) {
							return hexFrameDao
									.getHexStringBetweenSequenceNumbers(
											satelliteId, startSequenceNumber,
											endSequenceNumber);
						} else {
							return hexStrings;
						}
					} catch (NoSuchAlgorithmException nsae) {
						return hexStrings;
					}

				} else {
					return hexStrings;
				}
			} else {
				return hexStrings;
			}
		} else {
			return hexStrings;
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void checkMinMax(long satelliteId, RealTimeEntity realTimeEntity) {

		final Long[] longValues = realTimeEntity.getLongValues();

		for (int channel = 1; channel <= 43; channel++) {

			boolean isDirty = false;

			List<MinMaxEntity> channels = minMaxDao
					.findBySatelliteIdAndChannel(satelliteId, channel);
			if (channels.isEmpty()) {
				LOG.error("Did not find any minmax data for channel: "
						+ channel);
				break;
			}
			MinMaxEntity minMaxEntity = channels.get(0);

			Date refDate = minMaxEntity.getRefDate();

			Date currentDate = clock.currentDate();

			long ageInMillis = currentDate.getTime() - refDate.getTime();

			if (ageInMillis > 7 * 24 * 60 * 60 * 1000) {
				minMaxEntity.setEnabled(false);
				minMaxDao.save(minMaxEntity);
				minMaxEntity = new MinMaxEntity(satelliteId, (long) channel,
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
				} else if (channelValue > minMaxEntity.getMaximum()) {
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
				} else if (channelValue > minMaxEntity.getMaximum()) {
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
			digest = convertToHex(md5.digest(authCode.getBytes()));
		} else if (utf.intValue() == 8) {
			md5.update(hexString.getBytes(Charset.forName("UTF8")));
			md5.update(":".getBytes(Charset.forName("UTF8")));
			digest = convertToHex(md5.digest(authCode.getBytes(Charset
					.forName("UTF8"))));
		} else {
			md5.update(hexString.getBytes(Charset.forName("UTF16")));
			md5.update(":".getBytes(Charset.forName("UTF16")));
			digest = convertToHex(md5.digest(authCode.getBytes(Charset
					.forName("UTF16"))));
		}

		return digest;
	}

	private static String convertToHex(final byte[] data) {
		final StringBuffer buf = new StringBuffer();
		for (final byte element : data) {
			int halfbyte = element >>> 4 & HEX_0X0F;
			int twoHalfs = 0;
			do {
				if (0 <= halfbyte && halfbyte <= 9) {
					buf.append((char) ('0' + halfbyte));
				} else {
					buf.append((char) ('a' + (halfbyte - 10)));
				}
				halfbyte = element & HEX_0X0F;
			} while (twoHalfs++ < 1);
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

	class UserHexString {

		private final UserEntity user;
		private final String hexString;
		private final Date createdDate;

		public UserHexString(final UserEntity user, final String hexString,
				final Date createdDate) {
			this.user = user;
			this.hexString = hexString;
			this.createdDate = createdDate;
		}

		/**
		 * @return the createdDate
		 */
		public final Date getCreatedDate() {
			return createdDate;
		}

		public final String getHexString() {
			return hexString;
		}

		public final UserEntity getUser() {
			return user;
		}

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
			List<HexFrameEntity> hexFrames = getNullDigests();
			if (hexFrames.size() == 0) {
				break;
			}
			addDigests(hexFrames);
		} while(true);
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void addDigests(List<HexFrameEntity> hexFrames) {
		for (HexFrameEntity hexFrameEntity : hexFrames) {
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				hexFrameEntity.setDigest(new String(md.digest(hexFrameEntity.getHexString().getBytes())));
				hexFrameDao.save(hexFrameEntity);
			} catch (NoSuchAlgorithmException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	private List<HexFrameEntity> getNullDigests() {
		List<HexFrameEntity> hexFrames = hexFrameDao.findBySatelliteIdAndDigest(1L, (String)null, new PageRequest(0, 100));
		return hexFrames;
	}

}
