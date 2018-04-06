package com.example.air.to_do.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.air.to_do.MainActivity;
import com.example.air.to_do.R;
import com.example.air.to_do.model.Note;
import com.example.air.to_do.model.NoteDiffUtilCallback;

import io.realm.RealmResults;
import io.realm.Sort;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment implements NoteRowClickListener<Note> {
    private RecyclerView rv;
    private static NotesListRVAdapter adapter;
    private boolean Sorting = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).isNew = false;
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        initialiazeViews(view);
        return view;
    }

    public void initialiazeViews(View view) {
        setHasOptionsMenu(true);
        rv = view.findViewById(R.id.list_note_recyclerview);
        ((MainActivity) getActivity()).getInputMethodManager().toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        adapter = new NotesListRVAdapter(((MainActivity) getActivity()).realm.where(Note.class).findAll());
        rv.setAdapter(adapter);
        getActivity().setTitle(R.string.header_list_fragment);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        Sorting = true;
        if (((MainActivity) getActivity()).sortByDate) {
            refreshAdapter(((MainActivity) getActivity()).realm.where(Note.class).sort("date", Sort.ASCENDING).findAll());
        } else {
            refreshAdapter(((MainActivity) getActivity()).realm.where(Note.class).sort("title", Sort.ASCENDING).findAll());
        }
        adapter.setRowClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_add_new_note:
                ((MainActivity) getActivity()).isNew = true;
                changeFragment(new EditNotesFragment());
                return true;
            case R.id.action_sort_by_date:
                Sorting = true;
                ((MainActivity) getActivity()).sortByDate = true;
                refreshAdapter(((MainActivity) getActivity()).realm.where(Note.class).sort("date", Sort.ASCENDING).findAll());
                return true;
            case R.id.action_sort_by_title:
                Sorting = true;
                ((MainActivity) getActivity()).sortByDate = false;
                refreshAdapter(((MainActivity) getActivity()).realm.where(Note.class).sort("title", Sort.ASCENDING).findAll());
                return true;
            case R.id.action_open_about:
                About();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void About() {
        final AboutDialog ab_dialog = new AboutDialog(this.getContext(), ((MainActivity)getActivity()));
        ab_dialog.create();
        ab_dialog.show();
    }

    public void changeFragment(Fragment fragment) {
        ((MainActivity) getActivity()).ChangeFragment(fragment);
    }

    public void refreshAdapter(RealmResults<Note> notes) {
        if (Sorting) {
            NoteDiffUtilCallback noteDiffUtilCallback =
                    new NoteDiffUtilCallback(adapter.getNotes(), notes);
            DiffUtil.DiffResult productDiffResult = DiffUtil.calculateDiff(noteDiffUtilCallback);
            adapter.setNotes(notes);
            productDiffResult.dispatchUpdatesTo(adapter);
            Sorting = false;
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }


    @Override
    public void onRowClicked(int id) {
        ((MainActivity) getActivity()).isNew = false;
        ((MainActivity) getActivity()).editNote = ((MainActivity) getActivity()).realm.where(Note.class).findAll().get(id);
        ((MainActivity) getActivity()).editNotePosition = id;
        changeFragment(new EditNotesFragment());
    }
}
