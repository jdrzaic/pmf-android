package math.jdrzaic.bibliographyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddListActivity extends AppCompatActivity {

    EditText listNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listNameEditText = (EditText) findViewById(R.id.input_list_name);
                String name = listNameEditText.getText().toString();
                MainActivity.db.open();
                MainActivity.db.insertList(name);
                MainActivity.db.close();
                Intent intent = new Intent(AddListActivity.this, BibListActivity.class);
                startActivity(intent);
            }
        });
    }

}
