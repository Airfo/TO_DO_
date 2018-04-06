package com.example.air.to_do.presenter;

import com.example.air.to_do.fragments.NoteListFragment;
import com.example.air.to_do.R;
import com.example.air.to_do.EditNotesContract;
import com.example.air.to_do.fragments.EditNotesFragment;
import com.example.air.to_do.model.Mode;
import com.example.air.to_do.model.Note;

import java.util.Calendar;

import io.realm.FieldAttribute;
import io.realm.Realm;


public class EditNotesPresenter implements EditNotesContract.presenter {
    private Realm realm = Realm.getDefaultInstance();
    private EditNotesFragment eNoteFragment;
    private Mode mode;

    public EditNotesPresenter(EditNotesFragment fragment) {
        this.eNoteFragment = fragment;
    }

    @Override
    public void onInitilizeViews() {
        if (!mode.getIsNew()) {
            eNoteFragment.setTextToEditText(realm.where(Note.class).equalTo("id", mode.getEditNotePosition()).findFirst().getText());
        }else{
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
                realm.executeTransaction(r -> {
                    int nextId;
                    Number currentIdNum = realm.where(Note.class).max("id");
                    if(currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    Note note = r.createObject(Note.class, nextId);
                    note.setText(text);
                    note.setCalendar(Calendar.getInstance());
                });
                eNoteFragment.changeFragment(new NoteListFragment());
            } else {
                eNoteFragment.showError(eNoteFragment.getString(R.string.error_message_if_note_is_empty));
            }
        } else {
            realm.executeTransaction(r -> {
                Note note = realm.where(Note.class).equalTo("id", mode.getEditNotePosition()).findFirst();
                note.setText(text);
                note.setCalendar(Calendar.getInstance());
            });
            eNoteFragment.changeFragment(new NoteListFragment());
        }

    }
}
