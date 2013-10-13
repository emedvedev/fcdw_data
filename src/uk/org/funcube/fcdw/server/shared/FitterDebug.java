package uk.org.funcube.fcdw.server.shared;

public class FitterDebug {

	private final String dtmfRejectedToneCount;
	private final String deployCount0;
	private final String deployCount1;
	private final String deployCount2;

	private final String stateCycle;
	private final String stateDeviceCycle;
	private final String stateSequence;
	private final String stateDeviceError;
	private final String stateDeviceEnable;
	private final String stateCksum;

	private final String eclipseThresholdCurrent;
	private final String eclipseThresholdVolts;
	private final String eclipseHysteresisCurrent;
	private final String eclipseHysteresisVolts;
	private final String eclipsePanelEnableCurrent;
	private final String eclipsePanelEnableVolts;
	private final String eclipseMinNewModeDuration;
	private final String eclipseNewModeDuration;
	private final String eclipseActiveCountRxonly;
	private final String eclipseActiveCountAmateur;
	private final String eclipseActiveCountRxonlyRemaining;
	private final String eclipseActiveCountAmateurRemaining;
	private final String eclipseModulationDivider;
	private final String eclipseCksum;

	private final String safeEpsTemp0;
	private final String safeEpsTemp1;
	private final String safeEpsTemp2;
	private final String safeEpsTemp3;
	private final String safeEpsTemp4;
	private final String safeThresholdVolts0;
	private final String safeThresholdVolts1;
	private final String safeThresholdVolts2;
	private final String safeThresholdVolts3;
	private final String safeThresholdVolts4;
	private final String safeHysteresis;
	private final String safeMinModeDuration;
	private final String safeCurrentModeDuration;
	private final String safeCksum;

	private final String antsARM;
	private final String antsA4B;
	private final String antsA4T;
	private final String antsA4S;
	private final String antsA3B;
	private final String antsA3T;
	private final String antsA3S;
	private final String antsIGN;
	private final String antsA2B;
	private final String antsA2T;
	private final String antsA2S;
	private final String antsA1B;
	private final String antsA1T;
	private final String antsA1S;

	private final String burnSideA0;
	private final String burnSideA1;
	private final String burnSideA2;
	private final String burnSideA3;
	private final String burnSideB0;
	private final String burnSideB1;
	private final String burnSideB2;
	private final String burnSideB3;
	private final String burnCksum;
	private final String hexMessage;

	private final String bootLoadFitterMsgs;
	private final String errorDevice;
	private final String antsPowerCycle;
	private final String enableFitterUpload;
	private final String eclipseSwitch;
	private final String eclipseForce;
	private final String safeModeDetect;
	private final String safeModeForce;
	private final String debugMode1;
	private final String delayBoot;
	private final String ukubeEnabled;

	private int position = 0;

