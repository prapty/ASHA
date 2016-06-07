package com.thenewboston.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FlashActivity extends AppCompatActivity {
    public static String selCard=null;
    ArrayList<CardItem> items = new ArrayList<CardItem>();

    ListView list1;
    DBHelper helper;
    public String cName= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        helper = new DBHelper(this);

        final TextView txt1 = (TextView) findViewById(R.id.editText);
        ImageButton btnSearch = (ImageButton) findViewById(R.id.imageButton);
        TextView txt2 = (TextView) findViewById(R.id.lblSelected1);
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act2 = new Intent(v.getContext(),NewCard.class);
                startActivity(act2);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cName = txt1.getText().toString();
                selCard=cName;
                Intent act2 = new Intent(v.getContext(),ShowCard.class);
                startActivity(act2);
            }
        });
        list1 = (ListView) findViewById(R.id.cardlistView);

        final AmaderAdapter adpt = new AmaderAdapter();

        populateFromDB(adpt);
        list1.setAdapter(adpt);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selCard=items.get(position).getCardName();
                Intent act2 = new Intent(view.getContext(),ShowCard.class);
                startActivity(act2);
            }
        });
        /*LayoutTransition layoutT  = list1.getLayoutTransition();
        layoutT.enableTransitionType(LayoutTransition.APPEARING);
        layoutT.enableTransitionType(LayoutTransition.DISAPPEARING);
        layoutT.enableTransitionType(LayoutTransition.CHANGE_APPEARING);
        layoutT.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
        layoutT.enableTransitionType(LayoutTransition.CHANGING);*/
    }

    private void populateFromDB(AmaderAdapter adpt) {
        items.clear();
        adpt.notifyDataSetChanged();
        items.addAll(helper.retrieveCard());
        adpt.notifyDataSetChanged();
        //int k = items.size();
        //if (k == 0) Log.d(TAG, "nothing");
    }

    public class AmaderAdapter extends ArrayAdapter<CardItem> {
        public AmaderAdapter() {
            super(getApplicationContext(), R.layout.task_row, items);
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CardItem getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View amaderView = convertView;
            if (amaderView == null)
                amaderView = getLayoutInflater().inflate(R.layout.task_row, parent, false);

            TextView t1 = (TextView) amaderView.findViewById(R.id.txt1);

            //TextView t2 = (TextView) amaderView.findViewById(R.id.txt2);

            t1.setText(items.get(position).cardName);

            //t2.setText(items.get(position).topicId);

            return amaderView;
        }
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
}
