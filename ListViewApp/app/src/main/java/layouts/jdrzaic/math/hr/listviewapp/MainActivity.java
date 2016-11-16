package layouts.jdrzaic.math.hr.listviewapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ListActivity {
    String[] kolegiji;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ListView prviView = getListView();
        prviView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        prviView.setTextFilterEnabled(true);
        kolegiji = getResources().getStringArray(R.array.kolegiji);

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked,kolegiji));
    }

    public void onListItemClick(ListView parent, View v, int position, long id){
        Toast.makeText(this, "izabrali ste" + kolegiji[position], Toast.LENGTH_SHORT).show();
    }
}
