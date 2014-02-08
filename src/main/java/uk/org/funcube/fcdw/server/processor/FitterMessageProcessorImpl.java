// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.processor;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.SimpleTimeZone;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.org.funcube.fcdw.dao.FitterMessageDao;
import uk.org.funcube.fcdw.dao.HexFrameDao;
import uk.org.funcube.fcdw.domain.FitterMessageEntity;
import uk.org.funcube.fcdw.domain.HexFrameEntity;


public class FitterMessageProcessorImpl implements FitterMessageProcessor {
	
	private static Logger LOG = Logger.getLogger(FitterMessageProcessorImpl.class.getName());
	
	@Autowired
	FitterMessageDao fitterMessageDao;
	
	@Autowired
	HexFrameDao hexFrameDao;
	
	                                                                                                                                                            @Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void process(long satelliteId) {
		Calendar cal = Calendar.getInstance(TZ);
		cal.add(Calendar.HOUR_OF_DAY, -24);

		final List<HexFrameEntity> fitterList = hexFrameDao.findUnprocessedFitter(satelliteId, cal.getTime());

		LOG.debug("Found: " + fitterList.size() + " unprocessed fitter frames");

		for (final HexFrameEntity fitterFrame : fitterList) {

			Timestamp receivedDate = new Timestamp(fitterFrame.getCreatedDate().getTime());

			extractAndSaveFitter(satelliteId, fitterFrame, receivedDate);
		}
		
	}

	private void extractAndSaveFitter(long satelliteId, HexFrameEntity frame, Timestamp lastReceived) {

		StringBuffer sb = new StringBuffer();
		
		int frameType = frame.getFrameType().intValue();
		
		String slot = getSlotFromFrameType(frameType);

		final String messageHex = frame.getHexString().substring(106, frame.getHexString().length());
		
		// we may be processing a packet after a reset so the frame may contain 000's
		if (messageHex.substring(6, 8).equals("00")) {
			return;
		}

		// we do not process DEBUG frames
		if (!messageHex.substring(6, 8).equals("FF")) {

			for (int i = 6; i < messageHex.length() - 2; i += 2) {
				final int value = Integer.parseInt(messageHex.substring(i, i + 2), 16);
				if (value == 0) {
					if (i == 6) {
						break;
					} else {
						final String messageText = sb.toString();
						saveFitter(satelliteId, lastReceived, messageText, false, slot);
						sb = new StringBuffer();
					}
				} else {
					sb.append((char) value);
				}
			}
		} else {
			final String messageText = messageHex.substring(8, messageHex.length());
			saveFitter(satelliteId, lastReceived, messageText, true, slot);
		}

		frame.setFitterProcessed(true);
		hexFrameDao.save(frame);

	}

	/**
	 * @param frameType
	 * @return
	 */
	private String getSlotFromFrameType(int frameType) {

		String slot = "FM";
		
		switch(frameType) {
		case 13:
		case 14:
		case 15:
			slot += (frameType - 12);
			break;
		case 17:
		case 18:
		case 19:
			slot += (frameType - 13);
			break;
		case 21:
		case 22:
		case 23:
			slot += (frameType - 13);
			break;
		}
		
		return slot;
		
	}

	private void saveFitter(long satelliteId, Timestamp lastReceived, final String messageText, 
			final Boolean isDebug, final String slot) {
		List<FitterMessageEntity> fitterMessages 
			= fitterMessageDao.findBySatelliteIdAndMessageTextAndDebug(
					satelliteId, 
					messageText,
					isDebug);
		
		FitterMessageEntity fitterMessage;
		
		if (fitterMessages.size() != 0) {
			fitterMessage = fitterMessages.get(0);
			fitterMessage.setLastReceived(lastReceived);
			fitterMessage.setSlot(slot);
		} else {
			fitterMessage = new FitterMessageEntity(messageText, lastReceived, 
					satelliteId, isDebug, slot, lastReceived);
		}
		
		fitterMessageDao.save(fitterMessage);
	}

	private static final SimpleTimeZone TZ = new SimpleTimeZone(0, "UTC");

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void truncate(long satelliteId) {
		

		Calendar cal = Calendar.getInstance(TZ);
		cal.add(Calendar.DAY_OF_MONTH, -7);
		
		List<FitterMessageEntity> messages = fitterMessageDao.getNoneDebugReceivedBefore(satelliteId, cal.getTime());
		
		for (FitterMessageEntity entity : messages) {
			entity.setDisplay(false);
			fitterMessageDao.save(entity);
		}
	}

}
