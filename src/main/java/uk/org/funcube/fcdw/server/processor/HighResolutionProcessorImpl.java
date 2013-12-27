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
import uk.org.funcube.fcdw.dao.HighResolutionDao;
import uk.org.funcube.fcdw.domain.ClydeSpaceHPEntity;
import uk.org.funcube.fcdw.domain.GomSpaceHPEntity;
import uk.org.funcube.fcdw.domain.HexFrameEntity;
import uk.org.funcube.fcdw.domain.HighResolutionEntity;


public class HighResolutionProcessorImpl implements HighResolutionProcessor {
	
	private static Logger LOG = Logger.getLogger(HighResolutionProcessorImpl.class.getName());
	
	@Autowired
	HexFrameDao hexFrameDao;
	
	@Autowired
	HighResolutionDao highResolutionDao;

	@Override
	@Transactional(readOnly=false)
	public void process(final long satelliteId) {

		Calendar cal = Calendar.getInstance(TZ);
		cal.add(Calendar.HOUR, -24);

		final List<HexFrameEntity> hexFrames = hexFrameDao.findUnprocessedHighPrecision(satelliteId, cal.getTime());
		
		List<HexFrameEntity> processedHexFrames = new ArrayList<HexFrameEntity>();

		long oldId = -1;
		long sequenceNumber;

		List<String> frames = new ArrayList<String>();

		Date receivedDate = new Date();
		
		LOG.debug("Found: " + hexFrames.size() + " HP frames to process");

		for (final HexFrameEntity hexFrame : hexFrames) {
			if (hexFrame.getFrameType() == 20) {
				receivedDate = hexFrame.getCreatedDate();
			}

			sequenceNumber = hexFrame.getSequenceNumber();

			if (sequenceNumber != oldId && oldId != -1) {
				if (frames.size() == 3) {

					cal = Calendar.getInstance(TZ);
					cal.setTime(receivedDate);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					receivedDate = cal.getTime();

					saveHighPrecision(hexFrame.getSatelliteId().intValue(), oldId, frames, receivedDate);

					for (HexFrameEntity hfe : processedHexFrames) {
						hfe.setHighPrecisionProcessed(true);
						hexFrameDao.save(hfe);
					}
				}
				frames = new ArrayList<String>();
				processedHexFrames = new ArrayList<HexFrameEntity>();
				frames.add(hexFrame.getHexString().substring(106, hexFrame.getHexString().length()));
				processedHexFrames.add(hexFrame);
			} else {
				frames.add(hexFrame.getHexString().substring(106, hexFrame.getHexString().length()));
				processedHexFrames.add(hexFrame);
			}

			oldId = sequenceNumber;
		}

	}

	private void saveHighPrecision(final int satelliteId, final long sequenceNumber, final List<String> frames, final Date receivedDate) {

		HighResolutionEntity hrEntity = null;
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < 3; i++) {
			sb.append(StringUtils.right(frames.get(i), 400));
		}
		
		final String binaryString = DataProcessor.convertHexBytePairToBinary(sb.toString());

		for (int i = 0; i < 60; i++) {
			switch (satelliteId) {
			case 0:
				hrEntity = new GomSpaceHPEntity(satelliteId, sequenceNumber, binaryString.substring(i * 80, i * 80 + 80), receivedDate);
				break;
			case 1:
				hrEntity = new ClydeSpaceHPEntity(satelliteId, sequenceNumber, binaryString.substring(i * 80, i * 80 + 80), receivedDate);
				break;
			case 2:
				hrEntity = new GomSpaceHPEntity(satelliteId, sequenceNumber, binaryString.substring(i * 80, i * 80 + 80), receivedDate);
				break;
			default:
				LOG.error("Cannot process High Precision data for satellite ID: " + satelliteId);
				break;
			}

			highResolutionDao.save(hrEntity);
		}

	}

	private static final SimpleTimeZone TZ = new SimpleTimeZone(0, "UTC");

}
