package com.example.notebook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.Note;
import sqlite.MySQLiteHelper;

public class AddNoteActivity extends AppCompatActivity {

    private EditText add_title, add_message;
    private Button add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        add_title = (EditText) findViewById(R.id.add_title);
        add_message = (EditText) findViewById(R.id.add_message);
        add_btn = (Button) findViewById(R.id.add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = add_title.getText().toString();
                String message = add_message.getText().toString();

                try {
                    if(!title.equals("")) {
                        Note note = new Note(title, message);
                        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(getApplicationContext());
                        int noteId = mySQLiteHelper.addNote(note);
                        if(noteId > 0) {
                            Log.i("new note", String.valueOf(noteId));
                            resetForm();
                            Toast.makeText(AddNoteActivity.this, "Note added succesfully", Toast.LENGTH_SHORT).show();
                        } else {
                            throw new Exception("Error adding new note");
                        }
                    } else {
                        throw new Exception("Please enter title");
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void resetForm() {
        add_title.setText("");
        add_message.setText("");
    }
}
