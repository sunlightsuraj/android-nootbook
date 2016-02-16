package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import model.Note;
import sqlite.MySQLiteHelper;

public class OpenNoteActivity extends AppCompatActivity {

    private LinearLayout text_layout, edit_layout;

    private TextView title;
    private TextView message;
    private TextView date;
    private EditText editTitle;
    private EditText editMessage;
    private TextView editDate;
    private Button editButton;
    private Button editCacel;

    private FloatingActionButton fab_add;
    private FloatingActionButton fab_edit;
    private FloatingActionButton fab_delete;

    private String intentType;
    private int noteId;

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text_layout = (LinearLayout) findViewById(R.id.text_layout);
        edit_layout = (LinearLayout) findViewById(R.id.edit_layout);

        title = (TextView) findViewById(R.id.text_title);
        message = (TextView) findViewById(R.id.text_message);
        date = (TextView) findViewById(R.id.text_date);
        editTitle = (EditText) findViewById(R.id.edit_title);
        editMessage = (EditText) findViewById(R.id.edit_message);
        editDate = (TextView) findViewById(R.id.edit_date);
        editButton = (Button) findViewById(R.id.edit_btn);
        editCacel = (Button) findViewById(R.id.edit_cancel);

        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_edit = (FloatingActionButton) findViewById(R.id.fab_edit);
        fab_delete = (FloatingActionButton) findViewById(R.id.fab_delete);

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            intentType = bundle.getString("intentType");
            noteId = bundle.getInt("noteId");

            populate();

            fab_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                    Intent intent1 = new Intent(OpenNoteActivity.this, AddNoteActivity.class);
                    startActivity(intent1);
                }
            });

            fab_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text_layout.setVisibility(View.GONE);
                    edit_layout.setVisibility(View.VISIBLE);
                    fab_edit.setVisibility(View.INVISIBLE);
                }
            });

            fab_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(note != null) {
                        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(getApplicationContext());
                        if(mySQLiteHelper.deleteNote(note)) {
                            Toast.makeText(OpenNoteActivity.this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(OpenNoteActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(OpenNoteActivity.this, "Error deleting note", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String t = editTitle.getText().toString();
                    String m = editMessage.getText().toString();

                    try {
                        if(!t.equals("")) {
                            Note note1 = new Note(note.getId(),t,m,null);
                            MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(getApplicationContext());
                            int note_id = mySQLiteHelper.updateNote(note1);
                            if(note_id > 0) {
                                title.setText(t);
                                message.setText(m);

                                text_layout.setVisibility(View.VISIBLE);
                                edit_layout.setVisibility(View.GONE);
                                fab_edit.setVisibility(View.VISIBLE);
                                Toast.makeText(OpenNoteActivity.this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                throw new Exception("Error updating note");
                            }
                        } else {
                            throw new Exception("Please enter title");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(OpenNoteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            editCacel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text_layout.setVisibility(View.VISIBLE);
                    edit_layout.setVisibility(View.GONE);
                    fab_edit.setVisibility(View.VISIBLE);
                    editTitle.setText(note.getTitle());
                    editMessage.setText(note.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            populate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populate() {
        if (intentType != null) {
            switch (intentType) {
                case "open":
                    text_layout.setVisibility(View.VISIBLE);
                    edit_layout.setVisibility(View.GONE);
                    break;

                case "edit":
                    edit_layout.setVisibility(View.VISIBLE);
                    text_layout.setVisibility(View.GONE);
                    break;

                default:

            }
        }

        MySQLiteHelper db = new MySQLiteHelper(this);
        note = db.getNote(noteId);

        if(note != null) {
            title.setText(note.getTitle());
            message.setText(note.getMessage());
            date.setText(note.getCreateDate());
            editTitle.setText(note.getTitle());
            editMessage.setText(note.getMessage());
            editDate.setText(note.getCreateDate());
        }
    }

}
