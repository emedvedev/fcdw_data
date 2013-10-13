package uk.org.funcube.fcdw.domain;

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
@Table(name = "WholeOrbitData")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("wod")
public class WholeOrbitDataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long satelliteId;
	private long sequenceNumber;
	private long frameNumber;
	private Date createdDate;

	private long c1;
	private long c2;
	private long c3;
	private long c4;
	private long c5;
	private long c6;
	private long c7;
	private long c8;
	private long c9;
	private long c10;
	private long c11;
	private long c12;
	private long c13;
	private long c14;
	private long c15;
	private long c16;
	private long c17;
	private long c18;
	private long c19;
	private long c20;
	private long c21;
	private long c22;
	private long c23;
	private long c24;
	private long c25;
	private long c26;

	public WholeOrbitDataEntity() {
	}

	public final long getC1() {
		return c1;
	}

	public final long getC10() {
		return c10;
	}

	public final long getC11() {
		return c11;
	}

	public final long getC12() {
		return c12;
	}

	public final long getC13() {
		return c13;
	}

	public final long getC14() {
		return c14;
	}

	public final long getC15() {
		return c15;
	}

	public final long getC16() {
		return c16;
	}

	public final long getC17() {
		return c17;
	}

	public final long getC18() {
		return c18;
	}

	public final long getC19() {
		return c19;
	}

	public final long getC2() {
		return c2;
	}

	public final long getC20() {
		return c20;
	}

	public final long getC21() {
		return c21;
	}

	public final long getC22() {
		return c22;
	}

	public final long getC23() {
		return c23;
	}

	public final long getC24() {
		return c24;
	}

	public final long getC25() {
		return c25;
	}

	public final long getC26() {
		return c26;
	}

	public final long getC3() {
		return c3;
	}

	public final long getC4() {
		return c4;
	}

	public final long getC5() {
		return c5;
	}

	public final long getC6() {
		return c6;
	}

	public final long getC7() {
		return c7;
	}

	public final long getC8() {
		return c8;
	}

	public final long getC9() {
		return c9;
	}

	public final Date getCreatedDate() {
		return createdDate;
	}

	public final long getFrameNumber() {
		return frameNumber;
	}

	public final long getId() {
		return id;
	}

	public final long getSatelliteId() {
		return satelliteId;
	}

	public long getSequenceNumber() {
		return sequenceNumber;
	}

	public final void setC1(final long c1) {
		this.c1 = c1;
	}

	public final void setC10(final long c10) {
		this.c10 = c10;
	}

	public final void setC11(final long c11) {
		this.c11 = c11;
	}

	public final void setC12(final long c12) {
		this.c12 = c12;
	}

	public final void setC13(final long c13) {
		this.c13 = c13;
	}

	public final void setC14(final long c14) {
		this.c14 = c14;
	}

	public final void setC15(final long c15) {
		this.c15 = c15;
	}

	public final void setC16(final long c16) {
		this.c16 = c16;
	}

	public final void setC17(final long c17) {
		this.c17 = c17;
	}

	public final void setC18(final long c18) {
		this.c18 = c18;
	}

	public final void setC19(final long c19) {
		this.c19 = c19;
	}

	public final void setC2(final long c2) {
		this.c2 = c2;
	}

	public final void setC20(final long c20) {
		this.c20 = c20;
	}

	public final void setC21(final long c21) {
		this.c21 = c21;
	}

	public final void setC22(final long c22) {
		this.c22 = c22;
	}

	public final void setC23(final long c23) {
		this.c23 = c23;
	}

	public final void setC24(final long c24) {
		this.c24 = c24;
	}

	public final void setC25(final long c25) {
		this.c25 = c25;
	}

	public final void setC26(final long c26) {
		this.c26 = c26;
	}

	public final void setC3(final long c3) {
		this.c3 = c3;
	}

	public final void setC4(final long c4) {
		this.c4 = c4;
	}

	public final void setC5(final long c5) {
		this.c5 = c5;
	}

	public final void setC6(final long c6) {
		this.c6 = c6;
	}

	public final void setC7(final long c7) {
		this.c7 = c7;
	}

	public final void setC8(final long c8) {
		this.c8 = c8;
	}

	public final void setC9(final long c9) {
		this.c9 = c9;
	}

	public final void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	public final void setFrameNumber(final long frameNumber) {
		this.frameNumber = frameNumber;
	}

	public final void setId(final long id) {
		this.id = id;
	}

	public final void setSatelliteId(final long satelliteId) {
		this.satelliteId = satelliteId;
	}

	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
}
