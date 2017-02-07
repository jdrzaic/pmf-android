package math.jdrzaic.bibliographyapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;


public class DBAdapter {

    static final String TAG = "DBAdapterTag";
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";

    static final String KEY_TYPE = "type";
    static final String KEY_TITLE = "title";
    static final String KEY_JOURNAL = "journal";
    static final String KEY_NUMBER = "number";
    static final String KEY_PAGES = "pages";
    static final String KEY_YEAR = "year";
    static final String KEY_LINK = "link";
    static final String KEY_FILE = "file";
    static final String KEY_LIST_ID = "list_id";
    static final String KEY_ENTITY_ID = "entity_id";

    static final String DATABASE_NAME = "BibDB";
    static final String DATABASE_TABLE_LIST = "list";
    static final String DATABASE_TABLE_ENTITY = "entity";
    static final String DATABASE_TABLE_KEYWORD = "keyword";
    static final String DATABASE_TABLE_AUTHOR = "author";
    static int DATABASE_VERSION = 5;

    static final String CREATE_TABLE_LIST =
            "create table list (_id integer primary key autoincrement, "
                    + "name text not null);";
    static final String CREATE_TABLE_ENTITY = "create table entity (_id integer primary key autoincrement, "
            + "type text not null, title text not null, journal text not null," +
            " number integer, pages text, year integer not null, link text, file text," +
            " list_id integer not null);";

    static final String CREATE_TABLE_KEYWORD = "create table keyword (_id integer primary key autoincrement, " +
            "name text not null, entity_id integer not null)";

