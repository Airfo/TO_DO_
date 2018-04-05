package com.example.air.to_do;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.example.air.to_do.model.Note;

public interface EditNotesContract {
    interface view{
        public void initialiazeViews(android.view.View v);
        public void changeFragment(Fragment fragment);
        public void callOnBackPressed();
        public void showError(String message);
    }
    interface presenter{
        public void onBackPressed();
        public void onOptionEditDoneClick(String text,boolean isNew,int position);
    }


}