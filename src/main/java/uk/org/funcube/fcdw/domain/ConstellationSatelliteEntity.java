package uk.org.funcube.fcdw.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ConstellationSatellite")
public class ConstellationSatelliteEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long constellationId;
	private Long catnum;
	boolean active;
	
	/**
	 * @param constellationId
	 * @param catnum
	 */
	public ConstellationSatelliteEntity(final Long id, final Long constellationId, final Long catnum, boolean active) {
		super();
		this.id = id;
		this.constellationId = constellationId;
		this.catnum = catnum;
		this.active = active;
	}

	public ConstellationSatelliteEntity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the constellationId
	 */
	public final Long getConstellationId() {
		return constellationId;
	}

	/**
	 * @param constellationId the constellationId to set
	 */
	public final void setConstellationId(Long constellationId) {
		this.constellationId = constellationId;
	}

	public final Long getCatnum() {
		return catnum;
	}

	public final void setCatnum(final Long catnum) {
		this.catnum = catnum;
	}

	/**
	 * @return the active
	 */
	public final boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public final void setActive(boolean active) {
		this.active = active;
	}
}
