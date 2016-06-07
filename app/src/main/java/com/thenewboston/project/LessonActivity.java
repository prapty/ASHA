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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity {

    private String TAG = "LessonActivity";
    ArrayList<LessonItem> items = new ArrayList<LessonItem>();
    public static String selLesson=null;

    ListView list1;
    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        helper = new DBHelper(this);
        Button btnStart = (Button) findViewById(R.id.button);

        list1 = (ListView) findViewById(R.id.lessonlistView);
        final AmaderAdapter adpt = new AmaderAdapter();
        list1.setAdapter(adpt);

        populateFromDatabase(adpt);

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selLesson=items.get(position).getLessonName();

            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act2 = new Intent(v.getContext(),ShowLesson.class);
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

    public void populateFromDatabase(AmaderAdapter adpt) {
        items.clear();
        adpt.notifyDataSetChanged();
        items.addAll(helper.getLessonName(MainActivity.selTopic));
        adpt.notifyDataSetChanged();
        int k = items.size();
        if (k == 0) Log.d(TAG, "nothing");
    }

    public class AmaderAdapter extends ArrayAdapter<LessonItem> {

        public AmaderAdapter() {
            super(getApplicationContext(), R.layout.task_row, items);
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public LessonItem getItem(int position) {
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
            Log.d(TAG,"");
            //lesson=helper.getLessonName(MainActivity.selTopic);
            TextView t1 = (TextView) amaderView.findViewById(R.id.txt1);

            TextView t2 = (TextView) amaderView.findViewById(R.id.txt2);
            t1.setText(items.get(position).lessonName);
            //t1.setText(lesson.get(position));

            t2.setText(items.get(position).getCount()+ " views");

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
