package prva.math.hr.prva;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(this,getIntent().getStringExtra("str1"),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,Integer.toString(getIntent().getIntExtra("br1",0)),Toast.LENGTH_SHORT).show();

        Bundle bundle=getIntent().getExtras();
        Toast.makeText(this,bundle.getString("str2"),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,Integer.toString(bundle.getInt("br2")),Toast.LENGTH_SHORT).show();

        Toast.makeText(this,getIntent().getData().toString(),Toast.LENGTH_SHORT).show();
    }

}
