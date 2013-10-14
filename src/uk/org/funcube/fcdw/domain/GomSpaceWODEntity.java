// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("gomSpace")
public class GomSpaceWODEntity extends WholeOrbitDataEntity {

	public GomSpaceWODEntity() {
		super();
	}

	public GomSpaceWODEntity(final long satelliteId, 
			final long sequenceNumber, final long frameNumber, final long c1,
			final long c2, final long c3, final long c4, final long c5,
			final long c6, final long c7, final long c8, final long c9,
			final long c10, final long c11, final long c12, final long c13,
			final long c14, final Date createdDate) {
		setSatelliteId(satelliteId);
		setSequenceNumber(sequenceNumber);
		setFrameNumber(frameNumber);
		setC1(c1);
		setC2(c2);
		setC3(c3);
		setC4(c4);
		setC5(c5);
		setC6(c6);
		setC7(c7);
		setC8(c8);
		setC9(c9);
		setC10(c10);
		setC11(c11);
		setC12(c12);
		setC13(c13);
		setC14(c14);
		setCreatedDate(createdDate);
	}

	public GomSpaceWODEntity(final long satelliteId, 
			final long sequenceNumber, final long frameNumber,
			final String binaryDataString, final Date createdDate) {
        setSatelliteId(satelliteId);
        setSequenceNumber(sequenceNumber);
		setCreatedDate(createdDate);
		setFrameNumber(frameNumber);

		int pos = 0;

		for (; pos < 48; pos += 12) {
			final long value = Long.parseLong(
					binaryDataString.substring(pos, pos + 12), 2);
			switch (pos / 12) {
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
			}
		}

		int start = pos;

		for (; pos < 88; pos += 10) {
			final long value = Long.parseLong(
					binaryDataString.substring(pos, pos + 10), 2);
			switch ((pos - start) / 10) {
			case 0:
				setC5(value);
				break;
			case 1:
				setC6(value);
				break;
			case 2:
				setC7(value);
				break;
			case 3:
				setC8(value);
				break;
			}
		}

		start = pos;

		for (; pos < 176; pos += 16) {
			final long value = Long.parseLong(
					binaryDataString.substring(pos, pos + 16), 2);
			switch ((pos - start) / 16) {
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
			}
		}
	}

	public long getBatteryVoltage() {
		return getC13();
	}

	public long getMaterialScience1() {
		return getC1();
	}

	public long getMaterialScience2() {
		return getC2();
	}

	public long getMaterialScience3() {
		return getC3();
	}

	public long getMaterialScience4() {
		return getC4();
	}

	public long getPanelCurrent() {
		return getC12();
	}

	public long getPanelTempX1() {
		return getC5();
	}

	public long getPanelTempX2() {
		return getC6();
	}

	public long getPanelTempY1() {
		return getC7();
	}

	public long getPanelTempY2() {
		return getC8();
	}

	public long getPanelVolt1() {
		return getC9();
	}

	public long getPanelVolt2() {
		return getC10();
	}

	public long getPanelVolt3() {
		return getC11();
	}

	public long getSystemCurrent() {
		return getC14();
	}
}
