package uk.org.funcube.fcdw.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("clydeSpace")
public class ClydeSpaceHPEntity extends HighResolutionEntity {

	public ClydeSpaceHPEntity() {
	}

	public ClydeSpaceHPEntity(long satelliteId, long sequenceNumber, String binaryDataString, Date createdDate) {
		setSatelliteId(satelliteId);
		setCreatedDate(createdDate);
		setSequenceNumber(sequenceNumber);
		
		int pos = 0;

		for (; pos < 60; pos += 10) {
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
			}

		}

		for (; pos < 60 + 18; pos += 9) {
			final long value = Long.parseLong(
					binaryDataString.substring(pos, pos + 9), 2);
			switch ((pos - 60) / 9) {
			case 0:
				setC7(value);
				break;
			case 1:
				setC8(value);
				break;
			}
		}

		for (; pos < 60 + 18 + 2; pos++) {
			final long value = Long.parseLong(
					binaryDataString.substring(pos, pos + 1), 2);
			switch ((pos - 60 - 18)) {
			case 0:
				setC9(value);
				break;
			case 1:
				setC10(value);
				break;
			}
		}
	}

	public ClydeSpaceHPEntity(
			long c1, long c2, long c3, long c4, long c5, long c6,long c7, long c8, long c9, long c10) {
		setC1(c1);setC2(c2);setC3(c3);setC4(c4);setC5(c5);setC6(c6);setC7(c7);setC8(c8);setC9(c9);setC10(c10);
	}

	public Long getPanelX1Voltage() {
		return getC1();
	}
	
	public Long getPanelY1Voltage() {
		return getC2();
	}
	
	public Long getPanelZ1Voltage() {
		return getC3();
	}
	
	public Long getPanelX2Voltage() {
		return getC4();
	}
	
	public Long getPanelY2Voltage() {
		return getC5();
	}
	
	public Long getPanelZ2Voltage() {
		return getC6();
	}
	
	public Long getBattery1Current() {
		return getC7();
	}
	
	public Long getBattery2Current() {
		return getC8();
	}
	
	public Long getBattery1CurrentDirection() {
		return getC9();
	}
	
	public Long getBattery2CurrentDirection() {
		return getC10();
	}
}
