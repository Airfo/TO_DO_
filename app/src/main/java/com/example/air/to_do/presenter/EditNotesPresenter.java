package com.example.air.to_do.presenter;

import com.example.air.to_do.fragments.NoteListFragment;
import com.example.air.to_do.R;
import com.example.air.to_do.EditNotesContract;
import com.example.air.to_do.fragments.EditNotesFragment;
import com.example.air.to_do.model.Mode;
import com.example.air.to_do.model.Note;

import java.util.Calendar;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class EditNotesPresenter implements EditNotesContract.presenter {
    private Realm realm = Realm.getDefaultInstance();
    private EditNotesFragment eNoteFragment;
    Note note;
    private Mode mode;

    public EditNotesPresenter(EditNotesFragment fragment) {
        this.eNoteFragment = fragment;
    }

    @Override
    public void onInitilizeViews() {
        if (!mode.getIsNew()) {
            note = realm.where(Note.class).equalTo("id", mode.getEditNotePosition()).findFirstAsync();
            note.addChangeListener(new RealmChangeListener<Note>() {
                @Override
                public void onChange(Note note) {
                    eNoteFragment.setTextToEditText(note.getText());
                }
            });
        } else {
            eNoteFragment.setTextToEditText("");
        }
    }

    @Override
    public void onBackPressed() {
        eNoteFragment.changeFragment(new NoteListFragment());
    }

    @Override
    public void onOptionEditDoneClick(String text) {
        if (mode.getIsNew()) {
            if (!text.equals("")) {
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        Realm realm = Realm.getDefaultInstance();
                        realm.executeTransaction(r -> {
                            int nextId;
                            Number currentIdNum = realm.where(Note.class).max("id");
                            if (currentIdNum == null) {
                                nextId = 1;
                            } else {
                                nextId = currentIdNum.intValue() + 1;
                            }
                            Note note = r.createObject(Note.class, nextId);
                            note.setText(text);
                            note.setCalendar(Calendar.getInstance());
                        });
                        realm.close();
                    }
                });
                thread.start();
                eNoteFragment.changeFragment(new NoteListFragment());
            } else {
                eNoteFragment.showError(eNoteFragment.getString(R.string.error_message_if_note_is_empty));
            }
        } else {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(r -> {
                        Note note = realm.where(Note.class).equalTo("id", mode.getEditNotePosition()).findFirst();
                        note.setText(text);
                        note.setCalendar(Calendar.getInstance());
                    });
                    realm.close();
                }
            });
            thread.start();
            eNoteFragment.changeFragment(new NoteListFragment());
        }

    }
    @Override
    public void onDetach(){
        if(note!=null){
            note.removeAllChangeListeners();
        }
        realm.close();
    }

}
