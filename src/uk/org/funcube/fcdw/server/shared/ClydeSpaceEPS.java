// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;



public class ClydeSpaceEPS extends EPS {

	public ClydeSpaceEPS() {
		super();
	}

	public ClydeSpaceEPS(String binaryDataString) {
		super();

		for (int pos = 0; pos < binaryDataString.length(); pos += 10) {
			final long value = Long.parseLong(
					binaryDataString.substring(pos, pos + 10), 2);
			switch (pos / 10) {
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
			case 10:
				setC11(value);
				break;
			case 11:
				setC12(value);
				break;
			case 12:
				setC13(value);
				break;
			case 13:
				setC14(value);
				break;
			case 14:
				setC15(value);
				break;
			case 15:
				setC16(value);
				break;
			case 16:
				setC17(value);
				break;
			case 17:
				setC18(value);
				break;
			case 18:
				setC19(value);
				break;
			case 19:
				setC20(value);
				break;
			case 20:
				setC21(value);
				break;
			case 21:
				setC22(value);
				break;
			case 22:
				setC23(value);
				break;
			case 23:
				setC24(value);
				break;
			case 24:
				setC25(value);
				break;
			case 25:
				setC26(value);
				break;
			case 26:
				setC27(value);
				break;
			case 27:
				setC28(value);
				break;
			case 28:
				setC29(value);
				break;
			case 29:
				setC30(value);
				break;
			case 30:
				setC31(value);
				break;
			}

		}
	}

	public ClydeSpaceEPS(
			long c1, long c2, long c3, long c4, long c5, long c6,long c7, long c8, long c9, long c10,
			long c11, long c12, long c13, long c14, long c15, long c16,long c17, long c18, long c19, long c20,
			long c21, long c22, long c23, long c24, long c25, long c26,long c27, long c28, long c29, long c30, long c31) {
		setC1(c1);setC2(c2);setC3(c3);setC4(c4);setC5(c5);setC6(c6);setC7(c7);setC8(c8);setC9(c9);setC10(c10);
		setC11(c11);setC12(c12);setC13(c13);setC14(c14);setC15(c15);setC16(c16);setC17(c17);setC18(c18);setC19(c19);setC20(c20);
		setC21(c21);setC22(c22);setC23(c23);setC24(c24);setC25(c25);setC26(c26);setC27(c27);setC28(c28);setC29(c29);setC30(c30);setC31(c31);
	}

	public final Long getPanelY1Voltage() {
		return getC1();
	}

	public final Long getPanelY1Current() {
		return getC2();
	}

	public final Long getPanelY1Temperature() {
		return getC3();
	}

	public final Long getPanelX2Voltage() {
		return getC4();
	}

	public final Long getPanelX2Current() {
		return getC5();
	}

	public final Long getPanelX2Temperature() {
		return getC6();
	}

	public final Long getPanelX1Voltage() {
		return getC7();
	}

	public final Long getPanelX1Current() {
		return getC8();
	}

	public final Long getPanelX1Temperature() {
		return getC9();
	}

	public final Long getPanelZ1Voltage() {
		return getC10();
	}

	public final Long getPanelZ1Current() {
		return getC11();
	}

	public final Long getPanelZ1Temperature() {
		return getC12();
	}

	public final Long getPanelY2Voltage() {
		return getC13();
	}

	public final Long getPanelY2Current() {
		return getC14();
	}

	public final Long getPanelY2Temperature() {
		return getC15();
	}

	public final Long getPanelZ2Voltage() {
		return getC16();
	}

	public final Long getBatteryBusCurrent() {
		return getC17();
	}

	public final Long getBattery2Temperature() {
		return getC18();
	}

	public final Long getBattery2Voltage() {
		return getC19();
	}

	public final Long getCell2Voltage() {
		return getC20();
	}

	public final Long getBattery2CurrentDirection() {
		return getC21();
	}

	public final Long getBattery2Current() {
		return getC22();
	}

	public final Long getBattery1Temperature() {
		return getC23();
	}

	public final Long getBattery1Voltage() {
		return getC24();
	}

	public final Long getCell1Voltage() {
		return getC25();
	}

	public final Long getFiveVoltBusCurrent() {
		return getC26();
	}

	public final Long getThreeVoltBusCurrent() {
		return getC27();
	}

	public final Long getBattery1CurrentDirection() {
		return getC28();
	}

	public final Long getBattery1Current() {
		return getC29();
	}

	public final Long getPanelZ2Temperature() {
		return getC30();
	}

	public final Long getPanelZ2Current() {
		return getC31();
	}
}
