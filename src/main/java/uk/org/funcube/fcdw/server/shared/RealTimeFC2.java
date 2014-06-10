// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

import java.util.Date;

import org.jboss.logging.Logger;

import sun.util.LocaleServiceProviderPool.LocalizedObjectGetter;
import uk.org.funcube.fcdw.server.processor.DataProcessor;

/**
 * @author g4dpz
 *
 */
public class RealTimeFC2 extends RealTime {

	private static Logger LOG = Logger.getLogger(RealTimeFC2.class.getName());
	
	private Boolean eclipsed;
	private Long solarArrayCurrent1Plus;
	private Long solarArrayCurrent1Minus;
	private Long solarArrayCurrent2Plus;
	private Long solarArrayCurrent2Minus;
	private Long solarArrayCurrent3Plus;
	private Long solarArrayCurrent3Minus;
	private Long solarArrayCurrent4Plus;
	private Long solarArrayCurrent4Minus;
	private Long solarArrayCurrent5Plus;
	private Long solarArrayCurrent5Minus;
	private Long solarArrayCurrent6Plus;
	private Long solarArrayCurrent6Minus;
	private Long solarArrayTemperature;
	private Boolean battery0CurrentDirection;
	private Long battery0Current;
	private Long battery0Volts;
	private Long battery0Temperature;
	private Boolean battery1CurrentDirection;
	private Long battery1Current;
	private Long battery1Volts;
	private Long battery1Temperature;
	private Boolean battery2CurrentDirection;
	private Long battery2Current;
	private Long battery2Volts;
	private Long battery2Temperature;
	private Boolean batteryHeaterForcedOn;
	private Long antennaDeploymentTimeout;
	private Long antennaStatus0;
	private Long antennaStatus1;
	private Long antennaStatus2;
	private Long antennaStatus3;
	private Long antennaTemperature;
	
	private Long receiverDoppler;
	private Long receiverRSSI;
	private Long receiverTemperature;
	private Long receiverCurrent;
	private Long transmitCurrent3v3;
	private Long transmitCurrent5v0;
	private Long reversePower;
	private Long forwardPower;
	private Long paBoardTemperature;
	private Long paBoardCurrent;
	
	private Long magnetometer0;
	private Long magnetometer1;
	private Long magnetometer2;
	private Long magnetometerTemperature;
	
	private Long amacMode;
	private Long modeManagerMode;
    private Boolean modeManagerCommsNominal;
    private Long modeManagerCommsState;

    private Boolean tmtcManagerIdleEnable;
    private Boolean tmtcEventForwarding;
    private Long tcBufferReceiveEnable;
    private Long tmBufferSendEnable;
    private Long obcSoftResetCount;
    private Long epsHardResetCount;
	
	private Long dtmfCommandCount;
	private Long dtmfLastCommand;
	private Boolean dtmfCommandSuccess;
	
	private int stringPos = 0;

	public RealTimeFC2(int satelliteId, int frameType, int sensorId, Date now,
			String binaryString) {
		super();
		setSatelliteId(satelliteId);
		setFrameType(frameType);
		setSensorId(sensorId);
		setCreatedDate(now);
		parseBinary(binaryString);
	}

