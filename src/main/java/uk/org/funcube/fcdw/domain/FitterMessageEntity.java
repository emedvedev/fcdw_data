// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FitterMessage")
public class FitterMessageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String messageText;

	private Date lastReceived;

	private Long satelliteId;

	private Boolean debug;
	
	private Boolean display;
	
	private String slot;

	public FitterMessageEntity() {
	}

	public FitterMessageEntity(String theMessageText, Date lastReceived, Long satelliteId, Boolean debug,
			String slot) {
		super();
		this.messageText = theMessageText;
		this.lastReceived = lastReceived;
		this.satelliteId = satelliteId;
		this.debug = debug;
		this.display = !debug;
		this.slot = slot;
	}

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final String getMessageText() {
		return messageText;
	}

	public final void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public final Date getLastReceived() {
		return lastReceived;
	}

	public final void setLastReceived(Date lastReceived) {
		this.lastReceived = lastReceived;
	}

	public final Long getSatelliteId() {
		return satelliteId;
	}

	public final void setSatelliteId(Long satelliteId) {
		this.satelliteId = satelliteId;
	}

	public final Boolean getDebug() {
		return debug;
	}

	public final void setDebug(Boolean debug) {
		this.debug = debug;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public final String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

}
