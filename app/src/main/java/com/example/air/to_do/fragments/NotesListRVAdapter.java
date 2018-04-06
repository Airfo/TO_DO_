package com.example.air.to_do.fragments;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.air.to_do.R;
import com.example.air.to_do.model.Note;

import io.realm.Realm;
import io.realm.RealmResults;


class NotesListRVAdapter extends RecyclerView.Adapter<NotesListRVAdapter.ViewHolder> {
    private Realm realm=Realm.getDefaultInstance();
    private RealmResults<Note> notes;
    private NoteRowClickListener<Note> rowClickListener;

    NotesListRVAdapter() {
        this.notes = realm.where(Note.class).findAll();
    }

    public void setNotes(RealmResults<Note> notes) {
        this.notes = notes;
    }

    public RealmResults<Note> getNotes() {
        return notes;
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
        holder.item_note_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowClickListener.onRowClicked((int) (long) notes.get(holder.getAdapterPosition()).getId() - 1);
            }
        });
    }

    public void setRowClickListener(NoteRowClickListener<Note> rowClickListener) {
        this.rowClickListener = rowClickListener;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_note_tv, date_note_tv;
        RelativeLayout item_note_rl;

        public ViewHolder(View view) {
            super(view);
            title_note_tv = view.findViewById(R.id.title_note_textview);
            date_note_tv = view.findViewById(R.id.date_note_textview);
            item_note_rl = view.findViewById(R.id.item_note_relativelay);
        }

    }


}