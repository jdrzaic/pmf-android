package jdrzaic.math.hr.dbapplication;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import jdrzaic.math.hr.dbapplication.BooksProvider;

public class BooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
    }

    public void onClickAddTitle(View view) {

        //---add a book---

        ContentValues values = new ContentValues();
        values.put("title", ((EditText)
                findViewById(R.id.txtTitle)).getText().toString());
        values.put("isbn", ((EditText)
                findViewById(R.id.txtISBN)).getText().toString());
        Uri uri = getContentResolver().insert(
                Uri.parse(
                        "content://jdrzaic.math.hr.dbapplication.provider/books"),
                values);
    }

    public void onClickRetrieveTitles(View view) {
        //---retrieve the titles---
        Uri allTitles = Uri.parse(
                "content://jdrzaic.math.hr.dbapplication.provider/books");

        Cursor c;
        if (android.os.Build.VERSION.SDK_INT <11) {
            //---before Honeycomb---
            c = managedQuery(allTitles, null, null, null,
                    "title desc");
        } else {
            //---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    allTitles, null, null, null,
                    "title desc");
            c = cursorLoader.loadInBackground();
        }

        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(
                                BooksProvider._ID)) + ", " +
                                c.getString(c.getColumnIndex(
                                        BooksProvider.TITLE)) + ", " +
                                c.getString(c.getColumnIndex(
                                        BooksProvider.ISBN)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }
}
