package com.example.air.to_do;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;

import java.util.Arrays;
import java.util.Calendar;

import com.example.air.to_do.model.Note;

import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {
    private static Toolbar toolbar;
    public Realm realm;
    public RealmResults<Note> notes;
    NoteCollection notesReaded;
    public Note newNote, editNote;
    public int editNotePosition;
    Boolean menuCreated=false;
    Boolean isNew=false;

    private final OrderedRealmCollectionChangeListener<RealmResults<Note>> realmChangeListener = (people, changeSet) -> {
        String insertions = changeSet.getInsertions().length == 0 ? "" : "\n - Insertions: " + Arrays.toString(changeSet.getInsertions());
        String deletions = changeSet.getDeletions().length == 0 ? "" : "\n - Deletions: " + Arrays.toString(changeSet.getDeletions());
        String changes = changeSet.getChanges().length == 0 ? "" : "\n - Changes: " + Arrays.toString(changeSet.getChanges());

    };

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

        //Realm.deleteRealm(Realm.getDefaultConfiguration());
        realm = Realm.getDefaultInstance();
        notes = realm.where(Note.class).findAllAsync();
        notes.addChangeListener(realmChangeListener);
        example(realm);
    }
    private void example(Realm realm) {
        int i =0;

        // Delete all persons
        //showStatus("Deleting all persons");
        //realm.executeTransaction(r -> r.delete(Note.class));
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

    public void showStatus (int i) {
        for(i=0;i<realm.where(Note.class).count();i++){
            Log.d("value", "Value^     "+ realm.where(Note.class).findAll().get(i).getTitle()+" "+realm.where(Note.class).findAll().get(i).getFormatedDateString());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notes.removeAllChangeListeners(); // Remove the change listener when no longer needed.
        realm.close(); // Remember to close Realm when done.
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
