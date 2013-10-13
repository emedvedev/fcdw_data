package uk.org.funcube.fcdw.server.util;

import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Default implementation of the {@link Clock} that uses the UTC clock.
 */
public class UTCClock implements Clock {
	
	private static final SimpleTimeZone TZ = new SimpleTimeZone(0, "UTC");
	
    public UTCClock() {
        super();
    }

    public Date currentDate() {
        return Calendar.getInstance(TZ).getTime();
    }

    public long currentTime() {
        return Calendar.getInstance(TZ).getTime().getTime();
    }
}
