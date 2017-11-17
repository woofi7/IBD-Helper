package p55.a2017.bdeb.qc.ca.ibdhelper.Pain;

import java.util.Date;

import p55.a2017.bdeb.qc.ca.ibdhelper.util.EnumPainType;

public class Pain {
    private Date dayTime;
    private double intensity;
    private EnumPainType painType;
    private boolean[] location;

    public Date getDayTime() {
        return dayTime;
    }

    public void setDayTime(Date dayTime) {
        this.dayTime = dayTime;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public EnumPainType getPainType() {
        return painType;
    }

    public void setPainType(EnumPainType painType) {
        this.painType = painType;
    }

    public boolean[] getLocation() {
        return location;
    }

    public void setLocation(boolean[] location) {
        this.location = location;
    }
}
