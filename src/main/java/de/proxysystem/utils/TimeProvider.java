package de.proxysystem.utils;

public class TimeProvider {

  public static Long getSeconds(Long millis) {
    return ((millis / 1000) % 60);
  }

  public static Long getMinutes(Long millis) {
    return ((millis / 1000) / 60) % 60;
  }

  public static Long getHours(Long millis) {
    return (((millis / 1000) / 60) / 60) % 24;
  }

  public static Long getDays(Long millis) {
    return (((millis / 1000) / 60) / 60) / 24;
  }

  public static String getFormatedTime(Long millis) {
    long milli = millis;
    milli /= 1000;
    long seconds = (milli % 60);
    milli /= 60;
    long minutes = (milli % 60);
    milli /= 60;
    long hours = (milli % 24);
    milli /= 24;
    long days = milli;

    if (days != 0) {
      return days + " Tag(e), " + hours + " Stunde(n) und " + minutes + " Minute(n)";
    }

    if (hours != 0) {
      return hours + " Stunde(n) und " + minutes + " Minute(n)";
    }
    if (minutes != 0) {
      return minutes + " Minute(n) und " + seconds + " Sekunde(n)";
    }
    return days + " Tag(e), " + hours + " Stunde(n) und " + minutes + " Minute(n)";
  }

  public static String getTimeStringWithHighestTimeForm(Long millis) {
    long days = getDays(millis);
    long hours = getHours(millis);
    long minutes = getMinutes(millis);

    if (days != 0) {
      return days + " Tag" + (days > 1 ? "e" : "");
    }

    if (hours != 0) {
      return hours + " Stunde" + (hours > 1 ? "n" : "");
    }
    if (minutes != 0) {
      return minutes + " Minute" + (minutes > 1 ? "n" : "");
    }

    return days + " Tag(e), " + hours + " Stunde(n) und " + minutes + " Minute(n)";
  }
}
