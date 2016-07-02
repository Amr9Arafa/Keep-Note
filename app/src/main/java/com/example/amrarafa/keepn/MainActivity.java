package com.example.amrarafa.keepn;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.amrarafa.keepn.data.DbOpenHelper;
import com.example.amrarafa.keepn.data.NoteProvider;

public class MainActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertNote(" new text");
        Cursor cursor=getContentResolver().query(NoteProvider.CONTENT_URI,DbOpenHelper.ALL_COLUMNS
        ,null,null,null,null);
         String[] from= {DbOpenHelper.NOTE_TEXT};
        int[] to= {android.R.id.text1};
        CursorAdapter cursorAdapter= new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1
        ,cursor,from,to,0);

        ListView listView= (ListView) findViewById(R.id.android_list);
        listView.setAdapter(cursorAdapter);



    }

    private void insertNote(String textNote) {
        ContentValues contentValues= new ContentValues();
        contentValues.put(DbOpenHelper.NOTE_TEXT,textNote);
        Uri uri=getContentResolver().insert(NoteProvider.CONTENT_URI,contentValues);
        Log.d("MainACtivity", "Inserted note " + uri.getLastPathSegment());
    }
}
