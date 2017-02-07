package math.jdrzaic.bibliographyapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EntitiesListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<Entity> entitiesList;
    DBAdapter db;
    RecyclerView rvEntitiesView;
    int listId;
    String listName;
    EntitiesListAdapter adapter;
    ImageButton searchImageButton;
    EditText searchEditText;
    Spinner searchSpinner;

    public static final String LIST_ID_FROM_ENTITES = "list_id_from_entities";
    public static final String ENTITY_ID = "entity_show_id";
    public static final String TAG = "entities_tag";
    String[] items = new String[]{"title", "author", "journal", "year"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        listId = intent.getIntExtra(BibListActivity.LIST_ID, 1);
        listName = intent.getStringExtra(BibListActivity.LIST_NAME);
        setContentView(R.layout.activity_entities_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvEntitiesView = (RecyclerView) findViewById(R.id.entities_list);
        searchImageButton = (ImageButton) findViewById(R.id.button_search);
        searchEditText = (EditText) findViewById(R.id.search_edit);
        searchSpinner = (Spinner) findViewById(R.id.search_spinner);
        entitiesList = new ArrayList<>();
        db = new DBAdapter(this);
        loadEntities();

        adapter = new EntitiesListAdapter(this, entitiesList);
        adapter.setOnItemClickListener(new EntitiesListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(EntitiesListActivity.this, ShowEntityActivity.class);
                intent.putExtra(ENTITY_ID, entitiesList.get(position).id);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d(TAG, "onItemLongClick pos = " + position);
            }
        });
        rvEntitiesView.setAdapter(adapter);
        rvEntitiesView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_entities);
        FloatingActionButton fabBib = (FloatingActionButton) findViewById(R.id.fab_entities_bibtex);
        FloatingActionButton fabFile = (FloatingActionButton) findViewById(R.id.fab_entities_file);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EntitiesListActivity.this, AddEntityFromData.class);
                intent.putExtra(LIST_ID_FROM_ENTITES, listId);
                startActivityForResult(intent , 0);
            }
        });

        fabBib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EntitiesListActivity.this, AddEntityFromBibtexActivity.class);
                intent.putExtra(LIST_ID_FROM_ENTITES, listId);
                startActivityForResult(intent , 0);
            }
        });

        fabFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        setTitle(listName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(adapter);
        searchSpinner.setOnItemSelectedListener(this);
    }



    public Entity createEntityObj(Cursor c) {
        ArrayList<String> aKeywords = new ArrayList<>();
        ArrayList<String> aAuthors = new ArrayList<>();
        db.open();
        Cursor cutil = db.getKeywordsForEntity(c.getInt(0));
        if (cutil.moveToFirst()) {
            do {
                aKeywords.add(cutil.getString(0));
            } while (cutil.moveToNext());
        }
        Cursor cutil2 = db.getAuthorsForEntity(c.getInt(0));
        if (cutil2.moveToFirst()) {
            do {
                aAuthors.add(cutil2.getString(0));
            } while (cutil2.moveToNext());
        }
        db.close();
        return new Entity(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),
                c.getInt(4), c.getString(5), c.getInt(6), c.getString(7), c.getString(8), aKeywords, aAuthors);
    }

    public void deleteEntity(int id) {
        db.open();
        db.deleteEntity(id);
        db.close();
    }
    public List<Entity> loadEntities() {
        entitiesList = new ArrayList<>();
        db.open();
        Cursor c = db.getEntitiesForList(listId);
        if (c.moveToFirst()) {
            do {
                entitiesList.add(createEntityObj(c));
            } while (c.moveToNext());
        }
        db.close();
        return entitiesList;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, BibListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, Integer.toString(listId));
        loadEntities();
        adapter.mEntites = loadEntities();
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
    }

    private static final int FILE_SELECT_CODE = 2;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    // Get the path
                    String path = "";
                    try {
                        path = FileUtils.getPath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "File Path: " + path);
                    try {
                        FileInputStream fIn = new FileInputStream(new File(path));
                        InputStreamReader isr = new
                                InputStreamReader(fIn);


                        char[] inputBuffer = new char[512];
                        String s = "";

                        int charRead;
                        while ((charRead = isr.read(inputBuffer))>0)
                        {
                            //---convert the chars to a String---
                            String readString =
                                    String.copyValueOf(inputBuffer, 0,
                                            charRead);
                            s += readString;

                            inputBuffer = new char[512];
                        }
                        Log.d(TAG, s);
                        boolean err = false;
                        Entity fromBibtex = BibtexParser.parseBibtex(s);
                        if (fromBibtex.type.compareTo("") == 0) {
                            err = true;
                        }
                        if (fromBibtex.title.compareTo("") == 0) {
                            err = true;
                        }
                        if (fromBibtex.journal.compareTo("") == 0) {
                            err = true;
                        }
                        if (fromBibtex.year == -1) {
                            err = true;
                        }
                        if (fromBibtex.authorsString.compareTo("") == 0) {
                            err = true;
                        }
                        if (err) {
                            Toast.makeText(getBaseContext(), "Unable to parse file content", Toast.LENGTH_LONG).show();
                            return;
                        }
                        MainActivity.db.open();
                        MainActivity.db.insertEntity(fromBibtex.type, fromBibtex.authorsString, fromBibtex.title,
                                fromBibtex.journal, fromBibtex.number, fromBibtex.pages, fromBibtex.year,
                                fromBibtex.link, fromBibtex.file, fromBibtex.keywordsString, listId);
                        MainActivity.db.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void searchEntities(View view) {
        String query = searchEditText.getText().toString();
        if (!query.contains(":")) {
            adapter.mEntites = loadEntities();
            entitiesList = adapter.mEntites;
            adapter.notifyItemRangeChanged(0, adapter.getItemCount());
            if (query.length() > 0) {
                Toast.makeText(getBaseContext(), "Invalid query", Toast.LENGTH_LONG).show();
            }
            return;
        }
        String[] parts = query.split(":", 2);
        String key = parts[0].trim();
        String value = parts[1].trim();
        adapter.mEntites = loadEntities();
        entitiesList = adapter.mEntites;
        adapter.mEntites = BibtexParser.filter(key, value, adapter.mEntites);
        entitiesList = adapter.mEntites;
        Log.d(TAG, String.valueOf(entitiesList.size()));
        adapter.notifyDataSetChanged();

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                Collections.sort(entitiesList, new Comparator<Entity>() {
                    @Override
                    public int compare(Entity lhs, Entity rhs) {
                        return lhs.title.compareTo(rhs.title);
                    }
                });
                adapter.mEntites = entitiesList;
                break;
            case 1:
                Collections.sort(entitiesList, new Comparator<Entity>() {
                    @Override
                    public int compare(Entity lhs, Entity rhs) {
                        return lhs.getAuthors().compareTo(rhs.getAuthors());
                    }
                });
                adapter.mEntites = entitiesList;
                break;
            case 2:
                Collections.sort(entitiesList, new Comparator<Entity>() {
                    @Override
                    public int compare(Entity lhs, Entity rhs) {
                        return lhs.journal.compareTo(rhs.journal);
                    }
                });
                adapter.mEntites = entitiesList;
                break;
            case 3:
                Collections.sort(entitiesList, new Comparator<Entity>() {
                    @Override
                    public int compare(Entity lhs, Entity rhs) {
                        if(lhs.year < rhs.year) return -1;
                        return rhs.year < lhs.year ? 1 : 0;
                    }
                });
                adapter.mEntites = entitiesList;
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }
}
