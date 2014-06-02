package uk.org.funcube.fcdw.server.shared;

import java.util.Date;

public class UkubeRealTime extends RealTime {

	/**
	 * @param satelliteId
	 * @param frameType
	 * @param sensorId
	 * @param now
	 * @param binaryString
	 */
	public UkubeRealTime(int satelliteId, int frameType, int sensorId,
			Date now, String binaryString) {
		super();
		setSatelliteId(satelliteId);
		setFrameType(frameType);
		setSensorId(sensorId);
		setCreatedDate(now);
	}
	
	public boolean isEclipsed() {
		// TODO Fix this
		return false;
	}

}
