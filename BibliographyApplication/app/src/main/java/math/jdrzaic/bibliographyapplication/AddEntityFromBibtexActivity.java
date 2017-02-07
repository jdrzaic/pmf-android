package math.jdrzaic.bibliographyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddEntityFromBibtexActivity extends AppCompatActivity {

    EditText bibtexEditText;
    int listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entity_from_bibtex);
        bibtexEditText = (EditText)findViewById(R.id.input_bibtex);
        Intent intent = getIntent();
        listId = intent.getIntExtra(EntitiesListActivity.LIST_ID_FROM_ENTITES, 1);
    }

    public void saveEntityFromBibtex(View view) {
        Entity fromBibtex = BibtexParser.parseBibtex(bibtexEditText.getText().toString());
        if (fromBibtex.type.compareTo("") == 0) {
            Toast.makeText(getBaseContext(), "Type not specified or not in valid format", Toast.LENGTH_LONG).show();
            return;
        }
        if (fromBibtex.title.compareTo("") == 0) {
            Toast.makeText(getBaseContext(), "Title not specified or not in valid format", Toast.LENGTH_LONG).show();
            return;
        }
        if (fromBibtex.journal.compareTo("") == 0) {
            Toast.makeText(getBaseContext(), "Journal not specified or not in valid format", Toast.LENGTH_LONG).show();
            return;
        }
        if (fromBibtex.year == -1) {
            Toast.makeText(getBaseContext(), "Year not specified or not in valid format", Toast.LENGTH_LONG).show();
            return;
        }
        if (fromBibtex.authorsString.compareTo("") == 0) {
            Toast.makeText(getBaseContext(), "Authors not specified or not in valid format", Toast.LENGTH_LONG).show();
            return;
        }
        MainActivity.db.open();
        MainActivity.db.insertEntity(fromBibtex.type, fromBibtex.authorsString, fromBibtex.title,
                fromBibtex.journal, fromBibtex.number, fromBibtex.pages, fromBibtex.year,
                fromBibtex.link, fromBibtex.file, fromBibtex.keywordsString, listId);
        MainActivity.db.close();
        super.onBackPressed();
    }
}
