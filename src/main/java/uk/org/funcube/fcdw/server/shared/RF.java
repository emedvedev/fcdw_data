// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;



public class RF {
	
	private Long sensorId;
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
	
	public RF() {
	}

	public final Long getSensorId() {
		return sensorId;
	}

	public final void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
	}

	/**
	 * @return the c1
	 */
	public final Long getC1() {
		return c1;
	}

	/**
	 * @param c1 the c1 to set
	 */
	public final void setC1(Long c1) {
		this.c1 = c1;
	}

	/**
	 * @return the c2
	 */
	public final Long getC2() {
		return c2;
	}

	/**
	 * @param c2 the c2 to set
	 */
	public final void setC2(Long c2) {
		this.c2 = c2;
	}

	/**
	 * @return the c3
	 */
	public final Long getC3() {
		return c3;
	}

	/**
	 * @param c3 the c3 to set
	 */
	public final void setC3(Long c3) {
		this.c3 = c3;
	}

	/**
	 * @return the c4
	 */
	public final Long getC4() {
		return c4;
	}

	/**
	 * @param c4 the c4 to set
	 */
	public final void setC4(Long c4) {
		this.c4 = c4;
	}

	/**
	 * @return the c5
	 */
	public final Long getC5() {
		return c5;
	}

	/**
	 * @param c5 the c5 to set
	 */
	public final void setC5(Long c5) {
		this.c5 = c5;
	}

	/**
	 * @return the c6
	 */
	public final Long getC6() {
		return c6;
	}

	/**
	 * @param c6 the c6 to set
	 */
	public final void setC6(Long c6) {
		this.c6 = c6;
	}

	/**
	 * @return the c7
	 */
	public final Long getC7() {
		return c7;
	}

	/**
	 * @param c7 the c7 to set
	 */
	public final void setC7(Long c7) {
		this.c7 = c7;
	}

	/**
	 * @return the c8
	 */
	public final Long getC8() {
		return c8;
	}

	/**
	 * @param c8 the c8 to set
	 */
	public final void setC8(Long c8) {
		this.c8 = c8;
	}

	/**
	 * @return the c9
	 */
	public final Long getC9() {
		return c9;
	}

	/**
	 * @param c9 the c9 to set
	 */
	public final void setC9(Long c9) {
		this.c9 = c9;
	}

	/**
	 * @return the c10
	 */
	public final Long getC10() {
		return c10;
	}

	/**
	 * @param c10 the c10 to set
	 */
	public final void setC10(Long c10) {
		this.c10 = c10;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
		result = prime * result + ((c10 == null) ? 0 : c10.hashCode());
		result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
		result = prime * result + ((c3 == null) ? 0 : c3.hashCode());
		result = prime * result + ((c4 == null) ? 0 : c4.hashCode());
		result = prime * result + ((c5 == null) ? 0 : c5.hashCode());
		result = prime * result + ((c6 == null) ? 0 : c6.hashCode());
		result = prime * result + ((c7 == null) ? 0 : c7.hashCode());
		result = prime * result + ((c8 == null) ? 0 : c8.hashCode());
		result = prime * result + ((c9 == null) ? 0 : c9.hashCode());
		result = prime * result
				+ ((sensorId == null) ? 0 : sensorId.hashCode());
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
		RF other = (RF) obj;
		if (c1 == null) {
			if (other.c1 != null)
				return false;
		} else if (!c1.equals(other.c1))
			return false;
		if (c10 == null) {
			if (other.c10 != null)
				return false;
		} else if (!c10.equals(other.c10))
			return false;
		if (c2 == null) {
			if (other.c2 != null)
				return false;
		} else if (!c2.equals(other.c2))
			return false;
		if (c3 == null) {
			if (other.c3 != null)
				return false;
		} else if (!c3.equals(other.c3))
			return false;
		if (c4 == null) {
			if (other.c4 != null)
				return false;
		} else if (!c4.equals(other.c4))
			return false;
		if (c5 == null) {
			if (other.c5 != null)
				return false;
		} else if (!c5.equals(other.c5))
			return false;
		if (c6 == null) {
			if (other.c6 != null)
				return false;
		} else if (!c6.equals(other.c6))
			return false;
		if (c7 == null) {
			if (other.c7 != null)
				return false;
		} else if (!c7.equals(other.c7))
			return false;
		if (c8 == null) {
			if (other.c8 != null)
				return false;
		} else if (!c8.equals(other.c8))
			return false;
		if (c9 == null) {
			if (other.c9 != null)
				return false;
		} else if (!c9.equals(other.c9))
			return false;
		if (sensorId == null) {
			if (other.sensorId != null)
				return false;
		} else if (!sensorId.equals(other.sensorId))
			return false;
		return true;
	}

}
