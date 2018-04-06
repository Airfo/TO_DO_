package com.example.air.to_do.fragments;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.air.to_do.R;
import com.example.air.to_do.model.Mode;
import com.example.air.to_do.model.Note;

import java.util.ArrayList;
import java.util.HashSet;

import io.realm.Realm;
import io.realm.RealmResults;


class NotesListRVAdapter extends RecyclerView.Adapter<NotesListRVAdapter.ViewHolder> {
    private Realm realm = Realm.getDefaultInstance();
    private RealmResults<Note> notes;
    private NoteRowClickListener<Note> rowClickListener;
    private NoteRowLongClickListener<Note> rowLongClickListener;
    private Mode mode;
    private HashSet<Integer> checkedPos = new HashSet<Integer>();

    NotesListRVAdapter() {
        this.notes = realm.where(Note.class).findAll();
        clearSelectedNotes();
    }

    public void setNotes(RealmResults<Note> notes) {
        this.notes = notes;
        clearSelectedNotes();
    }

    public RealmResults<Note> getNotes() {
        return notes;
    }

    public HashSet getIdNotesToDelete(){
        HashSet<Integer> notesToDelete=new HashSet<Integer>();
            for(int i:checkedPos){
                notesToDelete.add((int) (long)notes.get(i).getId());
            }
            checkedPos.clear();
        return notesToDelete;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    public void clearSelectedNotes() {
        checkedPos.clear();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title_note_tv.setText(notes.get(position).getTitle());
        holder.date_note_tv.setText(notes.get(position).getFormatedDateString());
        holder.select_note_cb.setChecked(checkedPos.contains(position));

        holder.item_note_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mode.getSendDeleteMode()) {
                    rowClickListener.onRowClicked((int) (long) notes.get(holder.getAdapterPosition()).getId());
                } else {
                    holder.select_note_cb.setChecked(!holder.select_note_cb.isChecked());

                    if (holder.select_note_cb.isChecked()) {
                        checkedPos.add(position);
                    } else {
                        checkedPos.remove(position);
                    }
                }
            }
        });
        holder.item_note_rl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                rowLongClickListener.onRowLongClicked();
                return true;
            }
        });
        if (!mode.getSendDeleteMode()) {
            holder.select_note_cb.setVisibility(View.INVISIBLE);
        } else {
            holder.select_note_cb.setVisibility(View.VISIBLE);
        }

    }

    public void setRowClickListener(NoteRowClickListener<Note> rowClickListener) {
        this.rowClickListener = rowClickListener;
    }

    public void setRowLongClickListener(NoteRowLongClickListener<Note> rowLongClickListener) {
        this.rowLongClickListener = rowLongClickListener;
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_note_tv, date_note_tv;
        RelativeLayout item_note_rl;
        CheckBox select_note_cb;

        public ViewHolder(View view) {
            super(view);
            title_note_tv = view.findViewById(R.id.title_note_textview);
            date_note_tv = view.findViewById(R.id.date_note_textview);
            item_note_rl = view.findViewById(R.id.item_note_relativelay);
            select_note_cb = view.findViewById(R.id.select_note_checkbox);
            select_note_cb.setClickable(false);
        }

    }


}