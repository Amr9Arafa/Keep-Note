package com.example.amrarafa.keepn;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.amrarafa.keepn.data.DbOpenHelper;
import com.example.amrarafa.keepn.data.NoteProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertNote(" new text");
    }

    private void insertNote(String textNote) {
        ContentValues contentValues= new ContentValues();
        contentValues.put(DbOpenHelper.NOTE_TEXT,textNote);
        Uri uri=getContentResolver().insert(NoteProvider.CONTENT_URI,contentValues);
        Log.d("MainACtivity", "Inserted note " + uri.getLastPathSegment());
    }
}
