package com.notes.lagmay.simplenotetakingapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NotesActivity extends AppCompatActivity {

    private DbHelper dbHelper = null;
    private SQLiteDatabase db = null;
    public static final String TAG = "MainActivity";

    public EditText editText;
    public EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        editText = findViewById(R.id.editText);
        editText1 = findViewById(R.id.edit);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.save_note) {
            // insert note sample
            long idi = dbHelper.insertNote(db, editText.getText().toString(), editText1.getText().toString());
            Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
        }

        db.close();
        return true;
    }
}


