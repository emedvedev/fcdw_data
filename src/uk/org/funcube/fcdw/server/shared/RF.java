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
	
	public String toString() {
		return "EPS: \n"
				+ String.format("%d %d %d %d %d %d %d %d %d %d %d\n", sensorId,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10);
	}

	public String toBinaryString() {
		return null;
	}

}
