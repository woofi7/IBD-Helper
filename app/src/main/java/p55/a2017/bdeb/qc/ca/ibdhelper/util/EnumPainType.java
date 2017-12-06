package p55.a2017.bdeb.qc.ca.ibdhelper.util;

import android.content.Context;

import p55.a2017.bdeb.qc.ca.ibdhelper.R;


public enum EnumPainType {
    STING(0, R.string.painType_sting),
    CRAMP(1, R.string.painType_cramp),
    BURN(2, R.string.painType_burn)
    ;

    private int id;
    private int text;

    EnumPainType(int id, int text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public static EnumPainType fromId(int id) {
        for (EnumPainType enumPainType : EnumPainType.values()) {
            if (enumPainType.getId() == id) {
                return enumPainType;
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
