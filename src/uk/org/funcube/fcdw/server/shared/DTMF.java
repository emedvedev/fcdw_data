// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

public class DTMF {

	private long commandCount;
	private long lastCommand;

	public DTMF(String binaryString) {
		
		commandCount = Long.parseLong(binaryString.substring(0, 6), 2);
	    lastCommand = Long.parseLong(binaryString.substring(6, 11), 2);
	}

	public final long getCommandCount() {
		return commandCount;
	}

	public final long getLastCommand() {
		return lastCommand;
	}

}
