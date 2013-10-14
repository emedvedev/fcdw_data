// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;



public class ClydeSpaceSoftwareState extends SoftwareState {

	private static final int START = 0;

	public ClydeSpaceSoftwareState() {
		super();
	}

	public ClydeSpaceSoftwareState(String binaryDataString) {
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
			}
		}
	}

	public ClydeSpaceSoftwareState(
			boolean c1, boolean c2, boolean c3, boolean c4, boolean c5, boolean c6,boolean c7, boolean c8, boolean c9, boolean c10,
			boolean c11, boolean c12, boolean c13, boolean c14) {
		super();
		setC1(c1);setC2(c2);setC3(c3);setC4(c4);setC5(c5);setC6(c6);setC7(c7);setC8(c8);setC9(c9);setC10(c10);
		setC11(c11);setC12(c12);setC13(c13);;setC14(c14);
	}

	public final boolean isBit0() {
		return getC1();
	}

	public final boolean isBit1() {
		return getC2();
	}

	public final boolean isBit2() {
		return getC3();
	}

	public final boolean isBit3() {
		return getC4();
	}

	public final boolean isBit4() {
		return getC5();
	}

	public final boolean isBit5() {
		return getC6();
	}

	public final boolean isBit6() {
		return getC7();
	}

	public final boolean isBit7() {
		return getC8();
	}

	public final boolean isBit8() {
		return getC9();
	}

	public final boolean isBit9() {
		return getC10();
	}

	public final boolean isBit10() {
		return getC11();
	}

	public final boolean isBit11() {
		return getC12();
	}

	public final boolean isBit12() {
		return getC13();
	}

	public final boolean isBit13() {
		return getC14();
	}

}