	private void parseBinary(String binaryString) {
		
		// 1 bit
		setEclipsed(get1BitAsInt(binaryString) == 1);
		
		// 20 bits
		setSolarArrayCurrent1Plus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent1Minus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent2Plus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent2Minus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent3Plus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent3Minus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent4Plus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent4Minus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent5Plus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent5Minus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent6Plus(get10BitsAsULong(binaryString));
		setSolarArrayCurrent6Minus(get10BitsAsULong(binaryString));
		
		// 8 bits
		setSolarArrayTemperature(get8BitsAsULong(binaryString)); 
		
		// 25 bits
		setBattery0CurrentDirection(get1BitAsInt(binaryString) == 1); 
		setBattery0Current(get8BitsAsULong(binaryString)); 
		setBattery0Volts(get8BitsAsULong(binaryString)); 
		setBattery0Temperature(get8BitsAsULong(binaryString)); 
		
		// 25 bits 
		setBattery1CurrentDirection(get1BitAsInt(binaryString) == 1); 
		setBattery1Current(get8BitsAsULong(binaryString)); 
		setBattery1Volts(get8BitsAsULong(binaryString)); 
		setBattery1Temperature(get8BitsAsULong(binaryString)); ; 
		
		// 25 bits
		setBattery2CurrentDirection(get1BitAsInt(binaryString) == 1); 
		setBattery2Current(get8BitsAsULong(binaryString)); 
		setBattery2Volts(get8BitsAsULong(binaryString)); 
		setBattery2Temperature(get8BitsAsULong(binaryString)); 
		
		// 1 bit
		setBatteryHeaterForcedOn(get1BitAsInt(binaryString) == 1); 
		
		// 28 bits
		setAntennaDeploymentTimeout(get8BitsAsULong(binaryString)); 
		setAntennaStatus0(get3BitsAsULong(binaryString));
		setAntennaStatus1(get3BitsAsULong(binaryString));
		setAntennaStatus2(get3BitsAsULong(binaryString));
		setAntennaStatus3(get3BitsAsULong(binaryString));
		setAntennaTemperature(get8BitsAsULong(binaryString)); 

        // 48 bits
		setReceiverDoppler(get8BitsAsULong(binaryString)); 
		setReceiverRSSI(get8BitsAsULong(binaryString)); 
		setReceiverTemperature(get8BitsAsULong(binaryString)); 
		setReceiverCurrent(get8BitsAsULong(binaryString)); 
		setTransmitCurrent3v3(get8BitsAsULong(binaryString)); 
		setTransmitCurrent5v0(get8BitsAsULong(binaryString)); 

        // 32 bits
		setReversePower(get8BitsAsULong(binaryString)); 
		setForwardPower(get8BitsAsULong(binaryString)); 
		setPaBoardTemperature(get8BitsAsULong(binaryString)); ;
		setPaBoardCurrent(get8BitsAsULong(binaryString)); 
        
        // 71 bits
        setAmacMode(get3BitsAsULong(binaryString));
        setMagnetometer0(get12BitsAsULong(binaryString));
        setMagnetometer1(get12BitsAsULong(binaryString));
        setMagnetometer2(get12BitsAsULong(binaryString));
        
        get1BitAsInt(binaryString); // step over the useless Funtrx enabled bits
        get1BitAsInt(binaryString); // step over the useless Funtrx enabled bits

        setModeManagerMode(get3BitsAsULong(binaryString));
        setModeManagerCommsNominal(get1BitAsInt(binaryString) == 1);
        setModeManagerCommsState(get2BitsAsULong(binaryString));

        setTmtcManagerIdleEnable(get1BitAsInt(binaryString) == 1);
        setTmtcEventForwarding(get1BitAsInt(binaryString) == 1);
        setTcBufferReceiveEnable(get3BitsAsULong(binaryString));
        setTmBufferSendEnable(get3BitsAsULong(binaryString));
        setObcSoftResetCount(get8BitsAsULong(binaryString));
        setEpsHardResetCount(get8BitsAsULong(binaryString));
        
        LOG.debug("Finised Realtime, stringPos: " + stringPos);
        
        // end of ukcube data ours is last 36 bits
        //stringPos = 404 + 8; // start of the payload (plus id byte)
        stringPos = 440 - 36;
        
		setSequenceNumber(get24BitsAsULong(binaryString));
		setDtmfCommandCount(get6BitsAsULong(binaryString));
		setDtmfLastCommand(get5BitsAsULong(binaryString));
		setDtmfCommandSuccess(get1BitAsInt(binaryString) == 1);
	}

	private int get1BitAsInt(String binaryString) {
		final int value = Integer.parseInt(binaryString.substring(stringPos, stringPos + 1), 2);
		stringPos++;
		return value;
	}

