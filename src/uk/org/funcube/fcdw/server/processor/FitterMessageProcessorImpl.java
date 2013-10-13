package uk.org.funcube.fcdw.server.processor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Transactional(readOnly=false)
	public void process(long satelliteId) {
		Calendar cal = Calendar.getInstance(TZ);
		cal.add(Calendar.HOUR, -24);

		final List<HexFrameEntity> fitterList = hexFrameDao.findUnprocessedFitter(satelliteId, cal.getTime());

		LOG.debug("Found: " + fitterList.size() + " unprocessed fitter frames");

		Date receivedDate = null;

		for (final HexFrameEntity fitterFrame : fitterList) {

			receivedDate = fitterFrame.getCreatedDate();

			cal = Calendar.getInstance(TZ);
			cal.setTime(receivedDate);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			receivedDate = cal.getTime();

			extractAndSaveFitter(satelliteId, fitterFrame, receivedDate);
		}
		
	}

	private void extractAndSaveFitter(long satelliteId, HexFrameEntity frame, Date lastReceived) {

		StringBuffer sb = new StringBuffer();

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
						saveFitter(satelliteId, lastReceived, messageText, false);
						sb = new StringBuffer();
					}
				} else {
					sb.append((char) value);
				}
			}
		} else {
			final String messageText = messageHex.substring(8, messageHex.length());
			saveFitter(satelliteId, lastReceived, messageText, true);
		}

		frame.setFitterProcessed(true);
		hexFrameDao.save(frame);

	}

	private void saveFitter(long satelliteId, Date lastReceived, final String messageText, final Boolean isDebug) {
		List<FitterMessageEntity> fitterMessages 
			= fitterMessageDao.findBySatelliteIdAndMessageTextAndDebug(
					satelliteId, 
					messageText,
					isDebug);
		
		FitterMessageEntity fitterMessage;
		
		if (fitterMessages.size() != 0) {
			fitterMessage = fitterMessages.get(0);
			fitterMessage.setLastReceived(lastReceived);
		} else {
			fitterMessage = new FitterMessageEntity(messageText, lastReceived, satelliteId, isDebug);
		}
		
		fitterMessageDao.save(fitterMessage);
	}

	private static final SimpleTimeZone TZ = new SimpleTimeZone(0, "UTC");

}
