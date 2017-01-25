package jdrzaic.math.hr.dbapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RawFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_file);
        InputStream is = this.getResources().openRawResource(R.raw.example);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String str = "";
        try {
            while((str = br.readLine()) != null) {
                Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
            }
            is.close();
            br.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }



}
