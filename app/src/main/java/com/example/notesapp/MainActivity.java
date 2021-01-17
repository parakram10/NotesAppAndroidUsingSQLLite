package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout linear1, linear2, linear3;
    Button addNotes, submit;
    ListView listView;
    EditText title, desc;
    MyListViewAdapter arrayAdapter;
    DataBaseHelper dataBaseHelper;
    List<NotesModel> nodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);

        addNotes = findViewById(R.id.addNotes);
        submit = findViewById(R.id.submit);

        listView = findViewById(R.id.listView);

        title = findViewById(R.id.noteTitle);
        desc = findViewById(R.id.noteDesc);
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        displayList();

        arrayAdapter = new MyListViewAdapter(MainActivity.this,dataBaseHelper.getAllNotes());
        listView.setAdapter(arrayAdapter);

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setVisibility(View.GONE);
                linear2.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String t = title.getText().toString();
                String d = desc.getText().toString();

                if(TextUtils.isEmpty(t)) {
                    Toast.makeText(MainActivity.this, "Title must be given to the note.",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(d)){
                    Toast.makeText(MainActivity.this, "Description must be given to the note.",Toast.LENGTH_SHORT).show();
                }
                else {

                    linear2.setVisibility(View.GONE);
                    linear1.setVisibility(View.VISIBLE);

                    NotesModel notesModel;

                    try {
                        notesModel = new NotesModel(title.getText().toString(), desc.getText().toString(), -1);
                    } catch (Exception e) {
                        notesModel = new NotesModel("No Title", "No Description", -1);
                    }
                    boolean b = dataBaseHelper.addNote(notesModel);

                    if (b)
                        Toast.makeText(MainActivity.this, "Note successfully added to List", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "Not added to List", Toast.LENGTH_SHORT).show();

                    arrayAdapter = new MyListViewAdapter(MainActivity.this, dataBaseHelper.getAllNotes());
                    listView.setAdapter(arrayAdapter);

                    title.setText(" ");
                    desc.setText(" ");
                }
                displayList();
            }
        });
    }

    private void displayList() {
        if (dataBaseHelper.getAllNotes().size() > 0) {
            listView.setVisibility(View.VISIBLE);
            linear3.setVisibility(View.GONE);
        } else {
            linear3.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }
}