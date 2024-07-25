package de.proxysystem.utils;

import java.util.concurrent.TimeUnit;

public class TimeProvider {

  public static Long getSeconds(Long millis) {
    return TimeUnit.MILLISECONDS.toSeconds(millis);
  }

  public static Long getMinutes(Long millis) {
    return TimeUnit.MILLISECONDS.toMinutes(millis);
  }

  public static Long getHours(Long millis) {
    return TimeUnit.MILLISECONDS.toHours(millis);
  }

  public static Long getDays(Long millis) {
    return TimeUnit.MILLISECONDS.toDays(millis);
  }

  public static String getFormatedTime(Long millis) {
    long days = getDays(millis);
    long hours = getHours(millis);
    long minutes = getMinutes(millis);
    long seconds = getSeconds(millis);

    if (days != 0) {
      return days + " Tag(e), " + hours + " Stunde(n) und " + minutes + " Minute(n)";
    }

    if (hours != 0) {
      return hours + " Stunde(n) und " + minutes + " Minute(n)";
    }

    if (minutes != 0) {
      return minutes + " Minute(n) und " + seconds + " Sekunde(n)";
    }

    if(seconds != 0) {
      return seconds + " Sekunde(n)";
    }

    return days + " Tag(e), " + hours + " Stunde(n) und " + minutes + " Minute(n)";
  }
}
