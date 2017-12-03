package p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import p55.a2017.bdeb.qc.ca.ibdhelper.util.EnumPainType;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ibdHelper.db";
    private static final int DBVERSION = 1;
    private Context context;
    private static DbHelper instance = null;

    //Tables
    private static final String DAY_TABLE = "day";
    private static final String PAIN_TABLE = "pain";
    private static final String MEAL_TABLE = "meal";
    private static final String BOWEL_MOVEMENT_TABLE = "bowelMovement";

    //Columns
    private static final String DAY_ID = "_id";
    private static final String DAY_DATE = "date";

    private static final String PAIN_ID = "_id";
    private static final String PAIN_DAY_ID = "day_id";
    private static final String PAIN_TIME_HOUR = "time_hour";
    private static final String PAIN_TIME_MINUTE = "time_minute";
    private static final String PAIN_INTENSITY = "intensity";
    private static final String PAIN_TYPE = "type";
    private static final String PAIN_LOCATION = "location";
    private static final String PAIN_NOTE = "note";
    private static final String PAIN_ACTIVE = "active";

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DBVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlGeneral = "CREATE TABLE " + DAY_TABLE + "("
                + DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DAY_DATE + " TEXT"
                + ")";
        db.execSQL(sqlGeneral);

        String sqlPain = "CREATE TABLE " + PAIN_TABLE + "("
                + PAIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PAIN_DAY_ID + " INTEGER,"
                + PAIN_TIME_HOUR + " INTEGER,"
                + PAIN_TIME_MINUTE + " INTEGER,"
                + PAIN_INTENSITY + " INTEGER,"
                + PAIN_TYPE + " INTEGER,"
                + PAIN_LOCATION + " VARCHAR,"
                + PAIN_NOTE + " VARCHAR,"
                + PAIN_ACTIVE + " NUMERIC,"
                + "FOREIGN KEY (" + PAIN_DAY_ID + ") REFERENCES " + DAY_TABLE + "(" + DAY_ID + ")"
                + ")";
        db.execSQL(sqlPain);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    private long getDayId(Date dayDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateKey = sdf.format(dayDate);

        String selectQuery = "SELECT * FROM " + DAY_TABLE + " WHERE " + DAY_DATE + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{dateKey});
        try {
            if (cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndex(DAY_ID));
            }

            ContentValues values = new ContentValues();
            values.put(DAY_DATE, dateKey);
            return db.insert(DAY_TABLE, null, values);
        } finally {
            cursor.close();
            db.close();
        }
    }

    @NonNull
    private ContentValues preparePainQuery(Pain painData) {
        ContentValues values = new ContentValues();
        values.put(PAIN_TIME_HOUR, painData.getHours());
        values.put(PAIN_TIME_MINUTE, painData.getMinutes());
        values.put(PAIN_INTENSITY, painData.getIntensity());
        values.put(PAIN_TYPE, painData.getPainType().getId());
        values.put(PAIN_LOCATION, painData.getLocation());
        values.put(PAIN_NOTE, painData.getNote());
        values.put(PAIN_ACTIVE, true);
        return values;
    }

    public void savePain(Pain painData, Date dayDate) {
        SQLiteDatabase db = null;

        try {
            ContentValues values = preparePainQuery(painData);
            if (painData.getId() == -1) {
                values.put(PAIN_DAY_ID, getDayId(dayDate));
                db = this.getWritableDatabase();

                long id = db.insert(PAIN_TABLE, null, values);
                painData.setId(id);

            } else {
                db = this.getWritableDatabase();
                db.update(PAIN_TABLE, values, PAIN_ID + " = ?", new String[]{String.valueOf(painData.getId())});
            }
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void deletePain(long painId) {
        activePain(painId, false);
    }

    public void undeletePain(long painId) {
        activePain(painId, true);
    }

    public void activePain(long painId, boolean active) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PAIN_ACTIVE, active);

            db.update(PAIN_TABLE, values, PAIN_ID + " = ?", new String[]{String.valueOf(painId)});
        } finally {
            if (db != null)
                db.close();
        }
    }

    public List<Pain> loadPainData(Date dayDate) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        long dayId = getDayId(dayDate);
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("select * from " + PAIN_TABLE + " where " + PAIN_DAY_ID + " = " + dayId, null);
            ArrayList<Pain> pains = new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Pain pain = new Pain(
                            cursor.getLong(cursor.getColumnIndex(PAIN_ID)),
                            cursor.getInt(cursor.getColumnIndex(PAIN_TIME_HOUR)),
                            cursor.getInt(cursor.getColumnIndex(PAIN_TIME_MINUTE)),
                            cursor.getInt(cursor.getColumnIndex(PAIN_INTENSITY)),
                            EnumPainType.fromId(cursor.getInt(cursor.getColumnIndex(PAIN_TYPE))),
                            cursor.getString(cursor.getColumnIndex(PAIN_LOCATION)),
                            cursor.getString(cursor.getColumnIndex(PAIN_NOTE)));
                    pains.add(pain);
                    cursor.moveToNext();
                }
            }

            return pains;
        }
        finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    public List<Long> loadPainDataId(Date dayDate) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        long dayId = getDayId(dayDate);
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("select * from " + PAIN_TABLE + " where " + PAIN_DAY_ID + " = " + dayId + " and " + PAIN_ACTIVE + " = 1", null);
            ArrayList<Long> painIds = new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    painIds.add(cursor.getLong(cursor.getColumnIndex(PAIN_ID)));
                    cursor.moveToNext();
                }
            }

            return painIds;
        }
        finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    @Nullable
    public Pain loadPain(long painId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("select * from " + PAIN_TABLE + " where " + PAIN_ID + " = " + painId, null);
            Pain pain = null;
            if (cursor.moveToFirst()) {
                pain = new Pain(
                        cursor.getLong(cursor.getColumnIndex(PAIN_ID)),
                        cursor.getInt(cursor.getColumnIndex(PAIN_TIME_HOUR)),
                        cursor.getInt(cursor.getColumnIndex(PAIN_TIME_MINUTE)),
                        cursor.getInt(cursor.getColumnIndex(PAIN_INTENSITY)),
                        EnumPainType.fromId(cursor.getInt(cursor.getColumnIndex(PAIN_TYPE))),
                        cursor.getString(cursor.getColumnIndex(PAIN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(PAIN_NOTE)));
            }

            return pain;
        }
        finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }
}