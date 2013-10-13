package uk.org.funcube.fcdw.server.util;

import java.util.Date;

/**
 * Default implementation of the {@link Clock} that uses the system clock.
 */
public class OffsetClock implements Clock {

    private long offset;
    private final Clock baseClock;

    public OffsetClock(final Clock baseClock) {
        this.baseClock = baseClock;
    }

    @Override
    public Date currentDate() {
        return new Date(currentTime());
    }

    @Override
    public long currentTime() {
        return baseClock.currentTime() + offset;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(final long offset) {
        this.offset = offset;
    }

    public Clock getBaseClock() {
        return baseClock;
    }
}
