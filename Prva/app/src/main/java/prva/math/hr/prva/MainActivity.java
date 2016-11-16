package prva.math.hr.prva;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "prva.math.hr.prva.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.text);
        String message = (String)editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void onClick2(View view) {
        Intent i = new Intent(this, ThirdActivity.class);
        i.putExtra("str1","Ovo je prvi string");
        i.putExtra("br1",25);

        Bundle extras=new Bundle();
        extras.putString("str2","Ovo je drugi string");
        extras.putInt("br2",27);
        i.putExtras(extras);

        i.setData(Uri.parse("neki tekst treci"));
        startActivity(i);
    }
}
