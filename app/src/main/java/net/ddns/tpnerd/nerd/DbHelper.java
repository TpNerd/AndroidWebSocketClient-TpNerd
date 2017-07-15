package net.ddns.tpnerd.nerd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Toshi on 14/07/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="nerd.db";
    public static final int DB_VERSION=1;

    public static final String USER_TABLE="users";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_EMAIL="email";
    public static final String COLUMN_PASSWORD="password";
    public static final String COLUMN_USERNAME ="username";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE "+ USER_TABLE + "("
            + COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_EMAIL + " TEXT,"+
            COLUMN_USERNAME + " TEXT,"+
              COLUMN_PASSWORD+" TEXT);";
    private static final String TAG = "mydbhelper";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
        onCreate(db);
    }

    public boolean isTaken(String nam)
    {
        String selectQuery1="select * from "+USER_TABLE+ " where "+COLUMN_USERNAME+" = '"+nam+"';";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            return true;
        }
        cursor.close();
        db.close();

        return false;

    }

    public void addUser(String username, String email, String pass)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL,email);
        cv.put(COLUMN_PASSWORD,pass);
        cv.put(COLUMN_USERNAME,username);

        long id=db.insert(USER_TABLE,null,cv);
        Log.d(TAG,"user_inserted "+id);

    }

    public boolean getUser(String user, String pass)
    {
        String selectQuery1="select * from "+USER_TABLE+ " where "+COLUMN_USERNAME+" = '"+user+"' AND "+
                COLUMN_PASSWORD+" = '"+pass+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            return true;
        }
        cursor.close();
        db.close();

        return false;
    }


public boolean getUserByEmail(String email, String pass)
        {
        String selectQuery1="select * from "+USER_TABLE+ " where "+COLUMN_EMAIL+" = '"+email+"' AND "+
        COLUMN_PASSWORD+" = '"+pass+"'";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
        return true;
        }
        cursor.close();
        db.close();

        return false;
        }

        }
