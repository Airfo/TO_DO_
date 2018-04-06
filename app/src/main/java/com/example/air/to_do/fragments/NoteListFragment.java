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
import android.widget.Toast;

import com.example.air.to_do.activity.MainActivity;
import com.example.air.to_do.NoteListContract;
import com.example.air.to_do.R;
import com.example.air.to_do.model.Mode;
import com.example.air.to_do.model.Note;
import com.example.air.to_do.model.NoteDiffUtilCallback;
import com.example.air.to_do.presenter.NoteListPresenter;

import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment implements NoteRowClickListener<Note>, NoteListContract.view, NoteRowLongClickListener<Note> {
    private RecyclerView rv;
    private NoteListPresenter presenter;
    private static NotesListRVAdapter adapter;
    private boolean Sorting = false;
    private Mode mode= Mode.getInstance();
    private Menu menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mode.setIsNew(false);
        presenter = new NoteListPresenter(this);
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
        adapter = new NotesListRVAdapter();
        rv.setAdapter(adapter);
        getActivity().setTitle(R.string.header_list_fragment);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        Sorting = true;
        presenter.onInitilizeViews();
        adapter.setRowClickListener(this);
        adapter.setRowLongClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_add_new_note:
                presenter.onOptionAddNote();
                return true;
            case R.id.action_sort_by_date:
                Sorting = true;
                presenter.onOptionSortByDate();
                return true;
            case R.id.action_sort_by_title:
                Sorting = true;
                presenter.onOptionSortByTitle();
                return true;
            case R.id.action_open_about:
                presenter.onOptionAboutClick();
                return true;
            case R.id.action_delete_notes:
                presenter.onOptionsDeleteClick(adapter.getIdNotesToDelete());
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void showAbout() {
        final AboutDialog ab_dialog = new AboutDialog(this.getContext(), ((MainActivity) getActivity()));
        ab_dialog.create();
        ab_dialog.show();
    }

    public void changeFragment(Fragment fragment) {
        ((MainActivity) getActivity()).ChangeFragment(fragment);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
        this.menu=menu;
        menu.getItem(1).setVisible(false);
    }


    @Override
    public void onRowClicked(int id) {
        presenter.onRowClicked(id);
    }

    @Override
    public void exitSendDeleteMode(){
        mode.setSendDeleteMode(false);
        menu.getItem(1).setVisible(false);
        adapter.clearSelectedNotes();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRowLongClicked() {
        if(!mode.getSendDeleteMode()){
            mode.setSendDeleteMode(true);
            menu.getItem(1).setVisible(true);
            adapter.notifyDataSetChanged();
        }
    }


}
