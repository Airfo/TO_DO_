package com.example.air.to_do;

import com.example.air.to_do.model.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class NoteCollection  {

    private RealmList<Note> notes;

    public NoteCollection(RealmList<Note> notes) {
        this.notes = notes;
        sortTaskList();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public Note getNote(int position){
        return notes.get(position);
    }

    public RealmList<Note> getNotes() {
        return notes;
    }

    public void deleteNote(int position){
        notes.remove(position);
    }

    public void setNote(int position, Note note){
        notes.set(position,note);
    }

    public void sortTaskList(){
        Collections.sort(notes);
    }

    public int getCountNotes(){
        return notes.size();
    }

}
