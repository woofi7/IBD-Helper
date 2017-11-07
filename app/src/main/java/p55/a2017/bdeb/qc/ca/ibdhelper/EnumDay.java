package p55.a2017.bdeb.qc.ca.ibdhelper;

import android.content.Context;

public enum EnumDay {
    MONDAY(0, R.string.activity_main_group_day_monday),
    TUESDAY(1, R.string.activity_main_group_day_tuesday),
    WEDNESDAY(2, R.string.activity_main_group_day_wednesday),
    THURSDAY(3, R.string.activity_main_group_day_thursday),
    FRIDAY(4, R.string.activity_main_group_day_friday),
    SATURDAY(5, R.string.activity_main_group_day_saturday),
    SUNDAY(6, R.string.activity_main_group_day_sunday),
    ;

    private int id;
    private int text;


    EnumDay(int id, int text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText(Context context) {
        return context.getString(text);
    }
}