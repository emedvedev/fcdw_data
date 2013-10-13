package uk.org.funcube.fcdw.server.shared;

public class ValMinMax {

	private String name;
	private String value;
	private String min;
	private String max;

	public ValMinMax(final String name, final String value, final String min,
			final String max) {
		this.name = name;
		this.value = value;
		this.min = min;
		this.max = max;
	}

	public final String getName() {
		return name;
	}

	public final String getValue() {
		return value;
	}

	public final String getMin() {
		return min;
	}

	public final String getMax() {
		return max;
	}

}