	private long get2BitsAsULong(String binaryString) {
		final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 2), 2);
		stringPos += 2;
		return value;
	}

	private long get3BitsAsULong(String binaryString) {
		final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 3), 2);
		stringPos += 3;
		return value;
	}

	private long get5BitsAsULong(String binaryString) {
		final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 5), 2);
		stringPos += 5;
		return value;
	}

	private long get6BitsAsULong(String binaryString) {
		final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 6), 2);
		stringPos += 6;
		return value;
	}

	private long get8BitsAsULong(String binaryString) {
		final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 8), 2);
		stringPos += 8;
		return value;
	}

	private long get10BitsAsULong(String binaryString) {
		final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 10), 2);
		stringPos += 10;
		return value;
	}

	private long get12BitsAsULong(String binaryString) {
		final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 12), 2);
		stringPos += 12;
		return value;
	}

	private long get24BitsAsULong(String binaryString) {
		final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 24), 2);
		stringPos += 24;
		return value;
	}

	public final Boolean isEclipsed() {
		return eclipsed;
	}

	public final void setEclipsed(Boolean eclipsed) {
		this.eclipsed = eclipsed;
	}

	public Long getSolarArrayCurrent1Plus() {
		return solarArrayCurrent1Plus;
	}

	public void setSolarArrayCurrent1Plus(Long solarArrayCurrent1Plus) {
		this.solarArrayCurrent1Plus = solarArrayCurrent1Plus;
	}

	public Long getSolarArrayCurrent1Minus() {
		return solarArrayCurrent1Minus;
	}

	public void setSolarArrayCurrent1Minus(Long solarArrayCurrent1Minus) {
		this.solarArrayCurrent1Minus = solarArrayCurrent1Minus;
	}

	public Long getSolarArrayCurrent2Plus() {
		return solarArrayCurrent2Plus;
	}

	public void setSolarArrayCurrent2Plus(Long solarArrayCurrent2Plus) {
		this.solarArrayCurrent2Plus = solarArrayCurrent2Plus;
	}

	public Long getSolarArrayCurrent2Minus() {
		return solarArrayCurrent2Minus;
	}

	public void setSolarArrayCurrent2Minus(Long solarArrayCurrent2Minus) {
		this.solarArrayCurrent2Minus = solarArrayCurrent2Minus;
	}

	public Long getSolarArrayCurrent3Plus() {
		return solarArrayCurrent3Plus;
	}

	public void setSolarArrayCurrent3Plus(Long solarArrayCurrent3Plus) {
		this.solarArrayCurrent3Plus = solarArrayCurrent3Plus;
	}

	public Long getSolarArrayCurrent3Minus() {
		return solarArrayCurrent3Minus;
	}

	public void setSolarArrayCurrent3Minus(Long solarArrayCurrent3Minus) {
		this.solarArrayCurrent3Minus = solarArrayCurrent3Minus;
	}

	public Long getSolarArrayCurrent4Plus() {
		return solarArrayCurrent4Plus;
	}

	public void setSolarArrayCurrent4Plus(Long solarArrayCurrent4Plus) {
		this.solarArrayCurrent4Plus = solarArrayCurrent4Plus;
	}

	public Long getSolarArrayCurrent4Minus() {
		return solarArrayCurrent4Minus;
	}

	public void setSolarArrayCurrent4Minus(Long solarArrayCurrent4Minus) {
		this.solarArrayCurrent4Minus = solarArrayCurrent4Minus;
	}

	public Long getSolarArrayCurrent5Plus() {
		return solarArrayCurrent5Plus;
	}

	public void setSolarArrayCurrent5Plus(Long solarArrayCurrent5Plus) {
		this.solarArrayCurrent5Plus = solarArrayCurrent5Plus;
	}

	public Long getSolarArrayCurrent5Minus() {
		return solarArrayCurrent5Minus;
	}

	public void setSolarArrayCurrent5Minus(Long solarArrayCurrent5Minus) {
		this.solarArrayCurrent5Minus = solarArrayCurrent5Minus;
	}

	public Long getSolarArrayCurrent6Plus() {
		return solarArrayCurrent6Plus;
	}

	public void setSolarArrayCurrent6Plus(Long solarArrayCurrent6Plus) {
		this.solarArrayCurrent6Plus = solarArrayCurrent6Plus;
	}

	public Long getSolarArrayCurrent6Minus() {
		return solarArrayCurrent6Minus;
	}

	public void setSolarArrayCurrent6Minus(Long solarArrayCurrent6Minus) {
		this.solarArrayCurrent6Minus = solarArrayCurrent6Minus;
	}

	public Long getSolarArrayTemperature() {
		return solarArrayTemperature;
	}

	public void setSolarArrayTemperature(Long solarArrayTemperature) {
		this.solarArrayTemperature = solarArrayTemperature;
	}

	public Boolean getBattery0CurrentDirection() {
		return battery0CurrentDirection;
	}

	public void setBattery0CurrentDirection(Boolean battery0CurrentDirection) {
		this.battery0CurrentDirection = battery0CurrentDirection;
	}

	public Long getBattery0Current() {
		return battery0Current;
	}

	public void setBattery0Current(Long battery0Current) {
		this.battery0Current = battery0Current;
	}

	public Long getBattery0Volts() {
		return battery0Volts;
	}

	public void setBattery0Volts(Long battery0Volts) {
		this.battery0Volts = battery0Volts;
	}

	public Long getBattery0Temperature() {
		return battery0Temperature;
	}

	public void setBattery0Temperature(Long battery0Temperature) {
		this.battery0Temperature = battery0Temperature;
	}

	public Boolean getBattery1CurrentDirection() {
		return battery1CurrentDirection;
	}

	public void setBattery1CurrentDirection(Boolean battery1CurrentDirection) {
		this.battery1CurrentDirection = battery1CurrentDirection;
	}

	public Long getBattery1Current() {
		return battery1Current;
	}

	public void setBattery1Current(Long battery1Current) {
		this.battery1Current = battery1Current;
	}

	public Long getBattery1Volts() {
		return battery1Volts;
	}

	public void setBattery1Volts(Long battery1Volts) {
		this.battery1Volts = battery1Volts;
	}

	public Long getBattery1Temperature() {
		return battery1Temperature;
	}

	public void setBattery1Temperature(Long battery1Temperature) {
		this.battery1Temperature = battery1Temperature;
	}

	public Boolean getBattery2CurrentDirection() {
		return battery2CurrentDirection;
	}

	public void setBattery2CurrentDirection(Boolean battery2CurrentDirection) {
		this.battery2CurrentDirection = battery2CurrentDirection;
	}

	public Long getBattery2Current() {
		return battery2Current;
	}

	public void setBattery2Current(Long battery2Current) {
		this.battery2Current = battery2Current;
	}

	public Long getBattery2Volts() {
		return battery2Volts;
	}

	public void setBattery2Volts(Long battery2Volts) {
		this.battery2Volts = battery2Volts;
	}

	public Long getBattery2Temperature() {
		return battery2Temperature;
	}

	public void setBattery2Temperature(Long battery2Temperature) {
		this.battery2Temperature = battery2Temperature;
	}

	public Boolean getBatteryHeaterForcedOn() {
		return batteryHeaterForcedOn;
	}

	public void setBatteryHeaterForcedOn(Boolean batteryHeaterForcedOn) {
		this.batteryHeaterForcedOn = batteryHeaterForcedOn;
	}

	public Long getAntennaDeploymentTimeout() {
		return antennaDeploymentTimeout;
	}

	public void setAntennaDeploymentTimeout(Long antennaDeploymentTimeout) {
		this.antennaDeploymentTimeout = antennaDeploymentTimeout;
	}

	public Long getAntennaStatus0() {
		return antennaStatus0;
	}

	public void setAntennaStatus0(Long antennaStatus0) {
		this.antennaStatus0 = antennaStatus0;
	}

	public Long getAntennaStatus1() {
		return antennaStatus1;
	}

	public void setAntennaStatus1(Long antennaStatus1) {
		this.antennaStatus1 = antennaStatus1;
	}

	public Long getAntennaStatus2() {
		return antennaStatus2;
	}

	public void setAntennaStatus2(Long antennaStatus2) {
		this.antennaStatus2 = antennaStatus2;
	}

	public Long getAntennaStatus3() {
		return antennaStatus3;
	}

	public void setAntennaStatus3(Long antennaStatus3) {
		this.antennaStatus3 = antennaStatus3;
	}

	public Long getAntennaTemperature() {
		return antennaTemperature;
	}

	public void setAntennaTemperature(Long antennaTemperature) {
		this.antennaTemperature = antennaTemperature;
	}

	public Long getReceiverDoppler() {
		return receiverDoppler;
	}

	public void setReceiverDoppler(Long receiverDoppler) {
		this.receiverDoppler = receiverDoppler;
	}

	public Long getReceiverRSSI() {
		return receiverRSSI;
	}

	public void setReceiverRSSI(Long receiverRSSI) {
		this.receiverRSSI = receiverRSSI;
	}

	public Long getReceiverTemperature() {
		return receiverTemperature;
	}

	public void setReceiverTemperature(Long receiverTemperature) {
		this.receiverTemperature = receiverTemperature;
	}

	public Long getReceiverCurrent() {
		return receiverCurrent;
	}

	public void setReceiverCurrent(Long receiverCurrent) {
		this.receiverCurrent = receiverCurrent;
	}

	public Long getTransmitCurrent3v3() {
		return transmitCurrent3v3;
	}

	public void setTransmitCurrent3v3(Long transmitCurrent3v3) {
		this.transmitCurrent3v3 = transmitCurrent3v3;
	}

	public Long getTransmitCurrent5v0() {
		return transmitCurrent5v0;
	}

	public void setTransmitCurrent5v0(Long transmitCurrent5v0) {
		this.transmitCurrent5v0 = transmitCurrent5v0;
	}

	public Long getReversePower() {
		return reversePower;
	}

	public void setReversePower(Long reversePower) {
		this.reversePower = reversePower;
	}

	public Long getForwardPower() {
		return forwardPower;
	}

	public void setForwardPower(Long forwardPower) {
		this.forwardPower = forwardPower;
	}

	public Long getPaBoardTemperature() {
		return paBoardTemperature;
	}

	public void setPaBoardTemperature(Long paBoardTemperature) {
		this.paBoardTemperature = paBoardTemperature;
	}

	public Long getPaBoardCurrent() {
		return paBoardCurrent;
	}

	public void setPaBoardCurrent(Long paBoardCurrent) {
		this.paBoardCurrent = paBoardCurrent;
	}

	public Long getMagnetometer0() {
		return magnetometer0;
	}

	public void setMagnetometer0(Long magnetometer0) {
		this.magnetometer0 = magnetometer0;
	}

	public Long getMagnetometer1() {
		return magnetometer1;
	}

	public void setMagnetometer1(Long magnetometer1) {
		this.magnetometer1 = magnetometer1;
	}

	public Long getMagnetometer2() {
		return magnetometer2;
	}

	public void setMagnetometer2(Long magnetometer2) {
		this.magnetometer2 = magnetometer2;
	}

	public Long getMagnetometerTemperature() {
		return magnetometerTemperature;
	}

	public void setMagnetometerTemperature(Long magnetometerTemperature) {
		this.magnetometerTemperature = magnetometerTemperature;
	}

	public Long getDtmfCommandCount() {
		return dtmfCommandCount;
	}

	public void setDtmfCommandCount(Long dtmfCommandCount) {
		this.dtmfCommandCount = dtmfCommandCount;
	}

	public Long getDtmfLastCommand() {
		return dtmfLastCommand;
	}

	public void setDtmfLastCommand(Long dtmfLastCommand) {
		this.dtmfLastCommand = dtmfLastCommand;
	}

	public Boolean getDtmfCommandSuccess() {
		return dtmfCommandSuccess;
	}

	public void setDtmfCommandSuccess(Boolean dtmfCommandSuccess) {
		this.dtmfCommandSuccess = dtmfCommandSuccess;
	}

	public final Long getAmacMode() {
		return amacMode;
	}

	public final void setAmacMode(Long amacMode) {
		this.amacMode = amacMode;
	}

	public final Long getModeManagerMode() {
		return modeManagerMode;
	}

	public final void setModeManagerMode(Long modeManagerMode) {
		this.modeManagerMode = modeManagerMode;
	}

	public final Boolean getModeManagerCommsNominal() {
		return modeManagerCommsNominal;
	}

	public final void setModeManagerCommsNominal(Boolean modeManagerCommsNominal) {
		this.modeManagerCommsNominal = modeManagerCommsNominal;
	}

	public final Long getModeManagerCommsState() {
		return modeManagerCommsState;
	}

	public final void setModeManagerCommsState(Long modeManagerCommsState) {
		this.modeManagerCommsState = modeManagerCommsState;
	}

	public final Boolean getTmtcManagerIdleEnable() {
		return tmtcManagerIdleEnable;
	}

	public final void setTmtcManagerIdleEnable(Boolean tmtcManagerIdleEnable) {
		this.tmtcManagerIdleEnable = tmtcManagerIdleEnable;
	}

	public final Boolean getTmtcEventForwarding() {
		return tmtcEventForwarding;
	}

	public final void setTmtcEventForwarding(Boolean tmtcEventForwarding) {
		this.tmtcEventForwarding = tmtcEventForwarding;
	}

	public final Long getTcBufferReceiveEnable() {
		return tcBufferReceiveEnable;
	}

	public final void setTcBufferReceiveEnable(Long tcBufferReceiveEnable) {
		this.tcBufferReceiveEnable = tcBufferReceiveEnable;
	}

	public final Long getTmBufferSendEnable() {
		return tmBufferSendEnable;
	}

	public final void setTmBufferSendEnable(Long tmBufferSendEnable) {
		this.tmBufferSendEnable = tmBufferSendEnable;
	}

	public final Long getObcSoftResetCount() {
		return obcSoftResetCount;
	}

	public final void setObcSoftResetCount(Long obcSoftResetCount) {
		this.obcSoftResetCount = obcSoftResetCount;
	}

	public final Long getEpsHardResetCount() {
		return epsHardResetCount;
	}

	public final void setEpsHardResetCount(Long epsHardResetCount) {
		this.epsHardResetCount = epsHardResetCount;
	}
}
