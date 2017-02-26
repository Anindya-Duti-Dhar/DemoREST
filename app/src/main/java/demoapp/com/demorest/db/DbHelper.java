package demoapp.com.demorest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private String TAG = DbHelper.class.getSimpleName();

    private static final String DB_NAME = "demorest";

    public static final String DB_TABLE_NAME = "users";
    public static final String DB_COLUMN_ID = "iD";
    public static final String DB_COLUMN_USER_FULL_NAME = "userFullName";
    public static final String DB_COLUMN_USER_LOGIN = "userLogin";
    public static final String DB_COLUMN_USER_ID = "userID";
    public static final String DB_COLUMN_USER_PASSWORD = "userPass";
    public static final String DB_COLUMN_USER_TAG = "userTag";
    public static final int DATABASE_VERSION = 1;public static final String TABLE_STATEMENT2 = "create table "+DB_TABLE_NAME +"(iD INTEGER PRIMARY KEY AUTOINCREMENT,userID INTEGER,userLogin TEXT,userPass TEXT,userFullName TEXT,userTag TEXT)";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "--- onCreate database ---");
        // statement for User table
        db.execSQL(TABLE_STATEMENT2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
