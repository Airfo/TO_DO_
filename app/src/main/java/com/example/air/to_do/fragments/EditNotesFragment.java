package com.example.air.to_do.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.air.to_do.EditNotesContract;
import com.example.air.to_do.presenter.EditNotesPresenter;
import com.example.air.to_do.activity.MainActivity;
import com.example.air.to_do.R;
import com.example.air.to_do.model.Note;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditNotesFragment extends Fragment implements EditNotesContract.view{
    private EditText note_text_et;
    private EditNotesPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_note, container, false);
        initialiazeViews(v);
        presenter=new EditNotesPresenter(((MainActivity)getActivity()).realm, this);
        // Inflate the layout for this fragment
        return v;

    }

    public void initialiazeViews(android.view.View v) {
        getActivity().setTitle(R.string.header_edit_fragment);
        note_text_et=v.findViewById(R.id.edit_note_edtitext);
        note_text_et.requestFocus();
        ((MainActivity) getActivity()).getInputMethodManager().toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
        if(!((MainActivity) getActivity()).isNew){
            note_text_et.setText(((MainActivity) getActivity()).realm.where(Note.class).findAll().get(((MainActivity) getActivity()).editNotePosition).getText());
        }
    }

    public void changeFragment(Fragment fragment) {
        ((MainActivity) getActivity()).ChangeFragment(fragment);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            presenter.onBackPressed();
        }
        if (menuItem.getItemId() == R.id.action_add_new_note) {
            presenter.onOptionEditDoneClick(note_text_et.getText().toString(),((MainActivity) getActivity()).isNew, ((MainActivity) getActivity()).editNotePosition);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void callOnBackPressed() {
        (getActivity()).onBackPressed();
    }

    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
