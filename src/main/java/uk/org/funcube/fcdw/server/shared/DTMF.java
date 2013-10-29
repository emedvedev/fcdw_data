// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.shared;

public class DTMF {

	private long commandCount;
	private long lastCommand;

	public DTMF(long commandCount, long lastCommand) {
		super();
		this.commandCount = commandCount;
		this.lastCommand = lastCommand;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (commandCount ^ (commandCount >>> 32));
		result = prime * result + (int) (lastCommand ^ (lastCommand >>> 32));
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
		DTMF other = (DTMF) obj;
		if (commandCount != other.commandCount)
			return false;
		if (lastCommand != other.lastCommand)
			return false;
		return true;
	}

}
