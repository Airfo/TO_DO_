package com.example.air.to_do;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;

import com.example.air.to_do.model.Note;


public class MainActivity extends AppCompatActivity {
    private static Toolbar toolbar;
    public Note newNote, editNote;
    public int editNotePosition;
    Boolean menuCreated=false;
    public Boolean keyboardShowed=false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ChangeFragment(new NoteListFragment());

    }

    public InputMethodManager getInputMethodManager(){
        return (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void ChangeFragment(android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_main_activity_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}