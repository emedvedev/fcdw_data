// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.jboss.logging.Logger;

@Entity
@DiscriminatorValue("fc2")
public class FC2WODEntity extends WholeOrbitDataEntity {
	
	private static Logger LOG = Logger.getLogger(WholeOrbitDataEntity.class.getName());

	@Transient
	FC2WODEntity previous;

	@Transient
	FC2WODEntity next;

	public FC2WODEntity() {

	}

	public FC2WODEntity(final long satelliteId, 
			final long sequenceNumber, final long frameNumber,
			final String binaryDataString, final Date createdDate) {
		setSatelliteId(satelliteId);
		setSequenceNumber(sequenceNumber);
		setFrameNumber(frameNumber);
		setCreatedDate(createdDate);
		
		LOG.debug("WOD binary string length: " + binaryDataString.length() + ", bytes: " + binaryDataString.length() / 8);
		
//		WOMaterialScience1 = new MultiplierOffsetTelemetryValue(-0.024, 75.244,this.Get12bitsAsInt(rawStream)); // black structure
		setC1(parseBinary(binaryDataString, 0, 12));
//        WOMaterialScience2 = new MultiplierOffsetTelemetryValue(-0.024, 74.750, this.Get12bitsAsInt(rawStream)); // silver structure
		setC2(parseBinary(binaryDataString, 12, 12));
//        WOMaterialScience3 = new MultiplierOffsetTelemetryValue(-0.024, 75.039, this.Get12bitsAsInt(rawStream)); // black strip
		setC3(parseBinary(binaryDataString, 24, 12));
//        WOMaterialScience4 = new MultiplierOffsetTelemetryValue(-0.024, 75.987, this.Get12bitsAsInt(rawStream)); // silver strip
		setC4(parseBinary(binaryDataString, 36, 12));
//
//        WOPanelTemp0 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC5(parseBinary(binaryDataString, 48, 8));
//        WOPanelTemp1 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC6(parseBinary(binaryDataString, 56, 8));
//        WOPanelTemp2 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC7(parseBinary(binaryDataString, 64, 8));
//        WOPanelTemp3 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC8(parseBinary(binaryDataString, 72, 8));
//        WOPanelTemp4 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC9(parseBinary(binaryDataString, 80, 8));
//        WOPanelTemp5 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC10(parseBinary(binaryDataString, 88, 8));
//        WOPanelTemp6 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC11(parseBinary(binaryDataString, 96, 8));
//
//        WOBatteryCurrentDir0 = new RawTelemetryValue(this.Get1bitAsInt(rawStream));
		setC27(parseBinary(binaryDataString, 104, 1) == 1L);
//        WOBatteryCurrent0 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC12(parseBinary(binaryDataString, 105, 8));
//        WOBatteryVolts0 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC13(parseBinary(binaryDataString, 113, 8));
//        WOBatteryTemp0 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC14(parseBinary(binaryDataString, 121, 8));
//
//        WOBatteryCurrentDir1 = new RawTelemetryValue(this.Get1bitAsInt(rawStream));
		setC28(parseBinary(binaryDataString, 129, 1) == 1L);
//        WOBatteryCurrent1 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC15(parseBinary(binaryDataString, 130, 8));
//        WOBatteryVolts1 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC16(parseBinary(binaryDataString, 138, 8));
//        WOBatteryTemp1 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC17(parseBinary(binaryDataString, 146, 8));
//
//        WOBatteryCurrentDir2 = new RawTelemetryValue(this.Get1bitAsInt(rawStream));
		setC29(parseBinary(binaryDataString, 154, 1) == 1L);
//        WOBatteryCurrent2 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC18(parseBinary(binaryDataString, 155, 8));
//        WOBatteryVolts2 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC20(parseBinary(binaryDataString, 163, 8));
//        WOBatteryTemp2 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC21(parseBinary(binaryDataString, 171, 8));
//        WOInSunlight = new RawTelemetryValue(this.Get1bitAsInt(rawStream));
		setC30(parseBinary(binaryDataString, 179, 1) == 1L);
//        WOManagerMode = new RawTelemetryValue(this.Get3bitsAsInt(rawStream));
		setC22(parseBinary(binaryDataString, 180, 3));
	}

	public Long getWOMaterialScience1() {
		return getC1();
	}
		
	public Long getWOMaterialScience2() {
		return getC2();
	}
	
	public Long getWOMaterialScience3() {
		return getC3();
	}
	
	public Long getWOMaterialScience4() {
		return getC4();
	}
	
	public Long getWOPanelTemp0() {
		return getC5();
	}
	
	public Long getWOPanelTemp1() {
		return getC6();
	}
	
	public Long getWOPanelTemp2() {
		return getC7();
	}
	
	public Long getWOPanelTemp3() {
		return getC8();
	}
	
	public Long getWOPanelTemp4() {
		return getC9();
	}
	
	public Long getWOPanelTemp5() {
		return getC10();
	}
	
	public Long getWOPanelTemp6() {
		return getC11();
	}
	
	public Boolean getWOBatteryCurrentDir0() {
		return getC27();
	}
	
	public Long getWOBatteryCurrent0() {
		return getC12();
	}
	
	public Long getWOBatteryVolts0() {
		return getC13();
	}
	
	public Long getWOBatteryTemp0() {
		return getC14();
	}
	
	public Boolean getWOBatteryCurrentDir1() {
		return getC28();
	}
	
	public Long getWOBatteryCurrent1() {
		return getC15();
	}
	
	public Long getWOBatteryVolts1() {
		return getC16();
	}
	
	public Long getWOBatteryTemp1() {
		return getC17();
	}
	
	public Boolean getWOBatteryCurrentDir2() {
		return getC29();
	}
	
	public Long getWOBatteryCurrent2() {
		return getC18();
	}
	
	public Long getWOBatteryVolts2() {
		return getC19();
	}
	
	public Long getWOBatteryTemp2() {
		return getC20();
	}
	
	public Boolean getWOInSunlight() {
		return getC30();
	}
	
	public Long getWOManagerMode() {
		return getC21();
	}

	private long parseBinary(String binaryDataString, int pos, int length) {
		final String substring = binaryDataString.substring(pos, pos + length);
		final long value = Long.parseLong(
				substring, 2);
		return value;
	}
}
