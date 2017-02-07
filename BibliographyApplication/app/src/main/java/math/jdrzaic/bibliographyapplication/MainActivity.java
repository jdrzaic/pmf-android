package math.jdrzaic.bibliographyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static DBAdapter db;
    public static String LIST_ID = "list_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBAdapter(this);
        Intent i = new Intent(this, BibListActivity.class);
        i.putExtra(LIST_ID, 1);
        startActivity(i);
    }
}
