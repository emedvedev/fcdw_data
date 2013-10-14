// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.processor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import uk.org.funcube.fcdw.dao.HexFrameDao;
import uk.org.funcube.fcdw.dao.WholeOrbitDataDao;
import uk.org.funcube.fcdw.domain.ClydeSpaceWODEntity;
import uk.org.funcube.fcdw.domain.GomSpaceWODEntity;
import uk.org.funcube.fcdw.domain.HexFrameEntity;
import uk.org.funcube.fcdw.domain.WholeOrbitDataEntity;


public class WholeOrbitDataProcessorImpl implements WholeOrbitDataProcessor {
	
	private static Logger LOG = Logger.getLogger(HighResolutionProcessorImpl.class.getName());
	
	@Autowired
	HexFrameDao hexFrameDao;
	
	@Autowired
	WholeOrbitDataDao wholeOrbitDataDao;

	@Override
	@Transactional(readOnly=false)
	public void process(long satelliteId) {
		Calendar cal = Calendar.getInstance(TZ);
		cal.add(Calendar.HOUR, -24);

		final List<HexFrameEntity> wodList = hexFrameDao.findUnprocessedWOD(satelliteId, cal.getTime());

		LOG.debug("Found: " + wodList.size() + " unprocessed wod frames");

		long oldSeqNo = -1;

		List<String> frames = new ArrayList<String>();

		List<HexFrameEntity> processedHexFrames = new ArrayList<HexFrameEntity>();

		Date receivedDate = null;

		for (final HexFrameEntity wodFrame : wodList) {
			if (wodFrame.getFrameType() == 11) {
				receivedDate = wodFrame.getCreatedDate();
			}
			if (wodFrame.getSequenceNumber() != oldSeqNo) {
				if (oldSeqNo != -1) {
					if (frames.size() == 12) {

						cal = Calendar.getInstance(TZ);
						cal.setTime(receivedDate);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						receivedDate = cal.getTime();

						extractAndSaveWod(satelliteId, oldSeqNo, frames, receivedDate);
						
						for (HexFrameEntity hfe : processedHexFrames) {
							hfe.setHighPrecisionProcessed(true);
							hexFrameDao.save(hfe);
						}
					}
					frames = new ArrayList<String>();
					processedHexFrames = new ArrayList<HexFrameEntity>();
					frames.add(wodFrame.getHexString().substring(106, wodFrame.getHexString().length()));
					processedHexFrames.add(wodFrame);
				}

				oldSeqNo = wodFrame.getSequenceNumber();
			} else {
				frames.add(wodFrame.getHexString().substring(106, wodFrame.getHexString().length()));
				processedHexFrames.add(wodFrame);
			}
		}

	}

	private void extractAndSaveWod(final Long satelliteId, final long seqNo, final List<String> frames, final Date receivedDate) {

		final Date frameTime = new Date(receivedDate.getTime() - 104 * 60 * 1000);

		final StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 12; i++) {
			sb.append(StringUtils.right(frames.get(i), 400));
		}

		int start = 0;
		int end = 46;

		for (int i = 0; i < 104; i++) {

			final long frameNumber = seqNo * 2 + i;

			if (wholeOrbitDataDao
					.findBySatelliteIdAndFrameNumber(
							satelliteId, frameNumber).size() == 0) {

				WholeOrbitDataEntity wod = null;

				switch (satelliteId.intValue()) {
				case 0:
					wod = new GomSpaceWODEntity(satelliteId, seqNo, frameNumber, convertHexBytePairToBinary(sb.substring(start, end)),
							frameTime);
					break;
				case 1:
					wod = new ClydeSpaceWODEntity(satelliteId, seqNo, frameNumber, convertHexBytePairToBinary(sb.substring(start, end)),
							frameTime);
					break;
				case 2:
					wod = new GomSpaceWODEntity(satelliteId, seqNo, frameNumber, convertHexBytePairToBinary(sb.substring(start, end)),
							frameTime);
					break;
				default:
					break;
				}

				if (wod != null) {
					wholeOrbitDataDao.save(wod);
				}

				start += 46;
				end += 46;
			}

			// move the frame time forward a minute
			frameTime.setTime(frameTime.getTime() + 60 * 1000);
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
	
	private static final SimpleTimeZone TZ = new SimpleTimeZone(0, "UTC");

}
