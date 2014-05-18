// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "DTMFCommand")
public class DTMFCommandEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long satelliteId;
	private Long sequenceNumber;
	private Long frameType;
	private Timestamp createdDate;
	private String latitude;
	private String longitude;
	private Boolean valid;
	private Long value;

	public DTMFCommandEntity() {
	}

	public DTMFCommandEntity(Long satelliteId, Long sequenceNumber,
			Long frameType, Timestamp createdDate, String latitude,
			String longitude, Boolean valid, Long value) {
		this.satelliteId = satelliteId;
		this.sequenceNumber = sequenceNumber;
		this.frameType = frameType;
		this.createdDate = createdDate;
		this.latitude = latitude;
		this.longitude = longitude;
		this.valid = valid;
		this.value = value;
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getSatelliteId() {
		return satelliteId;
	}

	public final void setSatelliteId(Long satelliteId) {
		this.satelliteId = satelliteId;
	}

	public final Long getSequenceNumber() {
		return sequenceNumber;
	}

	public final void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public final Long getFrameType() {
		return frameType;
	}

	public final void setFrameType(Long frameType) {
		this.frameType = frameType;
	}

	public final Timestamp getCreatedDate() {
		return createdDate;
	}

	public final void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public final String getLatitude() {
		return latitude;
	}

	public final void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public final String getLongitude() {
		return longitude;
	}

	public final void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public final Boolean getValid() {
		return valid;
	}

	public final void setValid(Boolean valid) {
		this.valid = valid;
	}

	public final Long getValue() {
		return value;
	}

	public final void setValue(Long value) {
		this.value = value;
	}
	

}
