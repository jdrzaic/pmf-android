package math.jdrzaic.bibliographyapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class BibListActivity extends AppCompatActivity {

    ArrayList<BibList> bibLists;
    DBAdapter db;
    RecyclerView rvBibLists;
    BibListAdapter adapter;

    public static final String LIST_ID = "list_id";
    public static final String LIST_NAME = "list_name";
    public static final String TAG = "biblist_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bib_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvBibLists = (RecyclerView) findViewById(R.id.biblist_list);
        bibLists = new ArrayList<>();
        db = MainActivity.db;
        loadLists();

        adapter = new BibListAdapter(this, bibLists);
        adapter.setOnItemClickListener(new BibListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(BibListActivity.this, EntitiesListActivity.class);
                intent.putExtra(LIST_ID, bibLists.get(position).id);
                intent.putExtra(LIST_NAME, bibLists.get(position).name);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                v.setBackgroundColor(Color.MAGENTA);
            }
        });
        rvBibLists.setAdapter(adapter);
        rvBibLists.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BibListActivity.this, AddListActivity.class);
                startActivity(intent);
            }
        });
    }

    public BibList createBibListObj(Cursor c) {
        BibList aList = new BibList(c.getInt(0), c.getString(1));
        return aList;
    }

    public void deleteList(int id) {
        db.open();
        boolean i = db.deleteList(id);
        db.close();
    }
    public ArrayList<BibList> loadLists() {
        bibLists = new ArrayList<>();
        db.open();
        Cursor c = db.getAllLists();
        if (c.moveToFirst()) {
            do {
                bibLists.add(createBibListObj(c));
            } while (c.moveToNext());
        }
        db.close();
        return bibLists;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLists();
        adapter.notifyDataSetChanged();
    }
}
