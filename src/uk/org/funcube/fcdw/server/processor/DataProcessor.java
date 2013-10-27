// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.processor;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.org.funcube.fcdw.dao.HexFrameDao;
import uk.org.funcube.fcdw.dao.MinMaxDao;
import uk.org.funcube.fcdw.dao.RealTimeDao;
import uk.org.funcube.fcdw.dao.UserDao;
import uk.org.funcube.fcdw.domain.HexFrameEntity;
import uk.org.funcube.fcdw.domain.MinMaxEntity;
import uk.org.funcube.fcdw.domain.RealTimeEntity;
import uk.org.funcube.fcdw.domain.User;
import uk.org.funcube.fcdw.domain.UserEntity;
import uk.org.funcube.fcdw.server.shared.RealTime;
import uk.org.funcube.fcdw.server.util.Cache;
import uk.org.funcube.fcdw.server.util.Clock;
import uk.org.funcube.fcdw.server.util.UTCClock;

@Service
@RequestMapping("/api/data/hex")
public class DataProcessor {

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

	@Transactional(readOnly = false)
	@RequestMapping(value = "/{siteId}/", method = RequestMethod.POST)
	public ResponseEntity<String> uploadData(@PathVariable String siteId,
			@RequestParam(value = "digest") String digest,
			@RequestBody String body) {

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
						&& (digest.equals(calculatedDigest) || digest
								.equals(calculatedDigestUTF8))
						|| digest.equals(calculatedDigestUTF16)) {

					hexString = StringUtils.deleteWhitespace(hexString);

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

						final List<HexFrameEntity> frames = hexFrameDao
								.findBySatelliteIdAndSequenceNumberAndFrameType(
										satelliteId, sequenceNumber, frameType);

						HexFrameEntity hexFrame = null;

						if (frames != null && frames.size() == 0) {
							hexFrame = new HexFrameEntity((long) satelliteId,
									(long) frameType, sequenceNumber,
									hexString, now, true);

							hexFrame.getUsers().add(user);

							hexFrameDao.save(hexFrame);

							RealTimeEntity realTimeEntity = new RealTimeEntity(
									realTime);

							checkMinMax(satelliteId, realTimeEntity);

							realTimeDao.save(realTimeEntity);

						} else {
							hexFrame = frames.get(0);

							hexFrame.getUsers().add(user);

							hexFrameDao.save(hexFrame);
						}
					}

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

	/**
	 * @param realTimeEntity
	 */
	private void checkMinMax(long satelliteId, RealTimeEntity realTimeEntity) {

		for (int channel = 1; channel <= 24; channel++) {
			List<MinMaxEntity> channels = minMaxDao
					.findBySatelliteIdAndChannel(satelliteId, channel);
			if (channels.isEmpty()) {
				break;
			}
			MinMaxEntity minMaxEntity = channels.get(0);
			switch (channel) {
			case 1:
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
				if (realTimeEntity.getC9() == 0) {
					break;
				}
				if (realTimeEntity.getC9() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC9());
				} else if (realTimeEntity.getC9() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC9());
				}
				break;
			case 8:
				if (realTimeEntity.getC10() == 0) {
					break;
				}
				if (realTimeEntity.getC10() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC10());
				} else if (realTimeEntity.getC10() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC10());
				}
				break;
			case 9:
				if (realTimeEntity.getC11() == 0) {
					break;
				}
				if (realTimeEntity.getC11() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC11());
				} else if (realTimeEntity.getC11() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC11());
				}
				break;
			case 10:
				if (realTimeEntity.getC12() == 0) {
					break;
				}
				if (realTimeEntity.getC12() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC12());
				} else if (realTimeEntity.getC12() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC12());
				}
				break;
			case 11:
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
				if (realTimeEntity.getC29() == null || realTimeEntity.getC29() == 0) {
					break;
				}
				if (realTimeEntity.getC29() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC29());
				} else if (realTimeEntity.getC29() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC29());
				}
				break;
			case 19:
				if (realTimeEntity.getC30() == null || realTimeEntity.getC30() == 0) {
					break;
				}
				if (realTimeEntity.getC30() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC30());
				} else if (realTimeEntity.getC30() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC30());
				}
				break;
			case 20:
				if (realTimeEntity.getC31() == null || realTimeEntity.getC31() == 0) {
					break;
				}
				if (realTimeEntity.getC31() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC31());
				} else if (realTimeEntity.getC31() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC31());
				}
				break;
			case 21:
				if (realTimeEntity.getC32() == null || realTimeEntity.getC32() == 0) {
					break;
				}
				if (realTimeEntity.getC32() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC32());
				} else if (realTimeEntity.getC32() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC32());
				}
				break;
			case 22:
				if (realTimeEntity.getC35() == null || realTimeEntity.getC35() == 0) {
					break;
				}
				if (realTimeEntity.getC35() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC35());
				} else if (realTimeEntity.getC35() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC35());
				}
				break;
			case 23:
				if (realTimeEntity.getC36() == null || realTimeEntity.getC36() == 0) {
					break;
				}
				if (realTimeEntity.getC36() < minMaxEntity.getMinimum()) {
					minMaxEntity.setMinimum(realTimeEntity.getC36());
				} else if (realTimeEntity.getC36() > minMaxEntity.getMaximum()) {
					minMaxEntity.setMaximum(realTimeEntity.getC36());
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

		private final User user;
		private final String hexString;
		private final Date createdDate;

		public UserHexString(final User user, final String hexString,
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

		public final User getUser() {
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
