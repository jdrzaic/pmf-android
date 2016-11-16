package layouts.jdrzaic.math.hr.examapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class SharedPrefActivity extends AppCompatActivity {

    private static String MYPREFS = "myprefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);
        savePreferences();
    }

    public void onButtonClick(View view) {
        loadPreferences();
    }

    protected void savePreferences(){
        //stvorimo shared preference
        int mode=MODE_PRIVATE;
        SharedPreferences mySharedPreferences=getSharedPreferences(MYPREFS,mode);

        //editor za modificiranje shared preference
        SharedPreferences.Editor editor=mySharedPreferences.edit();

        //spremamo vrijednosti u shared preference
        editor.putBoolean("isTrue", true);
        editor.putFloat("lastFloat",1f);
        editor.putInt("wholeNumber", 5);
        editor.putLong("aNumber", 35);
        editor.putString("textEntryValue", "Neki tekst");

        //commit promjene
        editor.commit();
    }

    public void loadPreferences(){
        // dohvatimo preference
        int mode=MODE_PRIVATE;
        SharedPreferences mySharedPreferences=getSharedPreferences(MYPREFS,mode);

        //dohvatimo vrijednosti
        boolean isTrue=mySharedPreferences.getBoolean("isTrue", false);
        float lastFloat=mySharedPreferences.getFloat("lastFloat", 0f);
        int wholeNumber=mySharedPreferences.getInt("WholeNumber", 7);
        long aNumber=mySharedPreferences.getLong("aNumber", 0);
        String stringPreference=mySharedPreferences.getString("textEntryValue", "nista");

        //ispisemo string da se nesto ispise...
        Toast.makeText(getBaseContext(),
                stringPreference,
                Toast.LENGTH_SHORT).show();

    }
}
