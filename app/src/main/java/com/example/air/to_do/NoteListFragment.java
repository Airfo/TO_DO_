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
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        initialiazeViews(view);
        return view;
    }

    public void initialiazeViews(View view) {
        setHasOptionsMenu(true);
        rv = (RecyclerView) view.findViewById(R.id.list_note_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        //adapter = new NotesListRVAdapter();
        //rv.setAdapter(adapter);
        getActivity().setTitle("Мои заметки");
        refreshAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_add_new_note) {
            changeFragment(new EditNotesFragment());
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

    }


}
