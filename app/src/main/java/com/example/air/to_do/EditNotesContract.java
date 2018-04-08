package com.example.air.to_do;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.example.air.to_do.model.Note;

public interface EditNotesContract {
    interface view {
        public void initialiazeViews(android.view.View v);

        public void setTextToEditText(String text);

        public void changeFragment(Fragment fragment);

        public void showError(String message);
    }

    interface presenter {
        public void onInitilizeViews();

        public void onBackPressed();

        public void onOptionEditDoneClick(String text);

        public void onDetach();
    }


}
