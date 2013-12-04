// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.processor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import uk.org.funcube.fcdw.dao.EpochDao;
import uk.org.funcube.fcdw.dao.HexFrameDao;
import uk.org.funcube.fcdw.dao.MinMaxDao;
import uk.org.funcube.fcdw.dao.WholeOrbitDataDao;
import uk.org.funcube.fcdw.domain.ClydeSpaceWODEntity;
import uk.org.funcube.fcdw.domain.EpochEntity;
import uk.org.funcube.fcdw.domain.GomSpaceWODEntity;
import uk.org.funcube.fcdw.domain.HexFrameEntity;
import uk.org.funcube.fcdw.domain.MinMaxEntity;
import uk.org.funcube.fcdw.domain.WholeOrbitDataEntity;

public class WholeOrbitDataProcessorImpl implements WholeOrbitDataProcessor {

	private static Logger LOG = Logger
			.getLogger(WholeOrbitDataProcessorImpl.class.getName());

	@Autowired
	HexFrameDao hexFrameDao;

	@Autowired
	WholeOrbitDataDao wholeOrbitDataDao;
	
	@Autowired
	EpochDao epochDao;

	@Autowired
	MinMaxDao minMaxDao;

	@Override
	public void process(long satelliteId) {
		
		List<EpochEntity> epochList = getEpoch(satelliteId);
		
		if (epochList.isEmpty()) {
			LOG.error("Did not find epoch for satellite id: " + satelliteId);
			return;
		}
		
		EpochEntity epoch = epochList.get(0);
		
		Calendar cal = Calendar.getInstance(TZ);
		cal.add(Calendar.HOUR, -24);

		final List<HexFrameEntity> wodList = getUnprocessedWod(satelliteId, cal);

		LOG.debug("Found: " + wodList.size() + " unprocessed wod frames");

		long oldSeqNo = -1;

		List<String> frames = new ArrayList<String>();

		List<HexFrameEntity> processedHexFrames = new ArrayList<HexFrameEntity>();

		Date receivedDate = null;
		
		boolean firstSeqNoSet = false;
		long firstSeqNo = 0;

		for (final HexFrameEntity wodFrame : wodList) {
			
			long epochTime = epoch.getReferenceTime().getTime();
			long epochSequenceNumber = epoch.getSequenceNumber();

			Long sequenceNumber = wodFrame.getSequenceNumber();
			long timeOffset = (sequenceNumber - epochSequenceNumber) * 120 * 1000;
			long baseSatelliteTime = epochTime + timeOffset;
			
			if (wodFrame.getFrameType() == 11) {
				receivedDate = wodFrame.getCreatedDate();
			}
			if (sequenceNumber != oldSeqNo) {
				if (oldSeqNo != -1) {
					if (frames.size() == 12) {
						if (!firstSeqNoSet) {
							firstSeqNo = oldSeqNo;
							firstSeqNoSet = true;
						}

						cal = Calendar.getInstance(TZ);
						cal.setTime(receivedDate);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						receivedDate = cal.getTime();

						saveWod(satelliteId, oldSeqNo, frames,
								processedHexFrames, receivedDate,
								baseSatelliteTime, 50);
					}
					frames = new ArrayList<String>();
					processedHexFrames = new ArrayList<HexFrameEntity>();
					frames.add(wodFrame.getHexString().substring(106,
							wodFrame.getHexString().length()));
					processedHexFrames.add(wodFrame);
				}

				oldSeqNo = wodFrame.getSequenceNumber();
			} else {
				frames.add(wodFrame.getHexString().substring(106,
						wodFrame.getHexString().length()));
				processedHexFrames.add(wodFrame);
			}
		}

	}

	@Transactional(readOnly = false)
	private void saveWod(long satelliteId, long oldSeqNo, List<String> frames,
			List<HexFrameEntity> processedHexFrames, Date receivedDate,
			long baseSatelliteTime, long iterations) {
		extractAndSaveWod(satelliteId, oldSeqNo, frames,
				receivedDate, baseSatelliteTime, iterations);

		for (HexFrameEntity hfe : processedHexFrames) {
			hfe.setWodProcessed(true);
			hexFrameDao.save(hfe);
		}
	}

	@Transactional(readOnly = true)
	private List<HexFrameEntity> getUnprocessedWod(long satelliteId,
			Calendar cal) {
		final List<HexFrameEntity> wodList = hexFrameDao.findUnprocessedWOD(
				satelliteId, cal.getTime());
		return wodList;
	}

	@Transactional(readOnly = true)
	private List<EpochEntity> getEpoch(long satelliteId) {
		List<EpochEntity> epochList = epochDao.findBySatelliteId(satelliteId);
		return epochList;
	}

	private void extractAndSaveWod(final Long satelliteId, final long seqNo,
			final List<String> frames, final Date receivedDate, long baseSatelliteTime,
			long iterations) {

		final Date frameTime = new Date(
				receivedDate.getTime() - 104 * 60 * 1000);

		final StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 12; i++) {
			sb.append(StringUtils.right(frames.get(i), 400));
		}

