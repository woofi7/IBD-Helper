package p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper;

public class BowelMotion {
    private long id = -1;
    private int hours;
    private int minutes;
    private String color;
    private int consistency;
    private int quantity;
    private int importance;
    private int blood;
    private int pain;
    private int mucus;
    private String note;

    public BowelMotion() {
    }

    public BowelMotion(long id, int hours, int minutes, String color, int consistency, int quantity, int importance, int blood, int pain, int mucus, String note) {
        this.id = id;
        this.hours = hours;
        this.minutes = minutes;
        this.color = color;
        this.consistency = consistency;
        this.quantity = quantity;
        this.importance = importance;
        this.blood = blood;
        this.pain = pain;
        this.mucus = mucus;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getConsistency() {
        return consistency;
    }

    public void setConsistency(int consistency) {
        this.consistency = consistency;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getPain() {
        return pain;
    }

    public void setPain(int pain) {
        this.pain = pain;
    }

    public int getMucus() {
        return mucus;
    }

    public void setMucus(int mucus) {
        this.mucus = mucus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
}
