package com.example.air.to_do;

import android.support.v4.app.Fragment;

import com.example.air.to_do.model.Note;

import java.util.HashSet;

import io.realm.RealmResults;
import io.realm.Sort;

public interface NoteListContract {
    interface view {
        public void initialiazeViews(android.view.View v);

        public void changeFragment(Fragment fragment);

        public void showAbout();

        public void showError(String message);

        public void refreshAdapter(RealmResults<Note> notes);

        public void exitSendDeleteMode();
    }

    interface presenter {
        public void onInitilizeViews();

        public void onRowClicked(int id);

        public void onOptionAddNote();

        public void onOptionSortByDate();

        public void onOptionSortByTitle();

        public void requestSortData(String fieldName, Sort sort);

        public void onOptionAboutClick();

        public void onOptionsDeleteClick(HashSet<Integer> notesToDeleteIds);

        public void onBackPressed();

        public void onDetach();
    }


}
