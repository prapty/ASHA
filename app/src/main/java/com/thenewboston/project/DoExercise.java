package com.thenewboston.project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class DoExercise extends AppCompatActivity {
    private RadioGroup radioOptionGroup;
    private RadioButton radioChoice;
    private Button btnOk;
    DBHelper helper;
    ArrayList<QnItem> items = new ArrayList<QnItem>();
    ArrayList<String> options;
    int score;
    int intC=0;
    int randRow=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_exercise);
        helper = new DBHelper(this);
        items.addAll(helper.retrieveQn(ShowLesson.tab));
        ImageView imview = (ImageView) findViewById(R.id.imageView);
        Context context = imview.getContext();
        int imageid = 0;
        TextView label = (TextView) findViewById(R.id.textView8);
        Button nextqn= (Button) findViewById(R.id.button13);
        String res="";

        int len=items.size();

        Random random=new Random();

            for (int i = 1; i <= len && intC!=3; i++){

            long range = (long) len - (long) 1 + 1;
            long fraction = (long) (range * random.nextDouble());
            randRow = (int) (fraction + 1);

            options = new ArrayList<>();
            String z = items.get(randRow - 1).path;
            StringTokenizer multiTokenizer = new StringTokenizer(z, "\\.");
            while (multiTokenizer.hasMoreTokens()) {
                if (multiTokenizer.nextToken().equals("drawable")) {
                    res = multiTokenizer.nextToken();
                    break;
                }
            }

            imageid = context.getResources().getIdentifier(res, "drawable", context.getPackageName());
            imview.setImageResource(imageid);
            label.setText(items.get(randRow - 1).question);

            multiTokenizer = new StringTokenizer(items.get(randRow - 1).answer, ".");
            while (multiTokenizer.hasMoreTokens()) {
                options.add(multiTokenizer.nextToken());
            }
            intC++;
            radioOptionGroup = (RadioGroup) findViewById(R.id.radioOption);
            btnOk = (Button) findViewById(R.id.button12);
            RadioButton radio1 = (RadioButton) findViewById(R.id.radio1);
            radio1.setText(options.get(0));
            RadioButton radio2 = (RadioButton) findViewById(R.id.radio2);
            radio2.setText(options.get(1));
            RadioButton radio3 = (RadioButton) findViewById(R.id.radio3);
            radio3.setText(options.get(2));
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get selected radio button from radioGroup
                    int selectedId = radioOptionGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    radioChoice = (RadioButton) findViewById(selectedId);
                    //Toast.makeText(DoExercise.this,radioChoice.getText(), Toast.LENGTH_SHORT).show();
                    if (radioChoice.getText().equals(items.get(randRow - 1).correctA)) {
                        Toast.makeText(DoExercise.this, "Correct", Toast.LENGTH_SHORT).show();
                        score++;

                    } else {
                        Toast.makeText(DoExercise.this, "Incorrect. Correct answer is " +
                                items.get(randRow - 1).correctA, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        Toast.makeText(DoExercise.this,"Your score is " + score,Toast.LENGTH_SHORT).show();
    }
}
