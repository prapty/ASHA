package com.thenewboston.project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Environment;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewLesson extends AppCompatActivity {
    DBHelper helper;
    ArrayList<LessonItem> items = new ArrayList<LessonItem>();
    public static String newLess = "";
    ListView list1;
    public EditText txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lesson);
        helper=new DBHelper(this);
        Button btnCreate = (Button) findViewById(R.id.button3);
        list1 = (ListView) findViewById(R.id.lessonlistView);
        final AmaderAdapter adpt = new AmaderAdapter();
        list1.setAdapter(adpt);
        txt1 = (EditText) findViewById(R.id.editText2);

        populateFromDatabase(adpt);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newLess+=txt1.getText();
                newLess+=MainActivity.selTopic;
                int flag=1;
                for (String s:DBHelper.tables)
                {
                    if (s.equals(newLess))
                    {
                        TextView label = (TextView) findViewById(R.id.textView7);
                        label.setText("Already exists");
                        flag=0;
                        Intent act2 = new Intent(v.getContext(),NewLessonPath.class);
                        startActivity(act2);
                    }
                    else
                    {

                    }
                }
                if(flag==1) {
                    helper.createLessonTable(newLess);
                    LessonItem newItem = new LessonItem(MainActivity.selTopic,txt1.getText().toString(), null,0);
                    helper.insertLesson(newItem);
                    Intent act2 = new Intent(v.getContext(),NewLessonPath.class);
                    startActivity(act2);
                }
            }
        });
    }

    public void populateFromDatabase(AmaderAdapter adpt) {
        items.clear();
        adpt.notifyDataSetChanged();
        items.addAll(helper.getLessonName(MainActivity.selTopic));
        adpt.notifyDataSetChanged();
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
