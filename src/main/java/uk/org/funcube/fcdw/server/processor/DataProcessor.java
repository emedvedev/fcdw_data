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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import uk.org.funcube.fcdw.dao.EpochDao;
import uk.org.funcube.fcdw.dao.HexFrameDao;
import uk.org.funcube.fcdw.dao.MinMaxDao;
import uk.org.funcube.fcdw.dao.RealTimeDao;
import uk.org.funcube.fcdw.dao.SatelliteStatusDao;
import uk.org.funcube.fcdw.dao.UserDao;
import uk.org.funcube.fcdw.domain.EpochEntity;
import uk.org.funcube.fcdw.domain.HexFrameEntity;
import uk.org.funcube.fcdw.domain.MinMaxEntity;
import uk.org.funcube.fcdw.domain.RealTimeEntity;
import uk.org.funcube.fcdw.domain.SatelliteStatusEntity;
import uk.org.funcube.fcdw.domain.UserEntity;
import uk.org.funcube.fcdw.server.shared.RealTime;
import uk.org.funcube.fcdw.server.util.Cache;
import uk.org.funcube.fcdw.server.util.Clock;
import uk.org.funcube.fcdw.server.util.UTCClock;

@Service
@RequestMapping("/api/data/hex")
public class DataProcessor {

	private static final Buffer FIFO = BufferUtils
			.synchronizedBuffer(new CircularFifoBuffer(1000));

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
				final RealTime realTime = new RealTime(satelliteId, frameType,
						sensorId, now, binaryString);
				final long sequenceNumber = realTime.getSequenceNumber();

				if (sequenceNumber != -1) {

					Long maxSequenceNumber = hexFrameDao
							.getMaxSequenceNumber(satelliteId);

					if (maxSequenceNumber != null
							&& Math.abs(sequenceNumber - maxSequenceNumber) > TWO_DAYS_SEQ_COUNT) {
						LOG.error(String
								.format("Sequence number %d is out of bounds for satelliteId %d",
										sequenceNumber, satelliteId));
						return;
					}

					final List<HexFrameEntity> frames = hexFrameDao
							.findBySatelliteIdAndSequenceNumberAndFrameType(
									satelliteId, sequenceNumber, frameType);

					if (frames != null) {
						saveUpdateHexFrame(hexString, user, frameType,
								satelliteId, now, realTime, sequenceNumber,
								frames, epochMap.get(new Long(satelliteId)));
					}
				}
			}
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void saveUpdateHexFrame(final String hexString,
			final UserEntity user, final int frameType, final int satelliteId,
			final Date now, final RealTime realTime, final long sequenceNumber,
			final List<HexFrameEntity> frames, final EpochEntity epoch) {
		HexFrameEntity hexFrame;
		Set<UserEntity> users;
		if (frames.size() == 0) {
			
			SatelliteStatusEntity satelliteStatus = satelliteStatusDao.findBySatelliteId(satelliteId).get(0);

			Timestamp satelliteTime;

			if (epoch == null) {
				satelliteTime = new Timestamp(now.getTime());
			} else {
				satelliteTime = new Timestamp(
						epoch.getReferenceTime().getTime()
								+ ((sequenceNumber - epoch.getSequenceNumber()) * 2 * 60 * 1000)
								+ (frameType * 5 * 1000));
			}

			hexFrame = new HexFrameEntity((long) satelliteId, (long) frameType,
					sequenceNumber, hexString, now, true, satelliteTime);

			users = hexFrame.getUsers();

			users.add(user);

			RealTimeEntity realTimeEntity = new RealTimeEntity(realTime,
					satelliteTime);

			hexFrameDao.save(hexFrame);

			realTimeDao.save(realTimeEntity);

			checkMinMax(satelliteId, realTimeEntity);
			
			satelliteStatus.setSequenceNumber(realTime.getSequenceNumber());
			satelliteStatus.setLastUpdated(new Timestamp(now.getTime()));
			satelliteStatus.setEclipsed(realTime.getSoftwareState().getC9());
			
			satelliteStatusDao.save(satelliteStatus);

		} else {
			hexFrame = frames.get(0);

			users = hexFrame.getUsers();

			users.add(user);

			hexFrameDao.save(hexFrame);
		}
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

}
