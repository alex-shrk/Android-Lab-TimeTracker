package ru.ssau.sanya.timetracker;


import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeHelper {
    public static String getInterval(DateTime start, DateTime end) {
        Period period = new Period(start.getMillis(),end.getMillis());
        //Log.i("period",period.toString());
        return PeriodFormat.getDefault().print(period);

    }

    public static DateTime convertStringToDateTime(String date, String time) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-DD HH:mm");
        return DateTime.parse(date + " " + time, dtf);
    }
    public static String prepareDatetimeStringForView(String datetime) {
        String format = "YYYY-MM-DD HH:mm";
        DateTime dt = DateTime.parse(datetime);
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(dt.toDate());
    }
    public static String prepareDatetimeIntervalStringForView(String datetime) {
        //todo rus translate??
        //String format = "DD HH:mm";
        //DateTime dt = DateTime.parse(datetime);
        //final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        //return sdf.format(dt.toDate());
        return datetime;
    }

    public static String convertTimeToString(LocalTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
        return dateTime.toString(dtf);
    }

    public static String convertDateToString(Calendar date) {
        String format = "YYYY-MM-DD"; //format for correct working with datetime function sqlite
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(date.getTime());
    }
}
