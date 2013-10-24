// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

import uk.org.funcube.fcdw.domain.MinMaxEntity;


public class MinMax {
	
	private Long satelliteId;
	private Long channel;
	private Long minimum;
	private Long maximum;
	
	public MinMax() {
	}

	/**
	 * @param satelliteId
	 * @param channel
	 * @param minimum
	 * @param maximum
	 */
	public MinMax(Long satelliteId, Long channel, Long minimum,
			Long maximum) {
		super();
		this.satelliteId = satelliteId;
		this.channel = channel;
		this.minimum = minimum;
		this.maximum = maximum;
	}
	
	public MinMax(MinMaxEntity entity) {
		this.satelliteId = entity.getSatelliteId();
		this.channel = entity.getChannel();
		this.minimum = entity.getMinimum();
		this.maximum = entity.getMaximum();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((maximum == null) ? 0 : maximum.hashCode());
		result = prime * result + ((minimum == null) ? 0 : minimum.hashCode());
		result = prime * result
				+ ((satelliteId == null) ? 0 : satelliteId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MinMax other = (MinMax) obj;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (maximum == null) {
			if (other.maximum != null)
				return false;
		} else if (!maximum.equals(other.maximum))
			return false;
		if (minimum == null) {
			if (other.minimum != null)
				return false;
		} else if (!minimum.equals(other.minimum))
			return false;
		if (satelliteId == null) {
			if (other.satelliteId != null)
				return false;
		} else if (!satelliteId.equals(other.satelliteId))
			return false;
		return true;
	}
	
}

