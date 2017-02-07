package math.jdrzaic.bibliographyapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowEntityActivity extends AppCompatActivity {
    public static final String SHOW_TAG = "edit_tag";

    public int entityId;
    TextView typeEditText;
    TextView authorsEditText;
    TextView titleEditText;
    TextView journalEditText;
    TextView numberEditText;
    TextView pagesEditText;
    TextView yearEditText;
    TextView linkEditText;
    TextView fileEditText;
    TextView keywordsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_entity);
        Intent intent = getIntent();
        entityId = intent.getIntExtra(EntitiesListActivity.ENTITY_ID, 1);
        typeEditText = (TextView) findViewById(R.id.show_type);
        authorsEditText = (TextView)findViewById(R.id.show_author);
        titleEditText = (TextView)findViewById(R.id.show_title);
        journalEditText = (TextView)findViewById(R.id.show_journal);
        numberEditText = (TextView)findViewById(R.id.show_number);
        pagesEditText = (TextView)findViewById(R.id.show_pages);
        yearEditText = (TextView)findViewById(R.id.show_year);
        linkEditText = (TextView)findViewById(R.id.show_link);
        linkEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linkEditText.getText().toString();
                if(!url.startsWith("www.") && !url.startsWith("http://") && !url.startsWith("https://")){
                    url = "www."+url;
                }
                if(!url.startsWith("http://") && !url.startsWith("https://")){
                    url = "http://"+url;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        fileEditText = (TextView)findViewById(R.id.show_file);
        keywordsEditText = (TextView)findViewById(R.id.show_keywords);
        MainActivity.db.open();
        Cursor c = MainActivity.db.getEntity(entityId);
        if (c != null) {
            fillFields(c);
        }
        MainActivity.db.close();
    }

    public void fillFields(Cursor c) {
        typeEditText.setText(c.getString(1));
        titleEditText.setText(c.getString(2));
        journalEditText.setText(c.getString(3));
        if (c.getInt(4) >= 0) numberEditText.setText(String.valueOf(c.getInt(4)));
        pagesEditText.setText(c.getString(5));
        if (c.getInt(6) >= 0) yearEditText.setText(String.valueOf(c.getInt(6)));
        linkEditText.setText(c.getString(7));
        fileEditText.setText(c.getString(8));
        c = MainActivity.db.getAuthorsForEntity(entityId);
        String authorsStr = "";
        if (c.moveToFirst()) {
            do {
                authorsStr += c.getString(0);
                authorsStr += ", ";
            } while (c.moveToNext());
            authorsStr = authorsStr.substring(0, authorsStr.length() - 2);
        }
        authorsEditText.setText(authorsStr);
        c = MainActivity.db.getKeywordsForEntity(entityId);
        String keywordsStr = "";
        if (c.moveToFirst()) {
            do {
                keywordsStr += c.getString(0);
                keywordsStr += ", ";
            } while (c.moveToNext());
            keywordsStr = keywordsStr.substring(0, keywordsStr.length() - 2);
        }
        keywordsEditText.setText(keywordsStr);
    }
}
