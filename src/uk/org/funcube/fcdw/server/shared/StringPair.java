package uk.org.funcube.fcdw.server.shared;

public class StringPair {

	private String name;
	private String value;

	public StringPair(final String name, final String value) {
		this.name = name;
		this.value = value;
	}

	public final String getName() {
		return name;
	}

	public final String getValue() {
		return value;
	}

}
