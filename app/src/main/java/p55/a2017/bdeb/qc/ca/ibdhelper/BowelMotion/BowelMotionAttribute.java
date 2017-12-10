package p55.a2017.bdeb.qc.ca.ibdhelper.BowelMotion;

import android.content.Context;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;

public enum BowelMotionAttribute {
    COLOR(0, AttributeType.SELECT_LIST, R.string.activity_bowel_motion_tag_color, null, null, null, true),
    CONSISTENCY(1, AttributeType.PROGRESS_BAR, R.string.activity_bowel_motion_tag_consistency, "0", "20", "10", true),
    QUANTITY(2, AttributeType.PROGRESS_BAR, R.string.activity_bowel_motion_tag_quantity, "0", "10", "5", true),
    IMPORTANCE(3, AttributeType.PROGRESS_BAR, R.string.activity_bowel_motion_tag_importance, "0", "10", "0", false),
    BLOOD(4, AttributeType.PROGRESS_BAR, R.string.activity_bowel_motion_tag_blood, "0", "10", "0", false),
    MUCUS(5, AttributeType.PROGRESS_BAR, R.string.activity_bowel_motion_tag_mucus, "0", "10", "0", false),
    PAIN(6, AttributeType.PROGRESS_BAR, R.string.activity_main_day_txt_pain, "0", "10", "0", false)
    ;

    private int id;
    private AttributeType type;
    private int text;
    private String minValue;
    private String maxValue;
    private String currentValue;
    private boolean display;

    BowelMotionAttribute(int id, AttributeType type, int text, String minValue, String maxValue, String currentValue, boolean display) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        this.display = display;
    }

    public int getId() {
        return id;
    }

    public static BowelMotionAttribute fromId(int id) {
        for (BowelMotionAttribute attribute : BowelMotionAttribute.values()) {
            if (attribute.getId() == id) {
                return attribute;
            }
        }
        throw new IllegalArgumentException("Invalid id : " + id);
    }

    public String getText(Context context) {
        return context.getString(text);
    }

    public int getTextRessource() {
        return text;
    }
}

enum AttributeType {
    SELECT_LIST(0),
    PROGRESS_BAR(1);

    private int id;

    AttributeType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BowelMotionAttribute fromId(int id) {
        for (BowelMotionAttribute attribute : BowelMotionAttribute.values()) {
            if (attribute.getId() == id) {
                return attribute;
            }
        }
        throw new IllegalArgumentException("Invalid id : " + id);
    }
}