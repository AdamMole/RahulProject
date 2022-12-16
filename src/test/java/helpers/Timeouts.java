package helpers;

import java.time.Duration;

public class Timeouts {
    public static final Duration DEFAULT_POLLING_INTERVAL = Duration.ofMillis(50L);
    public static final Duration SPINNER_IN_PROGRESS_TIMEOUT = Duration.ofMinutes(2L);
    public static final Duration TIMEOUT_200_MILLISECONDS = Duration.ofMillis(200L);
    public static final Duration TIMEOUT_1_SECOND = Duration.ofSeconds(1L);
    public static final Duration TIMEOUT_2_SECONDS = Duration.ofSeconds(2L);
    public static final Duration TIMEOUT_3_SECONDS = Duration.ofSeconds(3L);
    public static final Duration TIMEOUT_5_SECONDS = Duration.ofSeconds(5L);
    public static final Duration TIMEOUT_10_SECONDS = Duration.ofSeconds(10L);
    public static final Duration TIMEOUT_15_SECONDS = Duration.ofSeconds(15L);
    public static final Duration TIMEOUT_20_SECONDS = Duration.ofSeconds(20L);
    public static final Duration TIMEOUT_30_SECONDS = Duration.ofSeconds(30L);
    public static final Duration TIMEOUT_45_SECONDS = Duration.ofSeconds(45L);
    public static final Duration TIMEOUT_90_SECONDS = Duration.ofSeconds(90L);
    public static final Duration TIMEOUT_1_MINUTE = Duration.ofMinutes(1L);
    public static final Duration TIMEOUT_2_MINUTES = Duration.ofMinutes(2L);
    public static final Duration TIMEOUT_3_MINUTES = Duration.ofMinutes(3L);
    public static final Duration TIMEOUT_5_MINUTES = Duration.ofMinutes(5L);
    public static final Duration DEFAULT_TIMEOUT;

    public Timeouts() {
    }

    static {
        DEFAULT_TIMEOUT = TIMEOUT_45_SECONDS;
    }
}

