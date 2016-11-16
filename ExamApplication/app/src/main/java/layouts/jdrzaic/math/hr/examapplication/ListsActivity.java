package layouts.jdrzaic.math.hr.examapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListsActivity extends ListActivity {

    String[] kolegiji;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ListView prviView = getListView();
        prviView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        prviView.setTextFilterEnabled(true);
        kolegiji = new String[]{"one", "two", "three", "four"};

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked,kolegiji));
    }

    public void onListItemClick(ListView parent, View v, int position, long id){
        Toast.makeText(this, "izabrali ste" + kolegiji[position], Toast.LENGTH_SHORT).show();
    }


}
