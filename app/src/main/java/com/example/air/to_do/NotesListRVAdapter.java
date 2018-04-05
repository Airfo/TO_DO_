package com.example.air.to_do;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.air.to_do.model.Note;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;


class NotesListRVAdapter extends RecyclerView.Adapter<NotesListRVAdapter.ViewHolder>{
    public RealmResults<Note> notes;
    NotesListRVAdapter(RealmResults<Note> notes){
        this.notes=notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title_note_tv.setText(notes.get(position).getTitle());
        holder.date_note_tv.setText(notes.get(position).getFormatedDateString());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_note_tv, date_note_tv;

        public ViewHolder(View view) {
            super(view);
            title_note_tv = view.findViewById(R.id.title_note_textview);
            date_note_tv = view.findViewById(R.id.date_note_textview);

        }

    }


}