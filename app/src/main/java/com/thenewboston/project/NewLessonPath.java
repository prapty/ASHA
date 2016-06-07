package com.thenewboston.project;

import android.app.Dialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewLessonPath extends AppCompatActivity {
    DBHelper helper;

    TextView textFolder;
    Button buttonOpenDialog;
    Button buttonUp;
    String KEY_TEXTPSS = "TEXTPSS";
    static final int CUSTOM_DIALOG_ID = 0;
    ListView dialog_ListView;

    File root;
    File curFolder;
    public EditText txt2=null;
    public TextView label=null;
    private List<String> fileList = new ArrayList<String>();
    public EditText txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lesson_path);
        helper=new DBHelper(this);

        Button btnAdd = (Button) findViewById(R.id.button10);
        Button btnMore = (Button) findViewById(R.id.button11);
        Button btnBrowse = (Button) findViewById(R.id.button9);
        txt=(EditText) findViewById(R.id.editText4);

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CUSTOM_DIALOG_ID);
            }
        });
        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        curFolder = root;

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsItem newItem = new DetailsItem(txt.getText().toString());
                helper.insertDetails(newItem,NewLesson.newLess);
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText("");
                showDialog(CUSTOM_DIALOG_ID);

            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog = null;

        switch (id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(NewLessonPath.this);
                dialog.setContentView(R.layout.dialoglayout);
                dialog.setTitle("Custom Dialog");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                textFolder = (TextView) dialog.findViewById(R.id.folder);
                buttonUp = (Button) dialog.findViewById(R.id.up);
                buttonUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });

                dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);
                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = new File(fileList.get(position));
                        if(selected.isDirectory()) {
                            ListDir(selected);
                        } else {
                            Toast.makeText(NewLessonPath.this, selected.toString() + " selected",
                                    Toast.LENGTH_LONG).show();
                            txt2.setText(selected.toString());
                            label.setText(selected.toString());
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }
                    }
                });

                break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }
    }

    void ListDir(File f) {
        if(f.equals(root)) {
            buttonUp.setEnabled(false);
        } else {
            buttonUp.setEnabled(true);
        }

        curFolder = f;
        textFolder.setText(f.getPath());

        File[] files = f.listFiles();
        fileList.clear();

        for(File file : files) {
            fileList.add(file.getPath());
        }

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, fileList);
        dialog_ListView.setAdapter(directoryList);
    }
}
