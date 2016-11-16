package layouts.jdrzaic.math.hr.examapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class AnotherActivity extends AppCompatActivity {

    String message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        Intent i = getIntent();
        message = i.getStringExtra("message");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, Integer.toString(message.length()), Toast.LENGTH_LONG);
    }

    public void openMaps(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,-122.4194"));
        startActivity(i);
    }

    public void openBrowser(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(i);
    }

    public void callPhone(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+1234567890"));
        startActivity(i);
    }
}
