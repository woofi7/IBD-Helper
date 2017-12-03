package p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper;

import p55.a2017.bdeb.qc.ca.ibdhelper.util.EnumPainType;

public class Pain {
    private long id = -1;
    private int hours;
    private int minutes;
    private int intensity;
    private EnumPainType painType;
    private String location;
    private String note;

    public Pain() {
    }

    public Pain(long id, int hours, int minutes, int intensity, EnumPainType painType, String location, String note) {
        this.id = id;
        this.hours = hours;
        this.minutes = minutes;
        this.intensity = intensity;
        this.painType = painType;
        this.location = location;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getIntensity() {
        return intensity;
    }

    public String getNote() {
        return note;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public EnumPainType getPainType() {
        return painType;
    }

    public void setPainType(EnumPainType painType) {
        this.painType = painType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
