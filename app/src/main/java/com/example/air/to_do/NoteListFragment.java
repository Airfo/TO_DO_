package com.example.air.to_do;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.air.to_do.model.Note;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment {
    private RecyclerView rv;
    private static NotesListRVAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).isNew=false;
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
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        refreshAdapter();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_add_new_note:
                ((MainActivity)getActivity()).isNew=true;
                changeFragment(new EditNotesFragment());
                return true;
            case R.id.action_sort_by_date:
                return true;
            case R.id.action_sort_by_title:
                return true;
            case R.id.action_open_about:
                return true;
        }




        return super.onOptionsItemSelected(menuItem);
    }

    public void changeFragment(Fragment fragment) {
        ((MainActivity) getActivity()).ChangeFragment(fragment);
    }

    public void refreshAdapter() {
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }


}
