package com.example.air.to_do.fragments;

import android.support.v4.app.Fragment;

import com.example.air.to_do.model.Note;

import io.realm.RealmResults;

public interface NoteListContract {
    interface view {
        public void initialiazeViews(android.view.View v);

        public void changeFragment(Fragment fragment);

        public void showError(String message);

        public void refreshAdapter(RealmResults<Note> notes);
    }

    interface presenter {
        public void onBackPressed();

        public void onOptionNewNoteClick(String text, boolean isNew, int position);

    }


}
