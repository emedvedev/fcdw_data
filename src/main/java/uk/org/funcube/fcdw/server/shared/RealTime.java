// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

import java.util.Date;

import org.jboss.logging.Logger;

import uk.org.funcube.fcdw.domain.RealTimeEntity;

public class RealTime {

	private static final Logger LOGGER = Logger.getLogger(RealTime.class
			.getName());

	private Date createdDate;

	private long satelliteId;
	private long sequenceNumber;

	private EPS eps;

	private RF rf;

	private Antenna antenna;

	private SoftwareState softwareState;
	
	private DTMF dtmf;

	private long frameType;

	private long sensorId;

	private long commandCount;

	private long lastCommand;

	public RealTime() {

	}

	public RealTime(final int satelliteId, final int frameType, final int sensorId,
			final Date createdDate, final String binaryString) {

		this.satelliteId = satelliteId;
		this.frameType = frameType;
		this.sensorId = sensorId;
		this.createdDate = createdDate;
		this.sequenceNumber = extractSequenceNumber(satelliteId, binaryString);
		this.eps = extractEPS(satelliteId, binaryString);
		this.rf = extractRF(satelliteId, sensorId, binaryString);
		this.antenna = extractAntenna(satelliteId, binaryString);
		this.dtmf = extractDtmf(satelliteId, binaryString);
		this.softwareState = extractSoftwareState(satelliteId,
				binaryString);
	}

	public final Date getCreatedDate() {
		return createdDate;
	}

	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public final EPS getEps() {
		return eps;
	}

	public final void setEps(EPS eps) {
		this.eps = eps;
	}

	public final RF getRf() {
		return rf;
	}

	public final void setRf(RF rf) {
		this.rf = rf;
	}

	public final Antenna getAntenna() {
		return antenna;
	}

	public final void setAntenna(Antenna antenna) {
		this.antenna = antenna;
	}

	public final SoftwareState getSoftwareState() {
		return softwareState;
	}

	public final void setSoftwareState(SoftwareState softwareState) {
		this.softwareState = softwareState;
	}

	public final DTMF getDtmf() {
		return dtmf;
	}

	public final void setDtmf(DTMF dtmf) {
		this.dtmf = dtmf;
	}

	public final long getFrameType() {
		return frameType;
	}

	public final void setFrameType(long frameType) {
		this.frameType = frameType;
	}

	private static final long extractSequenceNumber(final int satelliteId,
			final String binaryString) {

		long seqNo;

		switch (satelliteId) {
		case 0: {
			seqNo = Long.parseLong(binaryString.substring(392, 416), 2);
			break;
		}
		case 1: {
			seqNo = Long.parseLong(binaryString.substring(402, 426), 2);
			break;
		}
		case 2: {
			seqNo = Long.parseLong(binaryString.substring(392, 416), 2);
			break;
		}
		default:
			LOGGER.error("Unable to recover Seq. No. for satellite id: "
					+ satelliteId);
			seqNo = -1;
			break;
		}

		return seqNo;
	}

	public static EPS extractEPS(int satelliteId, String binaryString) {

		EPS epsEntity = null;

		switch (satelliteId) {
		case 0: {
			epsEntity = new GomSpaceEPS(binaryString.substring(0, 300));
			break;
		}
		case 1: {
			epsEntity = new ClydeSpaceEPS(binaryString.substring(0, 310));
			break;
		}
		case 2: {
			epsEntity = new GomSpaceEPS(binaryString.substring(0, 300));
			break;
		}
		default:
			LOGGER.error("Unable to recover EPS for satellite id: "
					+ satelliteId);
			break;
		}

		return epsEntity;
	}

