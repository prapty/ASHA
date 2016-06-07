package com.thenewboston.project;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class  RootHome extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ListView list1 ;
    private ArrayAdapter<String> listAdapter ;
    public static String selMenu = null;
    ArrayList<String> menulist = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_home);

        // Find the ListView resource.
        list1 = (ListView) findViewById( R.id.firstlistView);

        // Create and populate a List of planet names.
        String[] menu = new String[] { "Learn lessons", "Create lessons", "Use flashcards"};

        menulist.addAll( Arrays.asList(menu) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.menu_row, menulist);

        // Set the ArrayAdapter as the ListView's adapter.
        list1.setAdapter( listAdapter );
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selMenu=menulist.get(position);
                if(selMenu.equals("Learn lessons"))
                {
                    Intent act2 = new Intent(view.getContext(),MainActivity.class);
                    startActivity(act2);
                }
                else if(selMenu.equals("Create lessons"))
                {
                    Intent act2 = new Intent(view.getContext(),MainActivity.class);
                    startActivity(act2);
                }

                else if(selMenu.equals("Use flashcards"))
                {
                    Intent act2 = new Intent(view.getContext(),FlashActivity.class);
                    startActivity(act2);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
