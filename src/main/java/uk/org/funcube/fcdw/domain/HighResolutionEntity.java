// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "HighPrecision")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("highPrecision")
public class HighResolutionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	private Date createdDate;
	private Long sequenceNumber;
	private Long satelliteId;
	private Timestamp satelliteTime;

	public HighResolutionEntity() {
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
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

	public final Date getCreatedDate() {
		return createdDate;
	}

	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public final Long getSequenceNumber() {
		return sequenceNumber;
	}

	public final void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public final Long getSatelliteId() {
		return satelliteId;
	}

	public final void setSatelliteId(Long satelliteId) {
		this.satelliteId = satelliteId;
	}

	public final Timestamp getSatelliteTime() {
		return satelliteTime;
	}

	public final void setSatelliteTime(Timestamp satelliteTime) {
		this.satelliteTime = satelliteTime;
	}

	public String toString() {
		return "HP: \n"
				+ String.format("%d %d %d %d %d %d %d %d %d %d\n", c1,
						c2, c3, c4, c5, c6, c7, c8, c9, c10);
	}

	public String toBinaryString() {
		// TODO Auto-generated method stub
		return null;
	}

}
