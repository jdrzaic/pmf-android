package layouts.jdrzaic.math.hr.examapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openAnotherActivity(View view) {
        Intent i = new Intent(this, AnotherActivity.class);
        EditText editText = (EditText)findViewById(R.id.edit_text);
        String message = editText.getText().toString();
        i.putExtra("message", message);
        startActivity(i);
    }

    public void openFragmentsActivity(View view) {
        Intent i = new Intent(this, FragmentsActivity.class);
        startActivity(i);
    }

    public void openListActivity(View view) {
        Intent i  = new Intent(this, ListsActivity.class);
        startActivity(i);
    }

    public void openImagesActivity(View view) {
        Intent i  = new Intent(this, ImagesActivity.class);
        startActivity(i);
    }

    public void openSharedPrefActivity(View view) {
        Intent i  = new Intent(this, SharedPrefActivity.class);
        startActivity(i);
    }

    public void openFileActivity(View view) {
        Intent i  = new Intent(this, FileActivity.class);
        startActivity(i);
    }
}
