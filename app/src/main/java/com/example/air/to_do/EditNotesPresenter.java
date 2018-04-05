package com.example.air.to_do;

import com.example.air.to_do.model.Note;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditNotesPresenter implements EditNotesContract.presenter {
    private Realm realm;
    private RealmResults<Note> notes;
    private EditNotesFragment eNoteFragment;

    public EditNotesPresenter(Realm realm, RealmResults<Note> notes, EditNotesFragment fragment) {
        this.realm = realm;
        this.notes = notes;
        this.eNoteFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        eNoteFragment.callOnBackPressed();
    }

    @Override
    public void onOptionEditDoneClick(String text, boolean isNew, int position) {
        if (isNew) {
            if (!text.equals("")) {
                realm.executeTransaction(r -> {
                    Note note = r.createObject(Note.class, realm.where(Note.class).count() + 1);
                    note.setText(text);
                    note.setCalendar(Calendar.getInstance());
                });
                eNoteFragment.changeFragment(new NoteListFragment());
            } else {
                eNoteFragment.showError(eNoteFragment.getString(R.string.error_message_if_note_is_empty));
            }
        } else {
            realm.executeTransaction(r -> {
                Note note = realm.where(Note.class).findAll().get(position);
                note.setText(text);
                note.setCalendar(Calendar.getInstance());
                eNoteFragment.changeFragment(new NoteListFragment());
            });
        }

    }
}
