// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;



public class GomSpaceSoftwareState extends SoftwareState {

	private static final int START = 0;

	public GomSpaceSoftwareState() {
		super();
	}

	public GomSpaceSoftwareState(String binaryDataString) {
		super();

		int pos = 0;
		int end = binaryDataString.length();

		for (; pos < end; pos += 1) {
			final boolean value = 
					binaryDataString.substring(pos, pos + 1).equals("1");
			switch (pos - START) {
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
			}
		}
	}

	public GomSpaceSoftwareState(
			boolean c1, boolean c2, boolean c3, boolean c4, boolean c5, boolean c6,
			boolean c7, boolean c8, boolean c9, boolean c10,
			boolean c11, boolean c12, boolean c13, boolean c14, boolean c15, boolean c16,
			boolean c17, boolean c18, boolean c19, boolean c20,
			boolean c21, boolean c22, boolean c23, boolean c24) {
		super();
		setC1(c1);setC2(c2);setC3(c3);setC4(c4);setC5(c5);setC6(c6);setC7(c7);setC8(c8);setC9(c9);setC10(c10);
		setC11(c11);setC12(c12);setC13(c13);setC14(c14);setC15(c15);setC16(c16);setC17(c17);setC18(c18);setC19(c19);
		setC20(c20);setC21(c21);setC22(c22);setC23(c23);setC24(c24);
	}
	
	public final Long commandCount() {
		return (long)(
				  (getC1() ? 1 : 0) * 32
				+ (getC2() ? 1 : 0) * 16
				+ (getC3() ? 1 : 0) * 8
				+ (getC4() ? 1 : 0) * 4
				+ (getC5() ? 1 : 0) * 2
				+ (getC6() ? 1 : 0)
				);
	}
	
	public final Long lastCommand() {
		return (long)((getC7() ? 1 : 0) * 16
				+ (getC8() ? 1 : 0) * 8
				+ (getC9() ? 1 : 0) * 4
				+ (getC10() ? 1 : 0) * 2
				+ (getC11() ? 1 : 0));
	}

	public final boolean getCommandSuccess() {
		return getC12();
	}

	public final boolean getDataValidBOB() {
		return getC13();
	}

	public final boolean getDataValidEPS() {
		return getC14();
	}

	public final boolean getDataValidPA() {
		return getC15();
	}

	public final boolean getDataValidRF() {
		return getC16();
	}

	public final boolean getDataValidMSE() {
		return getC17();
	}

	public final boolean getDataValidAntsBusA() {
		return getC18();
	}

	public final boolean getDataValidAntsBusB() {
		return getC19();
	}

	public final boolean inEclipseMode() {
		return getC20();
	}

	public final boolean inSafeMode() {
		return getC21();
	}

	public final boolean hardwareABF() {
		return getC22();
	}

	public final boolean softwareABF() {
		return getC23();
	}

	public final boolean deployWait() {
		return getC24();
	}

}