    static final String CREATE_TABLE_AUTHOR = "create table author (_id integer primary key autoincrement, " +
            "name text not null, entity_id integer not null)";

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
                db.execSQL(CREATE_TABLE_LIST);
                db.execSQL(CREATE_TABLE_ENTITY);
                db.execSQL(CREATE_TABLE_KEYWORD);
                db.execSQL(CREATE_TABLE_AUTHOR);
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS list");
            db.execSQL("DROP TABLE IF EXISTS entity");
            db.execSQL("DROP TABLE IF EXISTS keyword");
            db.execSQL("DROP TABLE IF EXISTS author");
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public long insertList(String name)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        return db.insert(DATABASE_TABLE_LIST, null, initialValues);

    }

    public long insertEntity(String type, String authors, String title, String journal, int number,
                             String pages, int year, String link, String file, String keywords, int listId ) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("type", type);
        initialValues.put("title", title);
        initialValues.put("journal", journal);
        initialValues.put("number", number);
        initialValues.put("pages", pages);
        initialValues.put("year", year);
        initialValues.put("link", link);
        initialValues.put("file", file);
        initialValues.put("list_id", listId);

        long id = db.insert(DATABASE_TABLE_ENTITY, null, initialValues);
        String[] keywordArray = keywords.split(",");
        for(int i = 0; i < keywordArray.length; ++i) {
            String keyword = keywordArray[i].trim();
            if (keyword.compareTo("") == 0) {
                continue;
            }
            initialValues = new ContentValues();
            initialValues.put("name", keyword);
            initialValues.put("entity_id", id);
            db.insert(DATABASE_TABLE_KEYWORD, null, initialValues);
        }
        String[] authorsArray = authors.split(",");
        for(int i = 0; i < authorsArray.length; ++i) {
            String author = authorsArray[i].trim();
            if (author.compareTo("") == 0) {
                continue;
            }
            ContentValues initialValues1 = new ContentValues();
            initialValues1.put("name", author);
            initialValues1.put("entity_id", id);
            db.insert(DATABASE_TABLE_AUTHOR, null, initialValues1);
        }
        Cursor cu = getAuthorsForEntity(id);
        return id;
    }

    public boolean deleteList(long listId) {
        db.delete(DATABASE_TABLE_ENTITY, KEY_LIST_ID + " = " + listId, null);
        return db.delete(DATABASE_TABLE_LIST, KEY_ROWID + " = " + listId, null) > 0;
    }

    public boolean deleteEntity(long entityId) {
        return db.delete(DATABASE_TABLE_ENTITY, KEY_ROWID + " = " + entityId, null) > 0;

    }

    public Cursor getAllLists() {
        return db.query(DATABASE_TABLE_LIST, new String[] {KEY_ROWID, KEY_NAME}, null, null, null, null, null);
    }

    public Cursor getAllEntities() {
        return db.query(DATABASE_TABLE_ENTITY,
                new String[] {KEY_ROWID, KEY_TYPE, KEY_TITLE, KEY_JOURNAL, KEY_NUMBER,
                        KEY_PAGES, KEY_YEAR, KEY_LINK, KEY_FILE, KEY_LIST_ID},
                null, null, null, null, null);
    }

    public Cursor getEntitiesForList(int listId) {
        return db.query(true, DATABASE_TABLE_ENTITY,
                new String[] {KEY_ROWID, KEY_TYPE, KEY_TITLE, KEY_JOURNAL, KEY_NUMBER,
                        KEY_PAGES, KEY_YEAR, KEY_LINK, KEY_FILE},
                KEY_LIST_ID + " = " + listId, null, null, null, null, null);
    }

    public Cursor getList(long listId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_LIST,
                        new String[] {KEY_ROWID, KEY_NAME}, KEY_ROWID + " = " + listId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public Cursor getEntity(long entityId) {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_ENTITY,
                        new String[] {KEY_ROWID, KEY_TYPE, KEY_TITLE, KEY_JOURNAL, KEY_NUMBER,
                                KEY_PAGES, KEY_YEAR, KEY_LINK, KEY_FILE},
                        KEY_ROWID + " = " + entityId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateList(long rowId, String name)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        return db.update(DATABASE_TABLE_LIST, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean updateEntity(long rowId, String type, String authors, String title, String journal, int number,
                                String pages, int year, String link, String file, String keywords)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("type", type);
        initialValues.put("title", title);
        initialValues.put("journal", journal);
        initialValues.put("number", number);
        initialValues.put("pages", pages);
        initialValues.put("year", year);
        initialValues.put("link", link);
        initialValues.put("file", file);
        boolean updated = db.update(DATABASE_TABLE_ENTITY, initialValues, KEY_ROWID + "=" + rowId, null) > 0;
        close();
        open();
        db.delete(DATABASE_TABLE_KEYWORD, KEY_ENTITY_ID + " = " + rowId, null);
        close();
        open();
        db.delete(DATABASE_TABLE_AUTHOR, KEY_ENTITY_ID + " = " + rowId, null);
        close();
        String[] keywordArray = keywords.split(",");
        for(int i = 0; i < keywordArray.length; ++i) {
            String keyword = keywordArray[i].trim();
            if (keyword.compareTo("") == 0) {
                continue;
            }
            initialValues = new ContentValues();
            initialValues.put("name", keyword);
            initialValues.put("entity_id", rowId);
            open();
            db.insert(DATABASE_TABLE_KEYWORD, null, initialValues);
            close();
        }
        String[] authorsArray = authors.split(",");
        for(int i = 0; i < authorsArray.length; ++i) {
            String author = authorsArray[i].trim();
            if (author.compareTo("") == 0) {
                continue;
            }
            initialValues = new ContentValues();
            initialValues.put("name", author);
            initialValues.put("entity_id", rowId);
            open();
            db.insert(DATABASE_TABLE_AUTHOR, null, initialValues);
        }
        return updated;
    }

    public Cursor getKeywordsForEntity(long entityId) {
        return db.query(true, DATABASE_TABLE_KEYWORD,
                        new String[] {KEY_NAME},
                        KEY_ENTITY_ID + " = " + entityId, null, null, null, null, null);
    }

    public Cursor getAuthorsForEntity(long entityId) {
        return db.query(true, DATABASE_TABLE_AUTHOR,
                new String[] {KEY_NAME},
                KEY_ENTITY_ID + " = " + entityId, null, null, null, null, null);
    }

    public Cursor getEntitiesIdsForKeyword(String keyword) {
        return db.query(true, DATABASE_TABLE_KEYWORD,
                new String[] {KEY_ENTITY_ID}, KEY_NAME + " = " + keyword,
                null, null, null, null, null);
    }

    public Cursor getEntitiesIdsForAuthor(String author) {
        return db.query(true, DATABASE_TABLE_KEYWORD,
                new String[] {KEY_ENTITY_ID}, KEY_NAME + " = " + author,
                null, null, null, null, null);
    }
}