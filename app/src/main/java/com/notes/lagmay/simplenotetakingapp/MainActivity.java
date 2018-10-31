package com.notes.lagmay.simplenotetakingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DbHelper dbHelper = null;
    private SQLiteDatabase db = null;
    private ArrayList<Note> notes = new ArrayList<>();
    private ListView notesList = null;
    private NotesAdapter adapter = null;
    private Intent noteIntent;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteIntent = new Intent(this, NotesActivity.class);

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        notesList = findViewById(R.id.notes_list);

        notesList.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        notes =  dbHelper.getAllNote(db);
        adapter = new NotesAdapter(this, R.layout.layout, notes);
        notesList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.add_note){
            // Start the add note activity here
            Intent intent = new Intent(this, NotesActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Note selectedNote = notes.get(position);
        final long noteID = selectedNote.getId();

        // We need to show the dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(selectedNote.getTitle() + "\n\n" +selectedNote.getBody());
        builder.setCancelable(false);
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteIntent.putExtra("noteid", noteID);
                noteIntent.putExtra("Title",selectedNote.getTitle());
                noteIntent.putExtra("Body",selectedNote.getBody());
                startActivity(noteIntent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });

        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // perform delete
                boolean isDeleteSuccessful = dbHelper.deleteNote(db, noteID);
                if(isDeleteSuccessful){
                    Toast.makeText(MainActivity.this, "Note is deleted!", Toast.LENGTH_LONG).show();

                    notes.remove(selectedNote);
                    adapter.setDataset(notes);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
    }

}
