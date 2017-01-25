package jdrzaic.math.hr.kolokvijapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;

/**
 * Created by jelenadrzaic on 24/01/2017.
 */
public class DBAdapter {

    static final String TAG = "DBAdapterTag";
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "naziv";
    static final String KEY_TYPE = "vrsta";
    static final String KEY_MAIN_ING = "glavni_sastojak";
    static final String KEY_COOKIE_ID = "id_kolaca";
    static final String KEY_PRICE = "price";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE_COOKIES = "kolaci";
    static final String DATABASE_TABLE_PRICES = "cijene";
    static int DATABASE_VERSION = 1;

    static final String CREATE_DATABASE_COOKIES =
            "create table kolaci (_id integer primary key autoincrement, "
                    + "naziv text not null, vrsta text not null, glavni_sastojak text not null);";
    static final String CREATE_DATABASE_PRICES = "create table cijene (_id integer primary key autoincrement, "
            + "id_kolaca integer not null, price integer not null);";

    final Context context;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_DATABASE_COOKIES);
                db.execSQL(CREATE_DATABASE_PRICES);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS kolaci");
            db.execSQL("DROP TABLE IF EXISTS cijene");
            onCreate(db);
        }
    }


    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        dbHelper.close();
    }

    //---insert a contact into the database---
    public long insertCookie(String naziv, String vrsta, String glavniSastojak)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, naziv);
        initialValues.put(KEY_TYPE, vrsta);
        initialValues.put(KEY_MAIN_ING, glavniSastojak);
        return db.insert(DATABASE_TABLE_COOKIES, null, initialValues);

    }

    public long insertPrice(int cijena, int idKolaca) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PRICE, cijena);
        initialValues.put(KEY_COOKIE_ID, idKolaca);
        return db.insert(DATABASE_TABLE_PRICES, null, initialValues);
    }

    public Cursor getAllCookies()
    {
        return db.query(DATABASE_TABLE_COOKIES, new String[] {KEY_ROWID, KEY_NAME,
                KEY_TYPE, KEY_MAIN_ING}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getCookiesWithAlmond() throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_COOKIES, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_TYPE, KEY_MAIN_ING}, KEY_MAIN_ING + " ='badem'", null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getAllPrices()
    {
        return db.query(DATABASE_TABLE_PRICES, new String[] {KEY_ROWID, KEY_PRICE,
                KEY_COOKIE_ID}, null, null, null, null, null);
    }
}
