package com.example.amrarafa.keepn;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amrarafa.keepn.data.DbOpenHelper;
import com.example.amrarafa.keepn.data.NoteProvider;


public class EditorActivity extends AppCompatActivity {

    private String action;
    private EditText editor;
    private String noteFiler;
    private String oldText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editor= (EditText) findViewById(R.id.editText);

        Intent intent =getIntent();

        Uri uri = intent.getParcelableExtra(NoteProvider.CONTENT_ITEM_TYPE);

        if (uri == null){
            action= Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_note));
        }
        else {
            action=Intent.ACTION_EDIT;
            noteFiler=DbOpenHelper.NOTE_ID + "=" + uri.getLastPathSegment();

            Cursor cursor =getContentResolver().
                    query(uri,DbOpenHelper.ALL_COLUMNS,noteFiler,null,null);

            cursor.moveToFirst();
            oldText=cursor.getString(cursor.getColumnIndex(DbOpenHelper.NOTE_TEXT));
            editor.setText(oldText);
            editor.requestFocus();



        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finishEditing();
                break;
        }



        return true;
    }

    private void finishEditing(){
        String newText=editor.getText().toString().trim();

        switch (action){
            case Intent.ACTION_INSERT:
                if(newText.length()==0){
                    setResult(RESULT_CANCELED);
                }
                else {
                    insertNote(newText);
                }
                break;
            case Intent.ACTION_EDIT:
                if(newText.length()==0){
                    //delete note
                }else if (oldText.equals(newText)){
                    setResult(RESULT_CANCELED);
                }else{
                    updateNote(newText);
                }


        }
        finish();
    }

    private void updateNote(String textNote) {
        ContentValues contentValues= new ContentValues();
        contentValues.put(DbOpenHelper.NOTE_TEXT,textNote);
        getContentResolver().update(NoteProvider.CONTENT_URI, contentValues, noteFiler, null);
        Toast.makeText(this, R.string.note_updated, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);

    }

    private void insertNote(String textNote) {
        ContentValues contentValues= new ContentValues();
        contentValues.put(DbOpenHelper.NOTE_TEXT,textNote);
        getContentResolver().insert(NoteProvider.CONTENT_URI, contentValues);
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        finishEditing();
    }
}
