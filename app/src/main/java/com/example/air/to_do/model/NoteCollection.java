package com.example.air.to_do.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteCollection {
    private List<Note> notes=new ArrayList<Note>();

    public NoteCollection(List<Note> notes) {
        this.notes = notes;
        sortTaskList();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public Note getNote(int position){
        return notes.get(position);
    }

    public List<Note> getNotesList(){
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
