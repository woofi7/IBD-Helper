package p55.a2017.bdeb.qc.ca.ibdhelper.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    private static final String PAIN_DAY_ID = "_id";
    private static final String PAIN_TIME = "time";
    private static final String PAIN_INTENSITY = "intensity";
    private static final String PAIN_TYPE = "type";
    private static final String PAIN_LOCATION = "location";
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
        /*
          CREATE TABLE day (
         _id INTEGER PRIMARY KEY AUTOINCREMENT,
         date NUMERIC
         );

         CREATE TABLE pain (
         day_id INTEGER,
         time NUMERIC,
         intensity REAL,
         type INTEGER,
         location BLOB,
         active NUMERIC,
         PRIMARY KEY (day_id, time),
         FOREIGN KEY(day_id) REFERENCES day(_id)
         );
         */
        String sqlGeneral = "CREATE TABLE " + DAY_TABLE + "("
                + DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DAY_DATE + " NUMERIC"
                + ")";
        db.execSQL(sqlGeneral);

        String sqlPain = "CREATE TABLE " + PAIN_TABLE + "("
                + PAIN_DAY_ID + " INTEGER,"
                + PAIN_TIME + " NUMERIC,"
                + PAIN_INTENSITY + " REAL,"
                + PAIN_TYPE + " INTEGER,"
                + PAIN_LOCATION + " BLOB,"
                + PAIN_ACTIVE + " NUMERIC,"
                + "PRIMARY KEY (" + PAIN_DAY_ID + ", " + PAIN_TIME + "),"
                + "FOREIGN KEY (" + PAIN_DAY_ID + ") REFERENCES " + DAY_TABLE + "(" + DAY_ID + ")"
                + ")";
        db.execSQL(sqlPain);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
