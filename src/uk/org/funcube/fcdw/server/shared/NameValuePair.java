package uk.org.funcube.fcdw.server.shared;

public class NameValuePair {

	private String name;
	private String value;

	public NameValuePair(final String name, final String value) {
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
