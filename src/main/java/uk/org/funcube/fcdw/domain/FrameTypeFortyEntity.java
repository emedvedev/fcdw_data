package uk.org.funcube.fcdw.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FrameTypeForty")
public class FrameTypeFortyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long sequenceNumber;
	private Date createdDate;
	private String hexString;

	public FrameTypeFortyEntity(long sequenceNumber, Date createdDate, String hexString) {
		this.sequenceNumber = sequenceNumber;
		this.createdDate = createdDate;
		this.hexString = hexString;
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final long getSequenceNumber() {
		return sequenceNumber;
	}

	public final void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public final Date getCreatedDate() {
		return createdDate;
	}

	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public final String getHexString() {
		return hexString;
	}

	public final void setHexString(String hexString) {
		this.hexString = hexString;
	}

}
