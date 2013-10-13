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
