package math.jdrzaic.bibliographyapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditEntityActivity extends AppCompatActivity {
    public static final String EDIT_TAG = "edit_tag";

    public int entityId;
    EditText typeEditText;
    EditText authorsEditText;
    EditText titleEditText;
    EditText journalEditText;
    EditText numberEditText;
    EditText pagesEditText;
    EditText yearEditText;
    EditText linkEditText;
    EditText fileEditText;
    EditText keywordsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entity);
        Intent intent = getIntent();
        entityId = intent.getIntExtra(EntitiesListAdapter.ENTITY_ID, 1);
        typeEditText = (EditText)findViewById(R.id.edit_type);
        authorsEditText = (EditText)findViewById(R.id.edit_author);
        titleEditText = (EditText)findViewById(R.id.edit_title);
        journalEditText = (EditText)findViewById(R.id.edit_journal);
        numberEditText = (EditText)findViewById(R.id.edit_number);
        pagesEditText = (EditText)findViewById(R.id.edit_pages);
        yearEditText = (EditText)findViewById(R.id.edit_year);
        linkEditText = (EditText)findViewById(R.id.edit_link);
        fileEditText = (EditText)findViewById(R.id.edit_file);
        keywordsEditText = (EditText)findViewById(R.id.edit_keywords);
        MainActivity.db.open();
        Cursor c = MainActivity.db.getEntity(entityId);
        if (c != null) {
            fillFields(c);
        }
        MainActivity.db.close();
    }

    public void saveEntityFromAttributes(View view) {
        String type = typeEditText.getText().toString();
        String authors = authorsEditText.getText().toString().trim();
        String title = titleEditText.getText().toString();
        String journal = journalEditText.getText().toString();
        int number;
        int year;
        try {
            number = Integer.valueOf(numberEditText.getText().toString());
            year = Integer.valueOf(yearEditText.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Journal number and year should be a number",
                    Toast.LENGTH_LONG).show();
            return;
        }
        String pages = pagesEditText.getText().toString();
        String link = linkEditText.getText().toString();
        String file = fileEditText.getText().toString();
        String keywords = keywordsEditText.getText().toString().trim();
        MainActivity.db.open();
        MainActivity.db.updateEntity(entityId, type, authors, title, journal, number, pages, year,
                link, file, keywords);
        MainActivity.db.close();
        finish();
    }

    public void fillFields(Cursor c) {
        typeEditText.setText(c.getString(1), TextView.BufferType.EDITABLE);
        titleEditText.setText(c.getString(2), TextView.BufferType.EDITABLE);
        journalEditText.setText(c.getString(3), TextView.BufferType.EDITABLE);
        if (c.getInt(4) >= 0) numberEditText.setText(String.valueOf(c.getInt(4)), TextView.BufferType.EDITABLE);
        pagesEditText.setText(c.getString(5), TextView.BufferType.EDITABLE);
        if (c.getInt(6) >= 0) yearEditText.setText(String.valueOf(c.getInt(6)), TextView.BufferType.EDITABLE);
        linkEditText.setText(c.getString(7), TextView.BufferType.EDITABLE);
        fileEditText.setText(c.getString(8), TextView.BufferType.EDITABLE);
        c = MainActivity.db.getAuthorsForEntity(entityId);
        String authorsStr = "";
        if (c.moveToFirst()) {
            do {
                authorsStr += c.getString(0);
                authorsStr += ", ";
            } while (c.moveToNext());
            authorsStr = authorsStr.substring(0, authorsStr.length() - 2);
        }
        authorsEditText.setText(authorsStr, TextView.BufferType.EDITABLE);
        c = MainActivity.db.getKeywordsForEntity(entityId);
        String keywordsStr = "";
        if (c.moveToFirst()) {
            do {
                keywordsStr += c.getString(0);
                keywordsStr += ", ";
            } while (c.moveToNext());
            keywordsStr = keywordsStr.substring(0, keywordsStr.length() - 2);
        }
        keywordsEditText.setText(keywordsStr, TextView.BufferType.EDITABLE);
    }
}
