package p55.a2017.bdeb.qc.ca.ibdhelper.util;

import android.content.Context;
import android.util.Pair;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static Pair<String, String> getLocaleFormatedHours (Date date, Context context) {
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String hour = dateFormat.format(calendar.getTime());
        String dayTime = null;

        for (EnumDayTime enumDayTime : EnumDayTime.values()) {
            if (enumDayTime.getStartHour() <= calendar.get(Calendar.HOUR_OF_DAY)) {
                dayTime = enumDayTime.getText(context);
            }
            else if (enumDayTime.getStartHour() > calendar.get(Calendar.HOUR_OF_DAY)) {
                break;
            }
        }

        if (calendar.get(Calendar.HOUR_OF_DAY) < 6) {
            dayTime = EnumDayTime.MIDNIGHT.getText(context);
        }

        return new Pair<>(hour, dayTime);
    }
}
