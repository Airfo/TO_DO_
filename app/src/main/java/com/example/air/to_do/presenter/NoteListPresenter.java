package com.example.air.to_do.presenter;

import com.example.air.to_do.NoteListContract;
import com.example.air.to_do.fragments.EditNotesFragment;
import com.example.air.to_do.fragments.NoteListFragment;
import com.example.air.to_do.model.Mode;
import com.example.air.to_do.model.Note;

import io.realm.Realm;
import io.realm.Sort;

public class NoteListPresenter implements NoteListContract.presenter {
    private Realm realm=Realm.getDefaultInstance();
    private NoteListFragment nLFragment;
    private static Mode mode= Mode.getInstance();

    public NoteListPresenter(NoteListFragment fragment) {
        nLFragment = fragment;
    }

    @Override
    public void onInitilizeViews() {
        if (mode.getSortByDate()) {
            nLFragment.refreshAdapter(realm.where(Note.class).sort("date", Sort.DESCENDING).findAll());
        } else {
            nLFragment.refreshAdapter(realm.where(Note.class).sort("title", Sort.ASCENDING).findAll());
        }
    }

    @Override
    public void onRowClicked(int id) {
        mode.setIsNew(false);
        mode.setEditNotePosition(id);
        nLFragment.changeFragment(new EditNotesFragment());
    }

    @Override
    public void onOptionAddNote() {
        mode.setIsNew(true);
        nLFragment.changeFragment(new EditNotesFragment());
    }

    @Override
    public void onOptionSortByDate() {
        mode.setSortByDate(true);
        nLFragment.refreshAdapter(realm.where(Note.class).sort("date", Sort.DESCENDING).findAll());
    }

    @Override
    public void onOptionSortByTitle() {
        mode.setSortByDate(false);
        nLFragment.refreshAdapter(realm.where(Note.class).sort("title", Sort.ASCENDING).findAll());
    }

    @Override
    public void onOptionAboutClick() {
        nLFragment.showAbout();
    }

    @Override
    public void onBackPressed() {

    }

}
