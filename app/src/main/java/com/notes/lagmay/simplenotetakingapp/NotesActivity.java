package com.notes.lagmay.simplenotetakingapp;

import android.content.Intent;
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

    public EditText editText;
    public EditText editText1;
    private Intent intent;
    private String a;
    private String b;
    private long c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        intent = getIntent();

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        editText = findViewById(R.id.editText);
        editText1 = findViewById(R.id.editText2);

        a = intent.getStringExtra("Title");
        b = intent.getStringExtra("Body");
        c = intent.getLongExtra("noteid", 1);

        editText.setText(a);
        editText1.setText(b);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.save_note){
            if(c == 1) {
                long id1 = dbHelper.insertNote(db, editText.getText().toString(), editText1.getText().toString());

                Toast.makeText(this, "Note has been saved!", Toast.LENGTH_SHORT).show();

                this.finish();
            }
            else
            {
                boolean id2 = dbHelper.updateNote(db, c ,editText.getText().toString(), editText1.getText().toString());
                if(id2)
                {
                    Toast.makeText(this, "Note has been updated!", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                else
                {
                    //none
                }
            }
        }

        return true;
    }
}



