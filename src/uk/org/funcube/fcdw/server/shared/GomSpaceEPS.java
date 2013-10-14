// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;



public class GomSpaceEPS extends EPS {

	public GomSpaceEPS() {
		super();
	}

	public GomSpaceEPS(String binaryDataString) {
		super();

		int pos = 0;

		for (; pos < 128; pos += 16) {
			final long value = Long.parseLong(
					binaryDataString.substring(pos, pos + 16), 2);
			switch (pos / 16) {
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
			}
		}

		for (; pos < 192; pos += 8) {
			final long value = Long.parseLong(
					binaryDataString.substring(pos, pos + 8), 2);
			switch ((pos - 128) / 8) {
			case 0:
				setC9(value);
				break;
			case 1:
				setC10(value);
				break;
			case 2:
				setC11(value);
				break;
			case 3:
				setC12(value);
				break;
			case 4:
				setC13(value);
				break;
			case 5:
				setC14(value);
				break;
			case 6:
				setC15(value);
				break;
			case 7:
				setC16(value);
				break;
			}
		}

		for (; pos < 292; pos += 10) {
			final long value = Long.parseLong(
					binaryDataString.substring(pos, pos + 10), 2);
			switch ((pos - 192) / 10) {
			case 0:
				setC17(value);
				break;
			case 1:
				setC18(value);
				break;
			case 2:
				setC19(value);
				break;
			case 3:
				setC20(value);
				break;
			case 4:
				setC21(value);
				break;
			case 5:
				setC22(value);
				break;
			case 6:
				setC23(value);
				break;
			case 7:
				setC24(value);
				break;
			case 8:
				setC25(value);
				break;
			case 9:
				setC26(value);
				break;
			}
		}
	}

	public GomSpaceEPS(
			long c1, long c2, long c3, long c4, long c5, long c6,long c7, long c8, long c9, long c10,
			long c11, long c12, long c13, long c14, long c15, long c16,long c17, long c18, long c19, long c20,
			long c21, long c22, long c23, long c24, long c25, long c26) {
		setC1(c1);setC2(c2);setC3(c3);setC4(c4);setC5(c5);setC6(c6);setC7(c7);setC8(c8);setC9(c9);setC10(c10);
		setC11(c11);setC12(c12);setC13(c13);setC14(c14);setC15(c15);setC16(c16);setC17(c17);setC18(c18);setC19(c19);setC20(c20);
		setC21(c21);setC22(c22);setC23(c23);setC24(c24);setC25(c25);setC26(c26);
	}

	public final Long getPhotoVoltage1() {
		return getC1();
	}

	public final Long getPhotoVoltage2() {
		return getC2();
	}

	public final Long getPhotoVoltage3() {
		return getC3();
	}

	public final Long getTotalPhotoCurrent() {
		return getC4();
	}

	public final Long getBatteryVoltage() {
		return getC5();
	}

	public final Long getTotalSystemCurrent() {
		return getC6();
	}

	public final Long getRebootCount() {
		return getC7();
	}

	public final Long getEpsSoftwareErrors() {
		return getC8();
	}

	public final Long getBoostConvertorTemp1() {
		return getC9();
	}

	public final Long getBoostConvertorTemp2() {
		return getC10();
	}

	public final Long getBoostConvertorTemp3() {
		return getC11();
	}

	public final Long getBatteryTemp() {
		return getC12();
	}

	public final Long getLatchUpCount5v0() {
		return getC13();
	}

	public final Long getLatchUpCount3v3() {
		return getC14();
	}

	public final Long getResetCause() {
		return getC15();
	}

	public final Long getPowerPointTrackingMode() {
		return getC16();
	}

	public final Long getSunSensorX1() {
		return getC17();
	}

	public final Long getSunSensorY1() {
		return getC18();
	}

	public final Long getSunSensorZ1() {
		return getC19();
	}

	public final Long getSolarPanelTempX1() {
		return getC20();
	}

	public final Long getSolarPanelTempX2() {
		return getC21();
	}

	public final Long getSolarPanelTempY1() {
		return getC22();
	}

	public final Long getSolarPanelTempY2() {
		return getC23();
	}

	public final Long getBusVoltage3v3() {
		return getC24();
	}

	public final Long getBusCurrent3v3() {
		return getC25();
	}

	public final Long getBusVoltage5v0() {
		return getC26();
	}

}
