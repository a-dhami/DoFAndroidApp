package com.akash.lens;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.akash.lens.model.LensManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LensManager manager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Singleton Support
        manager = LensManager.getInstance();

        // Populate List View Call
        populateListView();

        //Click
        registerClickCallback();



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = addLens.makeLaunchIntent(MainActivity.this);
            startActivity(i);
        });
    }

    protected void onResume(){          //ensures that the lens populated list is refreshed whenever returning to the Main Application
        super.onResume();
        populateListView();
    }

    private void populateListView() {
        //Creation of the list of items required for the Lense List
        ArrayList<String> lensString = new ArrayList<>();
        for (Object l : manager) {
            lensString.add(l.toString());
        }

        //Build Adapter - linking the list generated above to the textView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.da_lens,
                lensString);

        //ListView Configure

        ListView list = findViewById(R.id.listViewMain);
        TextView emptyText = findViewById(R.id.textView_EmptyList);
        list.setEmptyView(emptyText);
        list.setAdapter(adapter);

    }

    private void registerClickCallback() {
        ListView list = findViewById(R.id.listViewMain);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = calcLens.makeLaunchIntent(MainActivity.this, position);
            MainActivity.this.startActivity(i);

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
