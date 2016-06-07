package com.thenewboston.project;

/*import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        helper = new DBHelper(this);

        TextView label = (TextView) findViewById(R.id.textView11);
        helper.updateMostHit("Flashcards","banana");
        label.setText(CardItem.mostHit);
    }
}
*/
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Button btnDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        RadioButton radio1 = (RadioButton) findViewById(R.id.radioMale);
        radio1.setText("Male");

        RadioButton radio2 = (RadioButton) findViewById(R.id.radioFemale);
        radio2.setText("Female");
        btnDisplay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioSexButton = (RadioButton) findViewById(selectedId);

                Toast.makeText(Main2Activity.this,
                        radioSexButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });

    }
}