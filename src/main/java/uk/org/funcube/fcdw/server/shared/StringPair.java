// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

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
