package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.Note;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "notebook";
    private static final int DB_VERSION = 1;

    private static final String T_NOTE = "note";

    private static final String id = "id";
    private static final String title = "title";
    private static final String message = "message";
    private static final String create_date = "create_date";

    private static final String[] COLUMNS = {id,title,message,create_date};

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + T_NOTE + "(" +
                id + " integer primary key autoincrement," +
                title + " varchar(100) not null," +
                message + " text," +
                create_date + " timestamp default current_timestamp )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + T_NOTE);
        this.onCreate(db);
    }

    public int addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(title, note.getTitle());
        values.put(message, note.getMessage());
        //values.put(create_date, note.getCreateDate().toString());

        int noteId = (int) db.insert(T_NOTE, null, values);

        db.close();

        return noteId;
    }

    public Note getNote(int noteId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select " + id + "," +
                title + "," +
                message + "," +
                "strftime('%d', " + create_date + ") as day," +
                "case strftime('%m', " + create_date + ") when '01' then 'January' when '02' then 'February' when '03' then 'March' when '04' then 'April' when '05' then 'May' when '06' then 'June' when '07' then 'July' when '08' then 'August' when '09' then 'September' when '10' then 'October' when '11' then 'November' when '12' then 'December' else '' end as month," +
                "strftime('%Y', " + create_date + ") as year" +
                " from " + T_NOTE + " where id = ?";

        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(noteId)});
        /*Cursor cursor = db.query(T_NOTE,
                COLUMNS,
                id + " = ? ",
                new String[] {String.valueOf(noteId)},
                null,
                null,
                null);*/

        Note note = null;

        if(cursor.moveToFirst()) {
            note = new Note();
            note.setId(Integer.parseInt(cursor.getString(0)));
            note.setTitle(cursor.getString(1));
            note.setMessage(cursor.getString(2));
            note.setCreateDate(cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5));
        }
        db.close();
        return note;
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> allNotes = new ArrayList<Note>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + id + "," +
                title + "," +
                message + "," +
                "strftime('%d', " + create_date + ") as day," +
                "case strftime('%m', " + create_date + ") when '01' then 'January' when '02' then 'February' when '03' then 'March' when '04' then 'April' when '05' then 'May' when '06' then 'June' when '07' then 'July' when '08' then 'August' when '09' then 'September' when '10' then 'October' when '11' then 'November' when '12' then 'December' else '' end as month," +
                "strftime('%Y', " + create_date + ") as year" +
                " from " + T_NOTE;

        Cursor cursor = db.rawQuery(query, null);

        /*Cursor cursor = db.query(T_NOTE,
                COLUMNS,
                null,
                null,
                null,
                null,
                create_date,
                null);*/

        Note note = null;
        if(cursor.moveToFirst()) {
            do {
                note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setMessage(cursor.getString(2));
                note.setCreateDate(cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5));

                allNotes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();
        return allNotes;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(title, note.getTitle());
        values.put(message, note.getMessage());

        int i = db.update(T_NOTE, values, id + " = ?", new String[] {String.valueOf(note.getId())});

        db.close();

        return i;
    }

    public boolean deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(T_NOTE, id + " = ? ", new String[] { String.valueOf(note.getId())});

        db.close();

        return true;
    }
}
