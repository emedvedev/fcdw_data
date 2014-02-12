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
@Table(name = "MinMax")
public class MinMaxEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long satelliteId;
	private Long channel;
	private Long minimum;
	private Long maximum;
	private Date refDate;
	private Boolean enabled;
	
	public MinMaxEntity() {
	}

	/**
	 * @param satelliteId
	 * @param channel
	 * @param minimum
	 * @param maximum
	 */
	public MinMaxEntity(Long satelliteId, Long channel, Long minimum,
			Long maximum, Date refDate, Boolean enabled) {
		super();
		this.satelliteId = satelliteId;
		this.channel = channel;
		this.minimum = minimum;
		this.maximum = maximum;
		this.refDate = refDate;
		this.enabled = enabled;
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

	public final Long getChannel() {
		return channel;
	}

	public final void setChannel(Long channel) {
		this.channel = channel;
	}

	public final Long getMinimum() {
		return minimum;
	}

	public final void setMinimum(Long minimum) {
		this.minimum = minimum;
	}

	public final Long getMaximum() {
		return maximum;
	}

	public final void setMaximum(Long maximum) {
		this.maximum = maximum;
	}

	public final Date getRefDate() {
		return refDate;
	}

	public final void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public final Boolean isEnabled() {
		return enabled;
	}

	public final void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((maximum == null) ? 0 : maximum.hashCode());
		result = prime * result + ((minimum == null) ? 0 : minimum.hashCode());
		result = prime * result + ((refDate == null) ? 0 : refDate.hashCode());
		result = prime * result
				+ ((satelliteId == null) ? 0 : satelliteId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MinMaxEntity)) {
			return false;
		}
		MinMaxEntity other = (MinMaxEntity) obj;
		if (channel == null) {
			if (other.channel != null) {
				return false;
			}
		} else if (!channel.equals(other.channel)) {
			return false;
		}
		if (enabled == null) {
			if (other.enabled != null) {
				return false;
			}
		} else if (!enabled.equals(other.enabled)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (maximum == null) {
			if (other.maximum != null) {
				return false;
			}
		} else if (!maximum.equals(other.maximum)) {
			return false;
		}
		if (minimum == null) {
			if (other.minimum != null) {
				return false;
			}
		} else if (!minimum.equals(other.minimum)) {
			return false;
		}
		if (refDate == null) {
			if (other.refDate != null) {
				return false;
			}
		} else if (!refDate.equals(other.refDate)) {
			return false;
		}
		if (satelliteId == null) {
			if (other.satelliteId != null) {
				return false;
			}
		} else if (!satelliteId.equals(other.satelliteId)) {
			return false;
		}
		return true;
	}
	
}

