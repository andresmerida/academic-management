package org.am.core.web.util;

import java.time.LocalTime;

public final class UtilConstants {
    public static final LocalTime DEFAULT_START_TIME_SCHEDULE = LocalTime.of(6,45);
    public static final LocalTime DEFAULT_END_TIME_SCHEDULE = LocalTime.of(21,45);
    public static final Integer DEFAULT_TIME_INTERVAL_SCHEDULE = 90;   // in minutes

    public static final Boolean DEFAULT_LUNCH_TIME_SCHEDULE = false;
    public static final Integer DEFAULT_BETWEEN_PERIOD = 0;

    private UtilConstants() {}
}
