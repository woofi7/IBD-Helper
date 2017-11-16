package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.content.Context;

import java.util.Calendar;

public enum EnumDay {
    MONDAY(0, R.string.activity_main_group_day_monday, Calendar.MONDAY),
    TUESDAY(1, R.string.activity_main_group_day_tuesday, Calendar.TUESDAY),
    WEDNESDAY(2, R.string.activity_main_group_day_wednesday, Calendar.WEDNESDAY),
    THURSDAY(3, R.string.activity_main_group_day_thursday, Calendar.THURSDAY),
    FRIDAY(4, R.string.activity_main_group_day_friday, Calendar.FRIDAY),
    SATURDAY(5, R.string.activity_main_group_day_saturday, Calendar.SATURDAY),
    SUNDAY(6, R.string.activity_main_group_day_sunday, Calendar.SUNDAY),
    ;

    private int id;
    private int text;
    private int dayOfWeek;

    EnumDay(int id, int text, int dayOfWeek) {
        this.id = id;
        this.text = text;
        this.dayOfWeek = dayOfWeek;
    }

    public static EnumDay fromDayOfWeek (int dayOfWeek) {
        for (EnumDay enumDay : EnumDay.values()) {
            if (enumDay.dayOfWeek == dayOfWeek) {
                return enumDay;
            }
        }
        throw new IllegalArgumentException("Invalid day of week : " + dayOfWeek);
    }

    public int getId() {
        return id;
    }

    public String getText(Context context) {
        return context.getString(text);
    }
}