// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.org.funcube.fcdw.dao.FitterMessageDao;
import uk.org.funcube.fcdw.dao.HexFrameDao;
import uk.org.funcube.fcdw.dao.SatelliteStatusDao;
import uk.org.funcube.fcdw.domain.FitterMessageEntity;
import uk.org.funcube.fcdw.domain.HexFrameEntity;
import uk.org.funcube.fcdw.domain.SatelliteStatusEntity;
import uk.org.funcube.fcdw.server.shared.FitterDebug;

public class FitterMessageProcessorImpl implements FitterMessageProcessor {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss zzz";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    protected final SimpleDateFormat SDTF = new SimpleDateFormat(DATE_TIME_FORMAT);
    protected final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);

    private static final String ONE = "1";

    private static Logger LOG = Logger.getLogger(FitterMessageProcessorImpl.class.getName());

    @Autowired
    FitterMessageDao fitterMessageDao;

    @Autowired
    HexFrameDao hexFrameDao;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    public FitterMessageProcessorImpl() {
        super();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void process(final long satelliteId) {
        final Calendar cal = Calendar.getInstance(TZ);
        cal.add(Calendar.HOUR_OF_DAY, -24);

        final List<HexFrameEntity> fitterList = hexFrameDao.findUnprocessedFitter(satelliteId, cal.getTime());

        LOG.debug("Found: " + fitterList.size() + " unprocessed fitter frames");

        for (final HexFrameEntity fitterFrame : fitterList) {

            final Timestamp receivedDate = new Timestamp(fitterFrame.getCreatedDate().getTime());

            extractAndSaveFitter(satelliteId, fitterFrame, receivedDate);
        }

        final List<SatelliteStatusEntity> satelliteStatuses = satelliteStatusDao.findBySatelliteId(satelliteId);
        if (!satelliteStatuses.isEmpty()) {
            final SatelliteStatusEntity satelliteStatus = satelliteStatuses.get(0);
            final List<FitterMessageEntity> latestDebug = fitterMessageDao.getLatestDebug(satelliteId, new PageRequest(
                    0, 1));
            if (!latestDebug.isEmpty()) {
                final FitterMessageEntity fitter = latestDebug.get(0);
                final FitterDebug fitterDebug = new FitterDebug(
                        FitterMessageProcessorImpl.convertHexBytePairToBinary(fitter.getMessageText()));
                satelliteStatus.setEclipseModeForced(ONE.equals(fitterDebug.getEclipseForce()));
                satelliteStatus.setEclipseSwitch(ONE.equals(fitterDebug.getEclipseSwitch()));
                satelliteStatusDao.save(satelliteStatus);
            }
        }

    }

    private void extractAndSaveFitter(final long satelliteId, final HexFrameEntity frame, final Timestamp lastReceived) {

        StringBuffer sb = new StringBuffer();

        final int frameType = frame.getFrameType().intValue();

        final String slot = getSlotFromFrameType(frameType);

        final String messageHex = frame.getHexString().substring(106, frame.getHexString().length());

        // we may be processing a packet after a reset so the frame may contain 000's
        if ("00".equals(messageHex.substring(6, 8))) {
            return;
        }

        // we do not process DEBUG frames
        if (!"FF".equals(messageHex.substring(6, 8))) {

            for (int i = 6; i < messageHex.length() - 2; i += 2) {
                final int value = Integer.parseInt(messageHex.substring(i, i + 2), 16);
                if (value == 0) {
                    if (i == 6) {
                        break;
                    }
                    else {
                        final String messageText = sb.toString();
                        saveFitter(satelliteId, lastReceived, messageText, false, slot);
                        sb = new StringBuffer();
                    }
                }
                else {
                    sb.append((char)value);
                }
            }
        }
        else {
            final String messageText = messageHex.substring(8, messageHex.length());
            saveFitter(satelliteId, lastReceived, messageText, true, slot);
        }

        frame.setFitterProcessed(true);
        hexFrameDao.save(frame);

    }

    private String getSlotFromFrameType(final int frameType) {

        String slot = "FM";

        switch (frameType) {
            case 13:
            case 14:
            case 15:
                slot += frameType - 12;
                break;
            case 17:
            case 18:
            case 19:
                slot += frameType - 13;
                break;
            case 21:
            case 22:
            case 23:
                slot += frameType - 14;
                break;
        }

        return slot;

    }

    private void saveFitter(final long satelliteId, final Timestamp lastReceived, final String messageText,
            final Boolean isDebug, final String slot) {
        final List<FitterMessageEntity> fitterMessages = fitterMessageDao.findBySatelliteIdAndMessageTextAndDebug(
                satelliteId,
                messageText,
                isDebug);

        FitterMessageEntity fitterMessage;

        if (fitterMessages.size() != 0) {
            fitterMessage = fitterMessages.get(0);
            fitterMessage.setLastReceived(lastReceived);
            fitterMessage.setSlot(slot);
        }
        else {
            fitterMessage = new FitterMessageEntity(messageText, lastReceived,
                    satelliteId, isDebug, slot, lastReceived);
        }

        fitterMessageDao.save(fitterMessage);
    }

    private static final SimpleTimeZone TZ = new SimpleTimeZone(0, "UTC");

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void truncate(final long satelliteId) {

        final Calendar cal = Calendar.getInstance(TZ);
        cal.add(Calendar.DAY_OF_MONTH, -7);
        

        LOG.debug("Dumping fitter messages from: " + SDTF.format(cal.getTime()));
        
        
        List<FitterMessageEntity> messages 
            = fitterMessageDao.getNoneDebugReceivedAfter(satelliteId, new Timestamp(cal.getTime().getTime()));
        
        if (!messages.isEmpty() && satelliteId == 2) {
            
            final File fileLocation = new File(System.getProperty("txt.fitter"));
            
            if (fileLocation.exists()) {
                fileLocation.delete();
            }
            
            FileWriter fw = null;
            
            try {
            
                fw = new FileWriter(fileLocation);
    
                for (final FitterMessageEntity entity : messages) {
                    fw.write(entity.getMessageText() + "\n");
                    LOG.debug("Writing " + entity.getMessageText() + "\n");
                }
            } 
            catch (final IOException ioe) {
                LOG.error(ioe.getMessage());
            }
            finally {
                try {
                    if (fw != null) {
                        fw.close();
                    }
                }
                catch (final IOException ioe) {
                        LOG.error(ioe.getMessage());
                }
            }
        }
        else {
            LOG.error("No Fitter messages to dump");
        }

        messages = fitterMessageDao.getNoneDebugReceivedBefore(satelliteId,
                cal.getTime());

        for (final FitterMessageEntity entity : messages) {
            entity.setDisplay(false);
            fitterMessageDao.save(entity);
        }
    }

    private static String convertHexBytePairToBinary(final String hexString) {
        final StringBuffer sb = new StringBuffer();

        for (int i = 0; i < hexString.length(); i += 2) {
            final String hexByte = hexString.substring(i, i + 2);
            final int hexValue = Integer.parseInt(hexByte, 16);
            sb.append(StringUtils.leftPad(Integer.toBinaryString(hexValue), 8, "0"));
        }
        return sb.toString();
    }

}
