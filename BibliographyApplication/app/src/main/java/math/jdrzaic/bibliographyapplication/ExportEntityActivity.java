package math.jdrzaic.bibliographyapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ExportEntityActivity extends AppCompatActivity {

    private int entityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_entity);
        Intent intent = getIntent();
        entityId = intent.getIntExtra(EntitiesListAdapter.ENTITY_ID, 1);
        EditText exportEditText = (EditText)findViewById(R.id.edit_export);
        MainActivity.db.open();
        Cursor c = MainActivity.db.getEntity(entityId);
        try {
            exportEditText.setText(BibtexParser.exportToBibtex(c), TextView.BufferType.EDITABLE);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
