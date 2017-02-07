package math.jdrzaic.bibliographyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddEntityFromData extends AppCompatActivity {

    private int listId;
    EditText typeEditText;
    EditText authorsEditText;
    EditText titleEditText;
    EditText journalEditText;
    EditText numberEditText;
    EditText pagesEditText;
    EditText yearEditText;
    EditText linkEditText;
    EditText keywordsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entity_from_data);
        Intent intent = getIntent();
        listId = intent.getIntExtra(EntitiesListActivity.LIST_ID_FROM_ENTITES, 1);
        typeEditText = (EditText)findViewById(R.id.input_type);
        authorsEditText = (EditText)findViewById(R.id.input_author);
        titleEditText = (EditText)findViewById(R.id.input_title);
        journalEditText = (EditText)findViewById(R.id.input_journal);
        numberEditText = (EditText)findViewById(R.id.input_number);
        pagesEditText = (EditText)findViewById(R.id.input_pages);
        yearEditText = (EditText)findViewById(R.id.input_year);
        linkEditText = (EditText)findViewById(R.id.input_link);
        keywordsEditText = (EditText)findViewById(R.id.input_keywords);
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
        String keywords = keywordsEditText.getText().toString().trim();
        MainActivity.db.open();
        MainActivity.db.insertEntity(type, authors, title, journal, number, pages, year, link, "",
                keywords, listId);
        MainActivity.db.close();
        finish();
    }
}
