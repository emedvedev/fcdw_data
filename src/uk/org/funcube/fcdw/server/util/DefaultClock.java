package uk.org.funcube.fcdw.server.util;

import java.util.Date;

/**
 * Default implementation of the {@link Clock} that uses the system clock.
 */
public class DefaultClock implements Clock {
    public DefaultClock() {
        super();
    }

    @Override
    public Date currentDate() {
        return new Date();
    }

    @Override
    public long currentTime() {
        return System.currentTimeMillis();
    }
}
