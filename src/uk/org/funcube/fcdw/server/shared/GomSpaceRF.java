package uk.org.funcube.fcdw.server.shared;



public class GomSpaceRF extends RF {

	public GomSpaceRF() {
		super();
	}

	public GomSpaceRF(long sensorId, String binaryDataString) {
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
			case 9:
				setC10(value);
				break;
			}
		}
	}

	public GomSpaceRF(long sensorId,
			long c1, long c2, long c3, long c4, long c5, long c6,long c7, long c8, long c9, long c10) {
		setSensorId(sensorId);
		setC1(c1);setC2(c2);setC3(c3);setC4(c4);setC5(c5);setC6(c6);setC7(c7);setC8(c8);setC9(c9);setC10(c10);
	}
	
	public final Long getReceiverDoppler() {
		return getC1();
	}

	public final Long getReceiverRSSI() {
		return getC2();
	}

	public final Long getTemperature() {
		return getC3();
	}

	public final Long getReceiveCurrent() {
		return getC4();
	}

	public final Long getTxCurrent3v3Bus() {
		return getC5();
	}

	public final Long getTxCurrent5v0Bus() {
		return getC6();
	}

	public final Long getForwardVoltage() {
		return getC7();
	}

	public final Long getReverseVoltage() {
		return getC8();
	}

	public final Long getBoardTemperature() {
		return getC9();
	}

	public final Long getBoardCurrent() {
		return getC10();
	}

}
