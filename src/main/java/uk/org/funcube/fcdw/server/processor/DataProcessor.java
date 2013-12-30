// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.processor;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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

import uk.org.funcube.fcdw.dao.HexFrameDao;
import uk.org.funcube.fcdw.dao.MinMaxDao;
import uk.org.funcube.fcdw.dao.RealTimeDao;
import uk.org.funcube.fcdw.dao.UserDao;
import uk.org.funcube.fcdw.domain.HexFrameEntity;
import uk.org.funcube.fcdw.domain.MinMaxEntity;
import uk.org.funcube.fcdw.domain.RealTimeEntity;
import uk.org.funcube.fcdw.domain.UserEntity;
import uk.org.funcube.fcdw.server.shared.RealTime;
import uk.org.funcube.fcdw.server.util.Cache;
import uk.org.funcube.fcdw.server.util.Clock;
import uk.org.funcube.fcdw.server.util.UTCClock;

@Service
@RequestMapping("/api/data/hex")
public class DataProcessor {
	
	private static final Buffer FIFO = BufferUtils.synchronizedBuffer(new CircularFifoBuffer(1000));
	
	long TWO_DAYS_SEQ_COUNT = 14400;

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
					
					FIFO.add(new UserHexString(user, StringUtils.deleteWhitespace(hexString), now));
					//LOG.debug("Writing fifo, size:" + FIFO.size() + " , element count: " + FIFO.toArray().length);



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
		//LOG.debug("Reading fifo, size:" + FIFO.size() + " , element count: " + FIFO.toArray().length);
		if (!FIFO.isEmpty()) {
			final Iterator<UserHexString> iter = FIFO.iterator();
			while (iter.hasNext()){
				final UserHexString userHexString = (UserHexString)iter.next();
				iter.remove();
				
				final String hexString = userHexString.getHexString();
				final UserEntity user = userHexString.getUser();
				final Date createdDate = userHexString.getCreatedDate();
				
				final int frameId = Integer.parseInt(
				hexString.substring(0, 2), 16);
				
				final int frameType = frameId & 63;
				final int satelliteId = (frameId & (128 + 64)) >> 6;
				final int sensorId = frameId % 2;
				final String binaryString = convertHexBytePairToBinary(hexString
						.substring(2, hexString.length()));
				final Date now = clock.currentDate();
				final RealTime realTime = new RealTime(satelliteId,
						frameType, sensorId, now, binaryString);
				final long sequenceNumber = realTime.getSequenceNumber();
		
				if (sequenceNumber != -1) {
					
					Long maxSequenceNumber = hexFrameDao.getMaxSequenceNumber(satelliteId);
					
					
					if (maxSequenceNumber != null && Math.abs(sequenceNumber - maxSequenceNumber) > TWO_DAYS_SEQ_COUNT) {
						LOG.error(String
								.format("Sequence number %d is out of bounds for satelliteId %d",
										sequenceNumber, satelliteId));
						return;
					}
					
					final List<HexFrameEntity> frames = hexFrameDao
							.findBySatelliteIdAndSequenceNumberAndFrameType(
									satelliteId, sequenceNumber, frameType);
		
					HexFrameEntity hexFrame = null;
					
					Set<UserEntity> users = new HashSet<UserEntity>();
		
					if (frames != null) {
						saveUpdateHexFrame(hexString, user, frameType,
								satelliteId, now, realTime, sequenceNumber,
								frames);
					}
				}
			}
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void saveUpdateHexFrame(final String hexString,
			final UserEntity user, final int frameType, final int satelliteId,
			final Date now, final RealTime realTime, final long sequenceNumber,
			final List<HexFrameEntity> frames) {
		HexFrameEntity hexFrame;
		Set<UserEntity> users;
		if (frames.size() == 0) {

			hexFrame = new HexFrameEntity(
					(long) satelliteId, (long) frameType,
					sequenceNumber, hexString, now, true);
			
			users = hexFrame.getUsers();

			users.add(user);

			RealTimeEntity realTimeEntity = new RealTimeEntity(
					realTime);

			hexFrameDao.save(hexFrame);

			realTimeDao.save(realTimeEntity);

			checkMinMax(satelliteId, realTimeEntity);

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

		for (int channel = 1; channel <= 27; channel++) {
			List<MinMaxEntity> channels = minMaxDao
					.findBySatelliteIdAndChannel(satelliteId, channel);
			if (channels.isEmpty()) {
				break;
			}
			MinMaxEntity minMaxEntity = channels.get(0);
			Long c9 = realTimeEntity.getC9();
			Long c10 = realTimeEntity.getC10();
			Long c11 = realTimeEntity.getC11();
			Long c12 = realTimeEntity.getC12();
			switch (channel) {
			case 1:
				// Solar Panel Voltage 1
				if (realTimeEntity.getC1() == 0) {
					break;
				}
				if (realTimeEntity.getC1() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC1());
				} else if (realTimeEntity.getC1() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC1());
				}
				break;
			case 2:
				// Solar Panel Voltage 2
				if (realTimeEntity.getC2() == 0) {
					break;
				}
				if (realTimeEntity.getC2() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC2());
				} else if (realTimeEntity.getC2() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC2());
				}
				break;
			case 3:
				// Solar Panel Voltage 3
				if (realTimeEntity.getC3() == 0) {
					break;
				}
				if (realTimeEntity.getC3() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC3());
				} else if (realTimeEntity.getC3() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC3());
				}
				break;
			case 4:
				// Total Photo current
				if (realTimeEntity.getC4() == 0) {
					break;
				}
				if (realTimeEntity.getC4() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC4());
				} else if (realTimeEntity.getC4() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC4());
				}
				break;
			case 5:
				// Battery voltage
				if (realTimeEntity.getC5() == 0) {
					break;
				}
				if (realTimeEntity.getC5() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC5());
				} else if (realTimeEntity.getC5() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC5());
				}
				break;
			case 6:
				// Total system current
				if (realTimeEntity.getC6() == 0) {
					break;
				}
				if (realTimeEntity.getC6() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC6());
				} else if (realTimeEntity.getC6() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC6());
				}
				break;
			case 7:
				// Boost converter temp 1
				if (c9 == 0) {
					break;
				}
				if (c9 > 250) {
					c9 = c9 - 255;
				}
				if (c9 < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(c9);
				} else if (c9 > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(c9);
				}
				break;
			case 8:
				// Boost converter temp 2
				if (c10 == 0) {
					break;
				}
				if (c10 > 250) {
					c10 = c10 - 255;
				}
				if (c10 < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(c10);
				} else if (c10 > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(c10);
				}
				break;
			case 9:
				// Boost converter temp 3
				if (c11 == 0) {
					break;
				}
				if (c11 > 250) {
					c11 = c11 - 255;
				}
				if (c11 < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(c11);
				} else if (c11 > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(c11);
				}
				break;
			case 10:
				// Battery temp
				if (c12 == 0) {
					break;
				}
				if (c12 > 250) {
					c12 = c12 - 255;
				}
				if (c12 < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(c12);
				} else if (c12 > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(c12);
				}
				break;
			case 11:
				// Solar panel temp X+
				if (realTimeEntity.getC20() == 0) {
					break;
				}
				if (realTimeEntity.getC20() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC20());
				} else if (realTimeEntity.getC20() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC20());
				}
				break;
			case 12:
				// Solar panel temp X-
				if (realTimeEntity.getC21() == 0) {
					break;
				}
				if (realTimeEntity.getC21() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC21());
				} else if (realTimeEntity.getC21() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC21());
				}
				break;
			case 13:
				// Solar panel temp Y+
				if (realTimeEntity.getC22() == 0) {
					break;
				}
				if (realTimeEntity.getC22() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC22());
				} else if (realTimeEntity.getC22() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC22());
				}
				break;
			case 14:
				// Solar panel temp Y-
				if (realTimeEntity.getC23() == 0) {
					break;
				}
				if (realTimeEntity.getC23() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC23());
				} else if (realTimeEntity.getC23() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC23());
				}
				break;
			case 15:
				// 3.3 bus voltage
				if (realTimeEntity.getC24() == 0) {
					break;
				}
				if (realTimeEntity.getC24() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC24());
				} else if (realTimeEntity.getC24() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC24());
				}
				break;
			case 16:
				// 3.3 bus current
				if (realTimeEntity.getC25() == 0) {
					break;
				}
				if (realTimeEntity.getC25() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC25());
				} else if (realTimeEntity.getC25() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC25());
				}
				break;
			case 17:
				// 5.0 bus voltage
				if (realTimeEntity.getC26() == 0) {
					break;
				}
				if (realTimeEntity.getC26() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC26());
				} else if (realTimeEntity.getC26() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC26());
				}
				break;
			case 18:
				// Receive temperature
				if (realTimeEntity.getC34() == null
						|| realTimeEntity.getC34() == 0) {
					break;
				}
				if (realTimeEntity.getC34() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC34());
				} else if (realTimeEntity.getC34() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC34());
				}
				break;
			case 19:
				// Receive current
				if (realTimeEntity.getC35() == null
						|| realTimeEntity.getC35() == 0) {
					break;
				}
				if (realTimeEntity.getC35() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC35());
				} else if (realTimeEntity.getC35() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC35());
				}
				break;
			case 20:
				// Transmit current 3.3V bus
				if (realTimeEntity.getC36() == null
						|| realTimeEntity.getC36() == 0) {
					break;
				}
				if (realTimeEntity.getC36() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC36());
				} else if (realTimeEntity.getC36() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC36());
				}
				break;
			case 21:
				// Transmit current 5V bus
				if (realTimeEntity.getC37() == null
						|| realTimeEntity.getC37() == 0) {
					break;
				}
				if (realTimeEntity.getC37() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC37());
				} else if (realTimeEntity.getC37() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC37());
				}
				break;
			case 22:
				// Forward Power
				if (realTimeEntity.getC38() == null
						|| realTimeEntity.getC38() == 0) {
					break;
				}
				if (realTimeEntity.getC38() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC38());
				} else if (realTimeEntity.getC38() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC38());
				}
				break;
			case 23:
				// Reflected Power
				if (realTimeEntity.getC39() == null
						|| realTimeEntity.getC39() == 0) {
					break;
				}
				if (realTimeEntity.getC39() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC39());
				} else if (realTimeEntity.getC39() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC39());
				}
				break;
			case 24:
				// PA Board temperature
				if (realTimeEntity.getC40() == null
						|| realTimeEntity.getC40() == 0) {
					break;
				}
				if (realTimeEntity.getC40() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC40());
				} else if (realTimeEntity.getC40() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC40());
				}
				break;
			case 25:
				// PA Board current
				if (realTimeEntity.getC41() == null
						|| realTimeEntity.getC39() == 0) {
					break;
				}
				if (realTimeEntity.getC41() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC41());
				} else if (realTimeEntity.getC41() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC41());
				}
				break;
			case 26:
				// ANTS Temp 0
				if (realTimeEntity.getC42() == null
						|| realTimeEntity.getC42() == 0) {
					break;
				}
				if (realTimeEntity.getC42() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC42());
				} else if (realTimeEntity.getC42() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC42());
				}
				break;
			case 27:
				// ANTS Temp 1
				if (realTimeEntity.getC43() == null
						|| realTimeEntity.getC43() == 0) {
					break;
				}
				if (realTimeEntity.getC43() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC43());
				} else if (realTimeEntity.getC43() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC43());
				}
				break;
			}
			minMaxDao.save(minMaxEntity);
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
