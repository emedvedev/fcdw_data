// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import uk.org.funcube.fcdw.server.shared.Antenna;
import uk.org.funcube.fcdw.server.shared.DTMF;
import uk.org.funcube.fcdw.server.shared.EPS;
import uk.org.funcube.fcdw.server.shared.RF;
import uk.org.funcube.fcdw.server.shared.RealTime;
import uk.org.funcube.fcdw.server.shared.SoftwareState;


@Entity
@Table(name = "RealTime")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class RealTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private long satelliteId;
	private long sequenceNumber;
	private long frameType;
	private Date createdDate;
	private long sensorId;
	private long commandCount;
	private long lastCommand;
	private Timestamp satelliteTime;

	private Long c1;
	private Long c2;
	private Long c3;
	private Long c4;
	private Long c5;
	private Long c6;
	private Long c7;
	private Long c8;
	private Long c9;
	private Long c10;
	private Long c11;
	private Long c12;
	private Long c13;
	private Long c14;
	private Long c15;
	private Long c16;
	private Long c17;
	private Long c18;
	private Long c19;
	private Long c20;
	private Long c21;
	private Long c22;
	private Long c23;
	private Long c24;
	private Long c25;
	private Long c26;
	private Long c27;
	private Long c28;
	private Long c29;
	private Long c30;
	private Long c31;
	private Long c32;
	private Long c33;
	private Long c34;
	private Long c35;
	private Long c36;
	private Long c37;
	private Long c38;
	private Long c39;
	private Long c40;
	private Long c41;
	private Long c42;
	private Long c43;
	private Boolean c44;
	private Boolean c45;
	private Boolean c46;
	private Boolean c47;
	private Boolean c48;
	private Boolean c49;
	private Boolean c50;
	private Boolean c51;
	private Boolean c52;
	private Boolean c53;
	private Boolean c54;
	private Boolean c55;
	private Boolean c56;
	private Boolean c57;
	private Boolean c58;
	private Boolean c59;
	private Boolean c60;
	private Boolean c61;
	private Boolean c62;
	private Boolean c63;
	private Boolean c64;
	private Boolean c65;
	private Boolean c66;
	private Boolean c67;
	private Boolean c68;
	private Boolean c69;
	private Boolean c70;
	private Boolean c71;
	
	@Transient
	public final Long[] getLongValues() {
		return new Long[] {
		    c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,
		    c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,
		    c21,c22,c23,c24,c25,c26,c27,c28,c29,c30,
		    c31,c32,c33,c34,c35,c36,c37,c38,c39,c40,
		    c41,c42,c43
		};
	}

	public RealTimeEntity() {
		// TODO Auto-generated constructor stub
	}

	public RealTimeEntity(RealTime realTime, Timestamp satelliteTime) {
		this.satelliteTime = satelliteTime;
		satelliteId = realTime.getSatelliteId();
		sequenceNumber = realTime.getSequenceNumber();
		frameType = realTime.getFrameType();
		sensorId = realTime.getSensorId();
		createdDate = realTime.getCreatedDate();
		EPS eps = realTime.getEps();
		c1 = eps.getC1();
		c2 = eps.getC2();
		c3 = eps.getC3();
		c4 = eps.getC4();
		c5 = eps.getC5();
		c6 = eps.getC6();
		c7 = eps.getC7();
		c8 = eps.getC8();
		c9 = eps.getC9();
		c10 = eps.getC10();
		c11 = eps.getC11();
		c12 = eps.getC12();
		c13 = eps.getC13();
		c14 = eps.getC14();
		c15 = eps.getC15();
		c16 = eps.getC16();
		c17 = eps.getC17();
		c18 = eps.getC18();
		c19 = eps.getC19();
		c20 = eps.getC20();
		c21 = eps.getC21();
		c22 = eps.getC22();
		c23 = eps.getC23();
		c24 = eps.getC24();
		c25 = eps.getC25();
		c26 = eps.getC26();
		c27 = eps.getC27();
		c28 = eps.getC28();
		c29 = eps.getC29();
		c30 = eps.getC30();
		c31 = eps.getC31();
		RF rf = realTime.getRf();
		c32 = rf.getC1();
		c33 = rf.getC2();
		c34 = rf.getC3();
		c35 = rf.getC4();
		c36 = rf.getC5();
		c37 = rf.getC6();
		c38 = rf.getC7();
		c39 = rf.getC8();
		c40 = rf.getC9();
		c41 = rf.getC10();
		Antenna antenna = realTime.getAntenna();
		c42 = antenna.getAntennaTemp0();
		c43 = antenna.getAntennaTemp1();
		c44 = antenna.isAntennaDeployment0();
		c45 = antenna.isAntennaDeployment1();
		c46 = antenna.isAntennaDeployment2();
		c47 = antenna.isAntennaDeployment3();
		SoftwareState softwareState = realTime.getSoftwareState();
		c48 = softwareState.getC1();
		c49 = softwareState.getC2();
		c50 = softwareState.getC3();
		c51 = softwareState.getC4();
		c52 = softwareState.getC5();
		c53 = softwareState.getC6();
		c54 = softwareState.getC7();
		c55 = softwareState.getC8();
		c56 = softwareState.getC9();
		c57 = softwareState.getC10();
		c58 = softwareState.getC11();
		c59 = softwareState.getC12();
		c60 = softwareState.getC13();
		c61 = softwareState.getC14();
		c62 = softwareState.getC15();
		c63 = softwareState.getC16();
		c64 = softwareState.getC17();
		c65 = softwareState.getC18();
		c66 = softwareState.getC19();
		c67 = softwareState.getC20();
		c68 = softwareState.getC21();
		c69 = softwareState.getC22();
		c70 = softwareState.getC23();
		c71 = softwareState.getC24();
		DTMF dtmf = realTime.getDTMF();
		commandCount = dtmf.getCommandCount();
		lastCommand = dtmf.getLastCommand();
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final long getSatelliteId() {
		return satelliteId;
	}

	public final void setSatelliteId(long satelliteId) {
		this.satelliteId = satelliteId;
	}

	public final long getSequenceNumber() {
		return sequenceNumber;
	}

	public final void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public final long getFrameType() {
		return frameType;
	}

	public final void setFrameType(long frameType) {
		this.frameType = frameType;
	}

	public final Date getCreatedDate() {
		return createdDate;
	}

	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public final long getSensorId() {
		return sensorId;
	}

	public final void setSensorId(long sensorId) {
		this.sensorId = sensorId;
	}

	public final long getCommandCount() {
		return commandCount;
	}

	public final void setCommandCount(long commandCount) {
		this.commandCount = commandCount;
	}

	public final long getLastCommand() {
		return lastCommand;
	}

	public final void setLastCommand(long lastCommand) {
		this.lastCommand = lastCommand;
	}

	public final Long getC1() {
		return c1;
	}

	public final void setC1(Long c1) {
		this.c1 = c1;
	}

	public final Long getC2() {
		return c2;
	}

	public final void setC2(Long c2) {
		this.c2 = c2;
	}

	public final Long getC3() {
		return c3;
	}

	public final void setC3(Long c3) {
		this.c3 = c3;
	}

	public final Long getC4() {
		return c4;
	}

	public final void setC4(Long c4) {
		this.c4 = c4;
	}

	public final Long getC5() {
		return c5;
	}

	public final void setC5(Long c5) {
		this.c5 = c5;
	}

	public final Long getC6() {
		return c6;
	}

	public final void setC6(Long c6) {
		this.c6 = c6;
	}

	public final Long getC7() {
		return c7;
	}

	public final void setC7(Long c7) {
		this.c7 = c7;
	}

	public final Long getC8() {
		return c8;
	}

	public final void setC8(Long c8) {
		this.c8 = c8;
	}

	public final Long getC9() {
		return c9;
	}

	public final void setC9(Long c9) {
		this.c9 = c9;
	}

	public final Long getC10() {
		return c10;
	}

	public final void setC10(Long c10) {
		this.c10 = c10;
	}

	public final Long getC11() {
		return c11;
	}

	public final void setC11(Long c11) {
		this.c11 = c11;
	}

	public final Long getC12() {
		return c12;
	}

	public final void setC12(Long c12) {
		this.c12 = c12;
	}

	public final Long getC13() {
		return c13;
	}

	public final void setC13(Long c13) {
		this.c13 = c13;
	}

	public final Long getC14() {
		return c14;
	}

	public final void setC14(Long c14) {
		this.c14 = c14;
	}

	public final Long getC15() {
		return c15;
	}

	public final void setC15(Long c15) {
		this.c15 = c15;
	}

	public final Long getC16() {
		return c16;
	}

	public final void setC16(Long c16) {
		this.c16 = c16;
	}

	public final Long getC17() {
		return c17;
	}

	public final void setC17(Long c17) {
		this.c17 = c17;
	}

	public final Long getC18() {
		return c18;
	}

	public final void setC18(Long c18) {
		this.c18 = c18;
	}

	public final Long getC19() {
		return c19;
	}

	public final void setC19(Long c19) {
		this.c19 = c19;
	}

	public final Long getC20() {
		return c20;
	}

	public final void setC20(Long c20) {
		this.c20 = c20;
	}

	public final Long getC21() {
		return c21;
	}

	public final void setC21(Long c21) {
		this.c21 = c21;
	}

	public final Long getC22() {
		return c22;
	}

	public final void setC22(Long c22) {
		this.c22 = c22;
	}

	public final Long getC23() {
		return c23;
	}

	public final void setC23(Long c23) {
		this.c23 = c23;
	}

	public final Long getC24() {
		return c24;
	}

	public final void setC24(Long c24) {
		this.c24 = c24;
	}

	public final Long getC25() {
		return c25;
	}

	public final void setC25(Long c25) {
		this.c25 = c25;
	}

	public final Long getC26() {
		return c26;
	}

	public final void setC26(Long c26) {
		this.c26 = c26;
	}

	public final Long getC27() {
		return c27;
	}

	public final void setC27(Long c27) {
		this.c27 = c27;
	}

	public final Long getC28() {
		return c28;
	}

	public final void setC28(Long c28) {
		this.c28 = c28;
	}

	public final Long getC29() {
		return c29;
	}

	public final void setC29(Long c29) {
		this.c29 = c29;
	}

	public final Long getC30() {
		return c30;
	}

	public final void setC30(Long c30) {
		this.c30 = c30;
	}

	public final Long getC31() {
		return c31;
	}

	public final void setC31(Long c31) {
		this.c31 = c31;
	}

	public final Long getC32() {
		return c32;
	}

	public final void setC32(Long c32) {
		this.c32 = c32;
	}

	public final Long getC33() {
		return c33;
	}

	public final void setC33(Long c33) {
		this.c33 = c33;
	}

	public final Long getC34() {
		return c34;
	}

	public final void setC34(Long c34) {
		this.c34 = c34;
	}

	public final Long getC35() {
		return c35;
	}

	public final void setC35(Long c35) {
		this.c35 = c35;
	}

	public final Long getC36() {
		return c36;
	}

	public final void setC36(Long c36) {
		this.c36 = c36;
	}

	public final Long getC37() {
		return c37;
	}

	public final void setC37(Long c37) {
		this.c37 = c37;
	}

	public final Long getC38() {
		return c38;
	}

	public final void setC38(Long c38) {
		this.c38 = c38;
	}

	public final Long getC39() {
		return c39;
	}

	public final void setC39(Long c39) {
		this.c39 = c39;
	}

	public final Long getC40() {
		return c40;
	}

	public final void setC40(Long c40) {
		this.c40 = c40;
	}

	public final Long getC41() {
		return c41;
	}

	public final void setC41(Long c41) {
		this.c41 = c41;
	}

	public final Long getC42() {
		return c42;
	}

	public final void setC42(Long c42) {
		this.c42 = c42;
	}

	public final Long getC43() {
		return c43;
	}

	public final void setC43(Long c43) {
		this.c43 = c43;
	}

	public final Boolean getC44() {
		return c44;
	}

	public final void setC44(Boolean c44) {
		this.c44 = c44;
	}

	public final Boolean getC45() {
		return c45;
	}

	public final void setC45(Boolean c45) {
		this.c45 = c45;
	}

	public final Boolean getC46() {
		return c46;
	}

	public final void setC46(Boolean c46) {
		this.c46 = c46;
	}

	public final Boolean getC47() {
		return c47;
	}

	public final void setC47(Boolean c47) {
		this.c47 = c47;
	}

	public final Boolean getC48() {
		return c48;
	}

	public final void setC48(Boolean c48) {
		this.c48 = c48;
	}

	public final Boolean getC49() {
		return c49;
	}

	public final void setC49(Boolean c49) {
		this.c49 = c49;
	}

	public final Boolean getC50() {
		return c50;
	}

	public final void setC50(Boolean c50) {
		this.c50 = c50;
	}

	public final Boolean getC51() {
		return c51;
	}

	public final void setC51(Boolean c51) {
		this.c51 = c51;
	}

	public final Boolean getC52() {
		return c52;
	}

	public final void setC52(Boolean c52) {
		this.c52 = c52;
	}

	public final Boolean getC53() {
		return c53;
	}

	public final void setC53(Boolean c53) {
		this.c53 = c53;
	}

	public final Boolean getC54() {
		return c54;
	}

	public final void setC54(Boolean c54) {
		this.c54 = c54;
	}

	public final Boolean getC55() {
		return c55;
	}

	public final void setC55(Boolean c55) {
		this.c55 = c55;
	}

	public final Boolean getC56() {
		return c56;
	}

	public final void setC56(Boolean c56) {
		this.c56 = c56;
	}

	public final Boolean getC57() {
		return c57;
	}

	public final void setC57(Boolean c57) {
		this.c57 = c57;
	}

	public final Boolean getC58() {
		return c58;
	}

	public final void setC58(Boolean c58) {
		this.c58 = c58;
	}

	public final Boolean getC59() {
		return c59;
	}

	public final void setC59(Boolean c59) {
		this.c59 = c59;
	}

	public final Boolean getC60() {
		return c60;
	}

	public final void setC60(Boolean c60) {
		this.c60 = c60;
	}

	public final Boolean getC61() {
		return c61;
	}

	public final void setC61(Boolean c61) {
		this.c61 = c61;
	}

	public final Boolean getC62() {
		return c62;
	}

	public final void setC62(Boolean c62) {
		this.c62 = c62;
	}

	public final Boolean getC63() {
		return c63;
	}

	public final void setC63(Boolean c63) {
		this.c63 = c63;
	}

	public final Boolean getC64() {
		return c64;
	}

	public final void setC64(Boolean c64) {
		this.c64 = c64;
	}

	public final Boolean getC65() {
		return c65;
	}

	public final void setC65(Boolean c65) {
		this.c65 = c65;
	}

	public final Boolean getC66() {
		return c66;
	}

	public final void setC66(Boolean c66) {
		this.c66 = c66;
	}

	public final Boolean getC67() {
		return c67;
	}

	public final void setC67(Boolean c67) {
		this.c67 = c67;
	}

	public final Boolean getC68() {
		return c68;
	}

	public final void setC68(Boolean c68) {
		this.c68 = c68;
	}

	public final Boolean getC69() {
		return c69;
	}

	public final void setC69(Boolean c69) {
		this.c69 = c69;
	}

	public final Boolean getC70() {
		return c70;
	}

	public final void setC70(Boolean c70) {
		this.c70 = c70;
	}

	public final Boolean getC71() {
		return c71;
	}

	public final void setC71(Boolean c71) {
		this.c71 = c71;
	}

	public final Timestamp getSatelliteTime() {
		return satelliteTime;
	}

	public final void setSatelliteTime(Timestamp satelliteTime) {
		this.satelliteTime = satelliteTime;
	}
	
	@Transient
	public final void setEclipsed(final Boolean eclipsed) {
		setC44(eclipsed);
	}
	
	@Transient
	public final Boolean isEclipsed() {
		return getC44();
	}
	
	@Transient
	public final void setSolarArrayCurrent1Plus(Long value) {
		setC1(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent1Plus() {
		return getC1();
	}
	
	@Transient
	public final void setSolarArrayCurrent1Minus(Long value) {
		setC2(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent1Minus() {
		return getC2();
	}
	
	@Transient
	public final void setSolarArrayCurrent2Plus(Long value) {
		setC3(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent2Plus() {
		return getC3();
	}
	
	@Transient
	public final void setSolarArrayCurrent2Minus(Long value) {
		setC4(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent2Minus() {
		return getC4();
	}
	
	@Transient
	public final void setSolarArrayCurrent3Plus(Long value) {
		setC5(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent3Plus() {
		return getC5();
	}
	
	@Transient
	public final void setSolarArrayCurrent3Minus(Long value) {
		setC6(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent3Minus() {
		return getC6();
	}
	
	@Transient
	public final void setSolarArrayCurrent4Plus(Long value) {
		setC7(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent4Plus() {
		return getC7();
	}
	
	@Transient
	public final void setSolarArrayCurrent4Minus(Long value) {
		setC8(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent4Minus() {
		return getC8();
	}
	
	@Transient
	public final void setSolarArrayCurrent5Plus(Long value) {
		setC9(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent5Plus() {
		return getC9();
	}
	
	@Transient
	public final void setSolarArrayCurrent5Minus(Long value) {
		setC10(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent5Minus() {
		return getC10();
	}
	
	@Transient
	public final void setSolarArrayCurrent6Plus(Long value) {
		setC11(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent6Plus() {
		return getC11();
	}
	
	@Transient
	public final void setSolarArrayCurrent6Minus(Long value) {
		setC12(value);
	}
	
	@Transient
	public final Long getSolarArrayCurrent6Minus() {
		return getC12();
	}
	
	@Transient
	public final void setSolarArrayTemperature6(Long value) {
		setC13(value);
	}
	
	@Transient
	public Long getSolarArrayTemperature6() {
		return getC13();
	}
	
	@Transient
	public final void setBattery0CurrentDirection(Boolean value) {
		setC45(value);
	}
	
	@Transient
	public Boolean getBattery0CurrentDirection() {
		return getC45();
	}
	
	@Transient
	public final void setBattery0Current(Long value) {
		setC14(value);
	}
	
	@Transient
	public Long getBattery0Current() {
		return getC14();
	}
	
	@Transient
	public final void setBattery0CellVolts(Long value) {
		setC15(value);
	}
	
	@Transient
	public Long getBattery0CellVolts() {
		return getC15();
	}
	
	@Transient
	public final void setBattery0Volts(Long value) {
		setC16(value);
	}
	
	@Transient
	public Long getBattery0Volts() {
		return getC16();
	}
	
	@Transient
	public final void setBattery0Temperature(Long value) {
		setC17(value);
	}
	
	@Transient
	public Long getBattery0Temperature() {
		return getC17();
	}
	
	@Transient
	public final void setBattery1CurrentDirection(Boolean value) {
		setC46(value);
	}
	
	@Transient
	public Boolean getBattery1CurrentDirection() {
		return getC46();
	}
	
	@Transient
	public final void setBattery1Current(Long value) {
		setC18(value);
	}
	
	@Transient
	public Long getBattery1Current() {
		return getC18();
	}
	
	@Transient
	public final void setBattery1CellVolts(Long value) {
		setC19(value);
	}
	
	@Transient
	public Long getBattery1CellVolts() {
		return getC19();
	}
	
	@Transient
	public final void setBattery1Volts(Long value) {
		setC20(value);
	}
	
	@Transient
	public Long getBattery1Volts() {
		return getC20();
	}
	
	@Transient
	public final void setBattery1Temperature(Long value) {
		setC21(value);
	}
	
	@Transient
	public Long getBattery1Temperature() {
		return getC21();
	}
	
	@Transient
	public final void setBattery2CurrentDirection(Boolean value) {
		setC47(value);
	}
	
	@Transient
	public Boolean getBattery2CurrentDirection() {
		return getC47();
	}
	
	@Transient
	public final void setBattery2Current(Long value) {
		setC22(value);
	}
	
	@Transient
	public Long getBattery2Current() {
		return getC22();
	}
	
	@Transient
	public final void setBattery2CellVolts(Long value) {
		setC23(value);
	}
	
	@Transient
	public Long getBattery2CellVolts() {
		return getC23();
	}
	
	@Transient
	public final void setBattery2Volts(Long value) {
		setC24(value);
	}
	
	@Transient
	public Long getBattery2Volts() {
		return getC24();
	}
	
	@Transient
	public final void setBattery2Temperature(Long value) {
		setC25(value);
	}
	
	@Transient
	public Long getBattery2Temperature() {
		return getC25();
	}
	
	@Transient
	public final void setBatteryHeaterForcedOn(Boolean value) {
		setC48(value);
	}
	
	@Transient
	public Boolean getBatteryHeaterForcedOn() {
		return getC48();
	}
	
	@Transient
	public final void setAntennaDeploymentTimeout(Long value) {
		setC23(value);
	}
	
	@Transient 
	public final Long getAntennaDeploymentTimeout() {
		return getC23();
	}
	
	@Transient
	public final void setAntennaStatus0(Boolean value) {
		setC49(value);
	}
	
	@Transient
	public Boolean getAntennaStatus0() {
		return getC49();
	}
	
	@Transient
	public final void setAntennaStatus1(Boolean value) {
		setC50(value);
	}
	
	@Transient
	public Boolean getAntennaStatus1() {
		return getC50();
	}
	
	@Transient
	public final void setAntennaStatus2(Boolean value) {
		setC51(value);
	}
	
	@Transient
	public Boolean getAntennaStatus2() {
		return getC51();
	}
	
	@Transient
	public final void setAntennaStatus3(Boolean value) {
		setC52(value);
	}
	
	@Transient
	public Boolean getAntennaStatus3() {
		return getC52();
	}
	
	@Transient
	public final void setAntennaTemperature(Long value) {
		setC24(value);
	}
	
	@Transient 
	public final Long getAntennaTemperature() {
		return getC24();
	}
	
	@Transient
	public final void setReceiverDoppler(Long value) {
		setC25(value);
	}
	
	@Transient 
	public final Long getReceiverDoppler() {
		return getC25();
	}
	
	@Transient
	public final void setReceiverRSSI(Long value) {
		setC26(value);
	}
	
	@Transient 
	public final Long getReceiverRSSI() {
		return getC26();
	}
	
	@Transient
	public final void setReceiverTemperature(Long value) {
		setC27(value);
	}
	
	@Transient 
	public final Long getReceiverTemperature() {
		return getC27();
	}
	
	@Transient
	public final void set(Long value) {
		setC28(value);
	}
	
	@Transient 
	public final Long getReceiveCurrent() {
		return getC28();
	}
	
	@Transient
	public final void setTransmitCurrent3v3Bus(Long value) {
		setC29(value);
	}
	
	@Transient 
	public final Long getTransmitCurrent3v3Bus() {
		return getC29();
	}
	
	@Transient
	public final void setAntennaDeploymentTimeout(Long value) {
		setC23(value);
	}
	
	@Transient 
	public final Long getAntennaDeploymentTimeout() {
		return getC23();
	}
	
	@Transient
	public final void setAntennaDeploymentTimeout(Long value) {
		setC23(value);
	}
	
	@Transient 
	public final Long getAntennaDeploymentTimeout() {
		return getC23();
	}
	
	@Transient
	public final void setAntennaDeploymentTimeout(Long value) {
		setC23(value);
	}
	
	@Transient 
	public final Long getAntennaDeploymentTimeout() {
		return getC23();
	}
	
	@Transient
	public final void setAntennaDeploymentTimeout(Long value) {
		setC23(value);
	}
	
	@Transient 
	public final Long getAntennaDeploymentTimeout() {
		return getC23();
	}
	
	@Transient
	public final void setAntennaDeploymentTimeout(Long value) {
		setC23(value);
	}
	
	@Transient 
	public final Long getAntennaDeploymentTimeout() {
		return getC23();
	}
	
	@Transient
	public final void setAntennaDeploymentTimeout(Long value) {
		setC23(value);
	}
	
	@Transient 
	public final Long getAntennaDeploymentTimeout() {
		return getC23();
	}
	
	@Transient
	public final void setAntennaDeploymentTimeout(Long value) {
		setC23(value);
	}
	
	@Transient 
	public final Long getAntennaDeploymentTimeout() {
		return getC23();
	}
	
	@Transient
	public final void setAntennaDeploymentTimeout(Long value) {
		setC23(value);
	}
	
	@Transient 
	public final Long getAntennaDeploymentTimeout() {
		return getC23();
	}
	
	@Transient
	public final void setAntennaDeploymentTimeout(Long value) {
		setC23(value);
	}
	
	@Transient 
	public final Long getAntennaDeploymentTimeout() {
		return getC23();
	}
	
	/*
	
	 0
	
	
	
	Transmit current 5.0V bus
	Reverse power
	Forward power
	Board temperature
	Board current
	Magnetometer 0
	Magnetometer 1
	Magnetometer 2
	AmacTemperature
	*/

	

}
