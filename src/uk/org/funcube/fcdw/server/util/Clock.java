package uk.org.funcube.fcdw.server.util;

import java.util.Date;

/**
 * Interface for the system clock. Used to allow mocking and testing.
 */
public interface Clock {
    long currentTime();

    Date currentDate();
}
