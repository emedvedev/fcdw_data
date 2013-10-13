package uk.org.funcube.fcdw.server.shared;



public class ClydeSpaceRF extends RF {

	public ClydeSpaceRF() {
		super();
	}

	public ClydeSpaceRF(long sensorId, String binaryDataString) {
		setSensorId(sensorId);
		
		int pos = 0;
		int end = binaryDataString.length();

		for (; pos < end; pos += 8) {
			final long value = Long.parseLong(
					binaryDataString.substring(pos, pos + 8), 2);
			switch (pos / 8) {
			case 0:
				setC1(value);
				break;
			case 1:
				setC2(value);
				break;
			case 2:
				setC3(value);
				break;
			case 3:
				setC4(value);
				break;
			case 4:
				setC5(value);
				break;
			case 5:
				setC6(value);
				break;
			case 6:
				setC7(value);
				break;
			case 7:
				setC8(value);
				break;
			case 8:
				setC9(value);
				break;
			}

		}
	}

	public ClydeSpaceRF(long sensorId,
			long c1, long c2, long c3, long c4, long c5, long c6,long c7, long c8, long c9) {
		setSensorId(sensorId);
		setC1(c1);setC2(c2);setC3(c3);setC4(c4);setC5(c5);setC6(c6);setC7(c7);setC8(c8);setC9(c9);
	}

	/**
	 * @return the receiverRSSI
	 */
	public final Long getReceiverRSSI() {
		return getC1();
	}

	/**
	 * @return the receiverDoppler
	 */
	public final Long getReceiverDoppler() {
		return getC2();
	}

	/**
	 * @return the transponderAGC
	 */
	public final Long getTransponderAGC() {
		return getC3();
	}

	/**
	 * @return the rxCurrent
	 */
	public final Long getRxCurrent() {
		return getC4();
	}

	/**
	 * @return the txCurrent
	 */
	public final Long getTxCurrent() {
		return getC5();
	}

	/**
	 * @return the paForwardVoltage
	 */
	public final Long getPaForwardVoltage() {
		return getC6();
	}

	/**
	 * @return the paReverseVoltage
	 */
	public final Long getPaReverseVoltage() {
		return getC7();
	}

	/**
	 * @return the paTemperature
	 */
	public final Long getPaTemperature() {
		return getC8();
	}

	/**
	 * @return the paCurrent
	 */
	public final Long getPaCurrent() {
		return getC9();
	}

	/**
	 * @return the paCurrent
	 */
	public final Long getBoardCurrent() {
		return getC10();
	}
}
