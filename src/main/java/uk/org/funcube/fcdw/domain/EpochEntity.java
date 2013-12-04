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
@Table(name = "Epoch")
public class EpochEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long satelliteId;
	private Long sequenceNumber;
	private Timestamp referenceTime;

	public EpochEntity() {
	}

	public EpochEntity(Long satelliteId, Long sequenceNumber, Timestamp referenceTime) {
		super();
		this.satelliteId = satelliteId;
		this.sequenceNumber = sequenceNumber;
		this.referenceTime = referenceTime;
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

	public final Timestamp getReferenceTime() {
		return referenceTime;
	}

	public final void setReferenceTime(Timestamp referenceTime) {
		this.referenceTime = referenceTime;
	}
	

}
