package com.example.air.to_do;

import android.support.v4.app.Fragment;

import com.example.air.to_do.model.Note;

import io.realm.RealmResults;

public interface NoteListContract {
    interface view {
        public void initialiazeViews(android.view.View v);

        public void changeFragment(Fragment fragment);

        public void showAbout();

        public void showError(String message);

        public void refreshAdapter(RealmResults<Note> notes);
    }

    interface presenter {
        public void onInitilizeViews();

        public void onRowClicked(int id);

        public void onOptionAddNote();

        public void onOptionSortByDate();

        public void onOptionSortByTitle();

        public void onOptionAboutClick();

        public void onBackPressed();

    }


}
