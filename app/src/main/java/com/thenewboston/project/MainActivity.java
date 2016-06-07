package com.thenewboston.project;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static int selTopic=0;
    private String TAG = "MainActivity";
    ArrayList<TopicItem> items = new ArrayList<TopicItem>();

    ListView list1;
    DBHelper helper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DBHelper(this);

        list1 = (ListView) findViewById(R.id.ourListview);
        //items=getIntent().getSerializableExtra("TOPICS_LIST");
        final AmaderAdapter adpt = new AmaderAdapter();
        list1.setAdapter(adpt);

        populateFromDatabase(adpt);

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView lbl = (TextView) findViewById(R.id.lblSelected);
                //lbl.setText(items.get(position).toString());
                selTopic=items.get(position).getRowId();
                if((RootHome.selMenu).equals("Learn lessons"))
                {
                    Intent act2 = new Intent(view.getContext(),LessonActivity.class);
                    startActivity(act2);
                }
                else if((RootHome.selMenu).equals("Create lessons"))
                {
                    Intent act2 = new Intent(view.getContext(),NewLesson.class);
                    startActivity(act2);
                }

            }
        });
        /*LayoutTransition layoutT  = list1.getLayoutTransition();
        layoutT.enableTransitionType(LayoutTransition.APPEARING);
        layoutT.enableTransitionType(LayoutTransition.DISAPPEARING);
        layoutT.enableTransitionType(LayoutTransition.CHANGE_APPEARING);
        layoutT.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
        layoutT.enableTransitionType(LayoutTransition.CHANGING);*/
    }

    private void populateFromDatabase(AmaderAdapter adpt) {
        items.clear();
        adpt.notifyDataSetChanged();
        items.addAll(helper.retrieveData());
        adpt.notifyDataSetChanged();
        int k = items.size();
        if (k == 0) Log.d(TAG, "nothing");
    }

    public class AmaderAdapter extends ArrayAdapter<TopicItem> {
        public AmaderAdapter() {
            super(getApplicationContext(), R.layout.task_row, items);
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public TopicItem getItem(int position) {
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

            t1.setText(items.get(position).topicName);

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