		int start = 0;
		int end = 46;

		for (int i = 0; i < 104; i++) {

			final long frameNumber = (seqNo * 104) + i;
			
			long satelliteTime = baseSatelliteTime + (i * 60 * 1000);
			
			LOG.debug("Finding: " + seqNo + ", " + i + ", " + frameNumber);
			
			boolean foundEntry = false;
			
			for (int entryCount = 0; entryCount < iterations; entryCount++) {
				if (wholeOrbitDataDao.findBySatelliteIdAndFrameNumber(satelliteId,
						frameNumber - 106 - (entryCount * 104)).size() > 0) {
					foundEntry = true;
					break;
				}
			}

			if (!foundEntry) {
				
				LOG.debug("Not Found");

				WholeOrbitDataEntity wod = null;

				switch (satelliteId.intValue()) {
				case 0:
					wod = new GomSpaceWODEntity(
							satelliteId,
							seqNo,
							frameNumber,
							convertHexBytePairToBinary(sb.substring(start, end)),
							frameTime);
					break;
				case 1:
					wod = new ClydeSpaceWODEntity(
							satelliteId,
							seqNo,
							frameNumber,
							convertHexBytePairToBinary(sb.substring(start, end)),
							frameTime);
					break;
				case 2:
					wod = new GomSpaceWODEntity(
							satelliteId,
							seqNo,
							frameNumber,
							convertHexBytePairToBinary(sb.substring(start, end)),
							frameTime);
					break;
				default:
					break;
				}

				if (wod != null) {
					//checkMinMax(satelliteId, wod);
					wod.setSatelliteTime(new Timestamp(satelliteTime));
					LOG.debug("Saving WOD with sequenceNumber, frameNumber: " + wod.getSequenceNumber() + ", " + wod.getFrameNumber());
					wholeOrbitDataDao.save(wod);
				}

				start += 46;
				end += 46;
			} else {
				LOG.debug("Found");
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
			sb.append(StringUtils.leftPad(Integer.toBinaryString(hexValue), 8,
					"0"));
		}
		return sb.toString();
	}

	private static final SimpleTimeZone TZ = new SimpleTimeZone(0, "UTC");

	/**
	 * @param wodEntity
	 */
	private void checkMinMax(long satelliteId, WholeOrbitDataEntity wodEntity) {

		for (int channel = 1; channel <= 14; channel++) {
			List<MinMaxEntity> channels = minMaxDao
					.findBySatelliteIdAndChannel(satelliteId, channel);
			if (channels.isEmpty()) {
				break;
			}
			MinMaxEntity minMaxEntity = channels.get(0);
			switch (channel) {
			case 1:
				if (wodEntity.getC9() == 0) {
					break;
				}
				if (wodEntity.getC9() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(wodEntity.getC9());
				} else if (wodEntity.getC9() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(wodEntity.getC9());
				}
				break;
			case 2:
				if (wodEntity.getC10() == 0) {
					break;
				}
				if (wodEntity.getC10() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(wodEntity.getC10());
				} else if (wodEntity.getC10() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(wodEntity.getC10());
				}
				break;
			case 3:
				if (wodEntity.getC11() == 0) {
					break;
				}
				if (wodEntity.getC11() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(wodEntity.getC11());
				} else if (wodEntity.getC11() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(wodEntity.getC11());
				}
				break;
			case 4:
				if (wodEntity.getC12() == 0) {
					break;
				}
				if (wodEntity.getC12() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(wodEntity.getC12());
				} else if (wodEntity.getC12() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(wodEntity.getC12());
				}
				break;
			case 5:
				if (wodEntity.getC13() == 0) {
					break;
				}
				if (wodEntity.getC13() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(wodEntity.getC13());
				} else if (wodEntity.getC13() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(wodEntity.getC13());
				}
				break;
			case 6:
				if (wodEntity.getC14() == 0) {
					break;
				}
				if (wodEntity.getC14() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(wodEntity.getC14());
				} else if (wodEntity.getC14() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(wodEntity.getC14());
				}
				break;
			case 11:
				if (wodEntity.getC5() == 0) {
					break;
				}
				if (wodEntity.getC5() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(wodEntity.getC5());
				} else if (wodEntity.getC5() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(wodEntity.getC5());
				}
				break;
			case 12:
				if (wodEntity.getC6() == 0) {
					break;
				}
				if (wodEntity.getC6() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(wodEntity.getC6());
				} else if (wodEntity.getC6() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(wodEntity.getC6());
				}
				break;
			case 13:
				if (wodEntity.getC7() == 0) {
					break;
				}
				if (wodEntity.getC7() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(wodEntity.getC7());
				} else if (wodEntity.getC7() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(wodEntity.getC7());
				}
				break;
			case 14:
				if (wodEntity.getC8() == 0) {
					break;
				}
				if (wodEntity.getC8() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(wodEntity.getC8());
				} else if (wodEntity.getC8() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(wodEntity.getC8());
				}
				break;
			}
			minMaxDao.save(minMaxEntity);
		}

	}

}