	public FitterDebug(final String hexMessage) {
		this.hexMessage = hexMessage;
		position = 0;

		dtmfRejectedToneCount = get16bitsAsUInt();
		deployCount0 = get8bitsAsUInt(0xff);
		deployCount1 = get8bitsAsUInt(0xff);
		deployCount2 = get8bitsAsUInt(0xff);

		stateCycle = get8bitsAsUInt(0xff);
		stateDeviceCycle = get8bitsAsUInt(0xff);
		stateSequence = get32bitsAsUInt((long) 0x0fff);
		stateDeviceError = get8bitsAsUInt(0xff);
		stateDeviceEnable = get8bitsAsUInt(0xff);
		stateCksum = get8bitsAsUInt(0xff);

		// skip 8 bits (In Eclipse)
		get8bitsAsUInt(0xff);

		eclipseThresholdCurrent = get16bitsAsUInt();
		eclipseThresholdVolts = get16bitsAsUInt();
		eclipseHysteresisCurrent = get16bitsAsUInt();
		eclipseHysteresisVolts = get16bitsAsUInt();
		eclipsePanelEnableCurrent = get8bitsAsUInt(0xff);
		eclipsePanelEnableVolts = get8bitsAsUInt(0xff);
		eclipseMinNewModeDuration = get16bitsAsUInt();
		eclipseNewModeDuration = get16bitsAsUInt();
		eclipseActiveCountRxonly = get8bitsAsUInt(0xff);
		eclipseActiveCountAmateur = get8bitsAsUInt(0xff);
		eclipseActiveCountRxonlyRemaining = get8bitsAsUInt(0xff);
		eclipseActiveCountAmateurRemaining = get8bitsAsUInt(0xff);
		eclipseModulationDivider = get8bitsAsUInt(0xff);
		eclipseCksum = get8bitsAsUInt(0xff);

		// Skip 8 bits (in safe)
		get8bitsAsUInt(0xff);

		safeEpsTemp0 = get16bitsAsUInt();
		safeEpsTemp1 = get16bitsAsUInt();
		safeEpsTemp2 = get16bitsAsUInt();
		safeEpsTemp3 = get16bitsAsUInt();
		safeEpsTemp4 = get16bitsAsUInt();
		safeThresholdVolts0 = get16bitsAsUInt();
		safeThresholdVolts1 = get16bitsAsUInt();
		safeThresholdVolts2 = get16bitsAsUInt();
		safeThresholdVolts3 = get16bitsAsUInt();
		safeThresholdVolts4 = get16bitsAsUInt();
		safeHysteresis = get16bitsAsUInt();
		safeMinModeDuration = get16bitsAsUInt();
		safeCurrentModeDuration = get16bitsAsUInt();
		safeCksum = get8bitsAsUInt(0xff);

		antsARM = get1Bit(); // System armed
		antsA4B = get1Bit(); // Deployment active (burning)
		antsA4T = get1Bit(); // Timed out last deployment
		antsA4S = get1Bit(); // Switch bit (1==closed, 0==deployed)
		// skip 1 bit
		get1Bit();
		antsA3B = get1Bit(); // Deployment active (burning)
		antsA3T = get1Bit(); // Timed out last deployment
		antsA3S = get1Bit(); // Switch bit (1==closed, 0==deployed)
		antsIGN = get1Bit(); // Ignore switches when activating (1==true)
		antsA2B = get1Bit(); // Deployment active (burning)
		antsA2T = get1Bit(); // Timed out last deployment
		antsA2S = get1Bit(); // Switch bit (1==closed, 0==deployed)
		// skip 1 bit
		get1Bit();
		antsA1B = get1Bit(); // Deployment active (burning)
		antsA1T = get1Bit(); // Timed out last deployment
		antsA1S = get1Bit();

		burnSideA0 = get16bitsAsUInt();
		burnSideA1 = get16bitsAsUInt();
		burnSideA2 = get16bitsAsUInt();
		burnSideA3 = get16bitsAsUInt();
		burnSideB0 = get16bitsAsUInt();
		burnSideB1 = get16bitsAsUInt();
		burnSideB2 = get16bitsAsUInt();
		burnSideB3 = get16bitsAsUInt();
		burnCksum = get8bitsAsUInt(0xff);

		bootLoadFitterMsgs = get8bitsAsUInt(0x01);
		errorDevice = get8bitsAsUInt(0x01);
		antsPowerCycle = get8bitsAsUInt(0x01);
		enableFitterUpload = get8bitsAsUInt(0x01);
		eclipseSwitch = get8bitsAsUInt(0x01);
		eclipseForce = get8bitsAsUInt(0x01);
		safeModeDetect = get8bitsAsUInt(0x01);
		safeModeForce = get8bitsAsUInt(0x01);
		debugMode1 = get8bitsAsUInt(0x01);
		delayBoot = get8bitsAsUInt(0x01);
		ukubeEnabled = get8bitsAsUInt(0x01);
	}

	public final String getDtmfRejectedToneCount() {
		return dtmfRejectedToneCount;
	}

	public final String getDeployCount0() {
		return deployCount0;
	}

	public final String getDeployCount1() {
		return deployCount1;
	}

	public final String getDeployCount2() {
		return deployCount2;
	}

	public final String getStateCycle() {
		return stateCycle;
	}

	public final String getStateDeviceCycle() {
		return stateDeviceCycle;
	}

	public final String getStateSequence() {
		return stateSequence;
	}

	public final String getStateDeviceError() {
		return stateDeviceError;
	}

	public final String getStateDeviceEnable() {
		return stateDeviceEnable;
	}

	public final String getStateCksum() {
		return stateCksum;
	}

	public final String getEclipseThresholdCurrent() {
		return eclipseThresholdCurrent;
	}

	public final String getEclipseThresholdVolts() {
		return eclipseThresholdVolts;
	}

	public final String getEclipseHysteresisCurrent() {
		return eclipseHysteresisCurrent;
	}

	public final String getEclipseHysteresisVolts() {
		return eclipseHysteresisVolts;
	}

	public final String getEclipsePanelEnableCurrent() {
		return eclipsePanelEnableCurrent;
	}

	public final String getEclipsePanelEnableVolts() {
		return eclipsePanelEnableVolts;
	}

	public final String getEclipseMinNewModeDuration() {
		return eclipseMinNewModeDuration;
	}

	public final String getEclipseNewModeDuration() {
		return eclipseNewModeDuration;
	}

	public final String getEclipseActiveCountRxonly() {
		return eclipseActiveCountRxonly;
	}

	public final String getEclipseActiveCountAmateur() {
		return eclipseActiveCountAmateur;
	}

