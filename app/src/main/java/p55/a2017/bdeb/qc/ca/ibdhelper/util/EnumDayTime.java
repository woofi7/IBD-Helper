package p55.a2017.bdeb.qc.ca.ibdhelper.util;

import android.content.Context;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;

public enum EnumDayTime {
    MORNING(0, R.string.timeOfDay_morning, 6),
    MIDDAY(1, R.string.timeOfDay_midday, 10),
    AFTERNOON(2, R.string.timeOfDay_afternoon, 13),
    EVENING(3, R.string.timeOfDay_evening, 18),
    MIDNIGHT(4, R.string.timeOfDay_midnight, 23),
    ;

    private int id;
    private int text;
    private int startHour;

    EnumDayTime(int id, int text, int startHour) {
        this.id = id;
        this.text = text;
        this.startHour = startHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getId() {
        return id;
    }

    public String getText(Context context) {
        return context.getString(text);
    }
}
