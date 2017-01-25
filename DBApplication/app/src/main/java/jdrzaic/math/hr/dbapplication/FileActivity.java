package jdrzaic.math.hr.dbapplication;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileActivity extends AppCompatActivity {

    TextView view;
    EditText editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        view = (TextView) findViewById(R.id.show_file);
        editor = (EditText) findViewById(R.id.edit_file);

    }

    public void saveContent(View view) {
        String input = editor.getText().toString();
        try {
            FileOutputStream fos = openFileOutput("textfile.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(input);
            osw.flush();
            osw.close();
            Toast.makeText(getBaseContext(), "Text saved", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        editor.setText("");
    }

    public void loadContent(View view) {
        try {
            FileInputStream fis = openFileInput("textfile.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            String loaded = new String();
            char[] inputBuffer = new char[256];
            int charRead;
            while((charRead = isr.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                loaded += readString;
                inputBuffer = new char[256];
            }
            editor.setText(loaded);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

}
