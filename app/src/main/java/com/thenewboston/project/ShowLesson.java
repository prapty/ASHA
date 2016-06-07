package com.thenewboston.project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowLesson extends AppCompatActivity {
    DBHelper helper;
    public int rowCount=1;
    public static String tab = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lesson);
        helper=new DBHelper(this);

        final Button ex=(Button)findViewById(R.id.button7);
        final Button next =(Button) findViewById(R.id.button8);
        Button prev =(Button) findViewById(R.id.button5);
        final ImageView imview = (ImageView) findViewById(R.id.mImageView);
        tab=LessonActivity.selLesson+MainActivity.selTopic;
        String z= helper.findLesson(tab,rowCount);
        //if(rowCount==5) {helper.updateMostHit("Lessons",tab);}

        //String z= helper.findLesson("Things2",rowCount);
        Context context=imview.getContext();
        final int imageid= context.getResources().getIdentifier(z,"drawable",context.getPackageName());
        imview.setImageResource(imageid);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rowCount==1) {imview.setImageResource(imageid);
                }
                else
                {
                    rowCount--;
                    String z= helper.findLesson(tab,rowCount);
                    Context context=imview.getContext();
                    int imageid= context.getResources().getIdentifier(z,"drawable",context.getPackageName());
                    imview.setImageResource(imageid);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rowCount==helper.getLessonRow(tab)) {
                    Intent act2 = new Intent(v.getContext(),exercises.class);
                    startActivity(act2);
                }
                else
                {
                    rowCount++;
                    String z= helper.findLesson(tab,rowCount);
                    Context context=imview.getContext();
                    int imageid= context.getResources().getIdentifier(z,"drawable",context.getPackageName());
                    imview.setImageResource(imageid);
                }
            }
        });
        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act2 = new Intent(v.getContext(),exercises.class);
                startActivity(act2);
            }
        });
    }
}