	public final String getEclipseActiveCountRxonlyRemaining() {
		return eclipseActiveCountRxonlyRemaining;
	}

	public final String getEclipseActiveCountAmateurRemaining() {
		return eclipseActiveCountAmateurRemaining;
	}

	public final String getEclipseModulationDivider() {
		return eclipseModulationDivider;
	}

	public final String getEclipseCksum() {
		return eclipseCksum;
	}

	public final String getSafeEpsTemp0() {
		return safeEpsTemp0;
	}

	public final String getSafeEpsTemp1() {
		return safeEpsTemp1;
	}

	public final String getSafeEpsTemp2() {
		return safeEpsTemp2;
	}

	public final String getSafeEpsTemp3() {
		return safeEpsTemp3;
	}

	public final String getSafeEpsTemp4() {
		return safeEpsTemp4;
	}

	public final String getSafeThresholdVolts0() {
		return safeThresholdVolts0;
	}

	public final String getSafeThresholdVolts1() {
		return safeThresholdVolts1;
	}

	public final String getSafeThresholdVolts2() {
		return safeThresholdVolts2;
	}

	public final String getSafeThresholdVolts3() {
		return safeThresholdVolts3;
	}

	public final String getSafeThresholdVolts4() {
		return safeThresholdVolts4;
	}

	public final String getSafeHysteresis() {
		return safeHysteresis;
	}

	public final String getSafeMinModeDuration() {
		return safeMinModeDuration;
	}

	public final String getSafeCurrentModeDuration() {
		return safeCurrentModeDuration;
	}

	public final String getSafeCksum() {
		return safeCksum;
	}

	public final String getAntsARM() {
		return antsARM;
	}

	public final String getAntsA4B() {
		return antsA4B;
	}

	public final String getAntsA4T() {
		return antsA4T;
	}

	public final String getAntsA4S() {
		return antsA4S;
	}

	public final String getAntsA3B() {
		return antsA3B;
	}

	public final String getAntsA3T() {
		return antsA3T;
	}

	public final String getAntsA3S() {
		return antsA3S;
	}

	public final String getAntsIGN() {
		return antsIGN;
	}

	public final String getAntsA2B() {
		return antsA2B;
	}

	public final String getAntsA2T() {
		return antsA2T;
	}

	public final String getAntsA2S() {
		return antsA2S;
	}

	public final String getAntsA1B() {
		return antsA1B;
	}

	public final String getAntsA1T() {
		return antsA1T;
	}

	public final String getAntsA1S() {
		return antsA1S;
	}

	public final String getBurnSideA0() {
		return burnSideA0;
	}

	public final String getBurnSideA1() {
		return burnSideA1;
	}

	public final String getBurnSideA2() {
		return burnSideA2;
	}

	public final String getBurnSideA3() {
		return burnSideA3;
	}

	public final String getBurnSideB0() {
		return burnSideB0;
	}

	public final String getBurnSideB1() {
		return burnSideB1;
	}

	public final String getBurnSideB2() {
		return burnSideB2;
	}

	public final String getBurnSideB3() {
		return burnSideB3;
	}

	public final String getBurnCksum() {
		return burnCksum;
	}

	public final String getHexMessage() {
		return hexMessage;
	}

	public final String getBootLoadFitterMsgs() {
		return bootLoadFitterMsgs;
	}

	public final String getErrorDevice() {
		return errorDevice;
	}

	public final String getAntsPowerCycle() {
		return antsPowerCycle;
	}

	public final String getEnableFitterUpload() {
		return enableFitterUpload;
	}

	public final String getEclipseSwitch() {
		return eclipseSwitch;
	}

	public final String getEclipseForce() {
		return eclipseForce;
	}

	public final String getSafeModeDetect() {
		return safeModeDetect;
	}

	public final String getSafeModeForce() {
		return safeModeForce;
	}

	public final String getDebugMode1() {
		return debugMode1;
	}

	public final String getDelayBoot() {
		return delayBoot;
	}

	public final String getUkubeEnabled() {
		return ukubeEnabled;
	}

	private String get1Bit() {
		int value = Integer.parseInt(hexMessage.substring(position, position + 1), 2);
		position += 1;
		return Integer.toString(value);
	}

	private String get8bitsAsUInt(Integer mask) {
		int value = Integer.parseInt(hexMessage.substring(position, position + 8), 2);
		position += 8;
		return Integer.toString(value & mask);
	}

	private final String get16bitsAsUInt() {
		int value = Integer.parseInt(hexMessage.substring(position, position + 16), 2);
		position += 16;
		return Integer.toString(value);
	}

	private String get32bitsAsUInt(Long mask) {
		long value = Long.parseLong(hexMessage.substring(position, position + 32), 2);
		position += 32;
		return Long.toString(value & mask);
	}

}
