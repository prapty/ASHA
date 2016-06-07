package com.thenewboston.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class exercises extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Button btnY = (Button) findViewById(R.id.button4);
        Button btnN = (Button) findViewById(R.id.button6);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act2 = new Intent(v.getContext(),DoExercise.class);
                startActivity(act2);
            }
        });
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent act2 = new Intent(v.getContext(),LessonActivity.class);
                startActivity(act2);
            }
        });
    }
}
