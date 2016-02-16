package com.example.notebook;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Note;

/**
 * Created by suraj on 1/25/16.
 * Email suraj.sht@hotmail.com
 */
public class CustomNoteListAdapter extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<Note> notes;

    public CustomNoteListAdapter(Activity context, ArrayList<Note> notes) {
        super(context, R.layout.all_note_list_item, notes);
        this.context = context;
        this.notes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.all_note_list_item, null, true);

        TextView item_title = (TextView) rowView.findViewById(R.id.list_item_title);
        TextView item_message = (TextView) rowView.findViewById(R.id.list_item_message_short);
        TextView item_date = (TextView) rowView.findViewById(R.id.list_item_create_date);

        Note note = notes.get(position);

        item_title.setText(note.getTitle());
        String message = note.getMessage();
        if(message.length() > 10) {
            String short_message = message.substring(0,10);
            Log.d("short message", short_message);
            item_message.setText(String.format("%s...", short_message));
        } else {
            item_message.setText(message);
        }

        String create_date = note.getCreateDate();
        Log.d("create_date", create_date);
        item_date.setText(create_date);

        return rowView;
    }
}
