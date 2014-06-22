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
public class FC2HPEntity extends HighResolutionEntity {
	
	private static Logger LOG = Logger.getLogger(FC2HPEntity.class.getName());

	public FC2HPEntity() {
		super();
	}

	public FC2HPEntity(long satelliteId, long sequenceNumber, String binaryDataString, Date createdDate) {
		setSatelliteId(satelliteId);
		setCreatedDate(createdDate);
		setSequenceNumber(sequenceNumber);
		
		long value = 0;
		
//		HRPanelVolts1 = new MultiplierTelemetryValue(4.0, this.Get8bitsAsInt(rawStream)); // note this only 8bit (so multiply by 4 to get back to 10bit equivalent)
		setC1(parseBinary(binaryDataString, 0, 8) << 2);
//        HRPanelVolts2 = new RawTelemetryValue(this.Get10bitsAsInt(rawStream));
		setC2(parseBinary(binaryDataString, 8, 10));
//        HRPanelVolts3 = new MultiplierTelemetryValue(4.0, this.Get8bitsAsInt(rawStream)); // note this only 8bit (so multiply by 4 to get back to 10bit equivalent)
		setC3(parseBinary(binaryDataString, 18, 8) << 2);
//        HRPanelVolts4 = new RawTelemetryValue(this.Get10bitsAsInt(rawStream));
		setC4(parseBinary(binaryDataString, 24, 10));
//        HRPanelVolts5 = new RawTelemetryValue(this.Get10bitsAsInt(rawStream));
		setC5(parseBinary(binaryDataString, 34, 10));
//        HRPanelVolts6 = new RawTelemetryValue(this.Get10bitsAsInt(rawStream));
		setC6(parseBinary(binaryDataString, 44, 10));
//
//        HRBatteryCurrent0 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC7(parseBinary(binaryDataString, 52, 8));
//        HRBatteryCurrent1 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC8(parseBinary(binaryDataString, 60, 8));
//        HRBatteryCurrent2 = new RawTelemetryValue(this.Get8bitsAsInt(rawStream));
		setC9(parseBinary(binaryDataString, 68, 8));
	}

	private long parseBinary(String binaryDataString, int pos, int length) {
		final String substring = binaryDataString.substring(pos, pos + length);
		final long value = Long.parseLong(
				substring, 2);
		return value;
	}

	@Transient
	public final Long getHRPanelVolts1() {
		return getC1();
	}
	
	@Transient
	public final Long getHRPanelVolts2() {
		return getC2();
	}
	
	@Transient
	public final Long getHRPanelVolts3() {
		return getC3();
	}
	
	@Transient
	public final Long getHRPanelVolts4() {
		return getC4();
	}
	
	@Transient
	public final Long getHRPanelVolts5() {
		return getC5();
	}
	
	@Transient
	public final Long getHRPanelVolts6() {
		return getC6();
	}

	@Transient
	public final Long getHRBatteryCurrent0() {
		return getC7();
	}
	
	@Transient
	public final Long getHRBatteryCurrent1() {
		return getC8();
	}
	
	@Transient
	public final Long getHRBatteryCurrent2() {
		return getC9();
	}
}