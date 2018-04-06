package com.example.air.to_do.presenter;

import com.example.air.to_do.NoteListContract;
import com.example.air.to_do.fragments.EditNotesFragment;
import com.example.air.to_do.fragments.NoteListFragment;
import com.example.air.to_do.model.Note;

import io.realm.Realm;
import io.realm.Sort;

public class NoteListPresenter implements NoteListContract.presenter {
    private Realm realm;
    private NoteListFragment nLFragment;

    public NoteListPresenter(Realm realm, NoteListFragment fragment) {
        this.realm = realm;
        nLFragment = fragment;
    }

    @Override
    public void onInitilizeViews(Boolean sortByDate) {
        if (sortByDate) {
            nLFragment.refreshAdapter(realm.where(Note.class).sort("date", Sort.DESCENDING).findAll());
        } else {
            nLFragment.refreshAdapter(realm.where(Note.class).sort("title", Sort.ASCENDING).findAll());
        }
    }

    @Override
    public void onRowClicked() {
        nLFragment.changeFragment(new EditNotesFragment());
    }

    @Override
    public void onOptionAddNote() {
        nLFragment.changeFragment(new EditNotesFragment());
    }

    @Override
    public void onOptionSortByDate() {
        nLFragment.refreshAdapter(realm.where(Note.class).sort("date", Sort.DESCENDING).findAll());
    }

    @Override
    public void onOptionSortByTitle() {
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