	public static RF extractRF(int satelliteId, int sensorId,
			String binaryString) {

		RF rfEntity = null;

		switch (satelliteId) {
		case 0: {
			rfEntity = new GomSpaceRF(sensorId, binaryString.substring(
					292, 372));
			break;
		}
		case 1: {
			rfEntity = new ClydeSpaceRF(sensorId, binaryString.substring(
					310, 382));
			break;
		}
		case 2: {
			rfEntity = new GomSpaceRF(sensorId, binaryString.substring(
					292, 372));
			break;
		}
		default:
			LOGGER.error("Unable to recover RF for satellite id: "
					+ satelliteId);
			break;
		}

		return rfEntity;
	}

	private DTMF extractDtmf(int satelliteId2, String binaryString) {


		DTMF dtmf = null;

		switch ((int)satelliteId) {
		case 0: {
			dtmf = new DTMF(
					binaryString.substring(416, 427));
			break;
		}
		case 1: {
			dtmf = new DTMF(
					binaryString.substring(426, 437));
			break;
		}
		case 2: {
			dtmf = new DTMF(
					binaryString.substring(416, 427));
			break;
		}
		default:
			LOGGER.error("Unable to recover DTMF Info for satellite id: "
					+ satelliteId);
			break;
		}

		return dtmf;
	}

	private SoftwareState extractSoftwareState(int satelliteId2,
			String binaryString) {

		SoftwareState softwareState = null;

		switch ((int)satelliteId) {
		case 0: {
			softwareState = new GomSpaceSoftwareState(
					binaryString.substring(427, binaryString.length()));
			break;
		}
		case 1: {
			softwareState = new ClydeSpaceSoftwareState(
					binaryString.substring(437, binaryString.length()));
			break;
		}
		case 2: {
			softwareState = new GomSpaceSoftwareState(
					binaryString.substring(427, binaryString.length()));
			break;
		}
		default:
			LOGGER.error("Unable to recover SoftwareState for satellite id: "
					+ satelliteId);
			break;
		}

		return softwareState;
	}

	private Antenna extractAntenna(int satelliteId2, String binaryString) {

		int pos = 0;
		int start = 0;

		switch ((int)satelliteId) {
		case 0: {
			start = 372;
			break;
		}
		case 1: {
			start = 382;
			break;
		}
		case 2: {
			start = 372;
			break;
		}
		default:
			LOGGER.error("Unable to recover SoftwareState for satellite id: "
					+ satelliteId);
			break;
		}

		pos = start;

		long antennaTemp0 = 0;
		long antennaTemp1 = 0;
		for (; pos < start + 16; pos += 8) {
			final long temperature = Long.parseLong(
					binaryString.substring(pos, pos + 8), 2);
			if (0 == (pos - start) / 8) {
				antennaTemp0 = temperature;
			} else {
				antennaTemp1 = temperature;
			}
		}

		boolean antennaDeployment0 = false;
		boolean antennaDeployment1 = false;
		boolean antennaDeployment2 = false;
		boolean antennaDeployment3 = false;

		// we do not reset pos before the loop
		for (; pos < start + 20; pos += 1) {
			final boolean deployed = binaryString.substring(pos, pos + 1)
					.equals("1");
			switch (pos - start - 16) {
			case 0:
				antennaDeployment0 = deployed;
				break;
			case 1:
				antennaDeployment1 = deployed;
				break;
			case 2:
				antennaDeployment2 = deployed;
				break;
			case 3:
				antennaDeployment3 = deployed;
				break;
			}
		}

		return new Antenna(antennaTemp0, antennaTemp1, antennaDeployment0,
				antennaDeployment1, antennaDeployment2, antennaDeployment3);
	}

	public DTMF getDTMF() {
		// TODO Auto-generated method stub
		return dtmf;
	}

	public final long getSensorId() {
		return sensorId;
	}

	public final void setSensorId(long sensorId) {
		this.sensorId = sensorId;
	}

	/**
	 * @return
	 */
	public long getCommandCount() {
		return commandCount;
	}

	/**
	 * @return
	 */
	public long getLastCommand() {
		return lastCommand;
	}
	
	public Boolean isEclipsed() {
		return softwareState.getC9();
	}

}
