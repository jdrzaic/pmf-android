package jdrzaic.math.hr.dbapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String MY_PREF = "MyPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    protected void savePreferences() {
        int mode = MODE_PRIVATE;
        SharedPreferences preferences = getSharedPreferences(MY_PREF, mode);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("key1", true);
        editor.putFloat("lastFloat",1f);
        editor.putInt("wholeNumber", 5);
        editor.putLong("aNumber", 35);
        editor.putString("textEntryValue", "Neki tekst");
        editor.commit();
    }

    public void loadPreferences() {
        int mode = MODE_PRIVATE;
        SharedPreferences preferences = getSharedPreferences(MY_PREF, mode);
        boolean isTrue = preferences.getBoolean("key1", false);

        Toast.makeText(getBaseContext(), "" + isTrue, Toast.LENGTH_LONG).show();
    }

    public void onClick(View view) {
        savePreferences();
        loadPreferences();
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}
