package com.example.air.to_do.model;

public class Mode {
    private static Mode mode;
    private static int editNotePosition;
    private static Boolean isNew = false;
    private static Boolean sortByDate = true;

    private Mode() {
    }

    public static Mode getInstance() {
        if (mode == null) {
            mode = new Mode();
        }
        return mode;
    }

    public static int getEditNotePosition() {
        return editNotePosition;
    }

    public static Boolean getIsNew() {
        return isNew;
    }

    public static Boolean getSortByDate() {
        return sortByDate;
    }

    public static void setEditNotePosition(int editNotePosition) {
        Mode.editNotePosition = editNotePosition;
    }

    public static void setIsNew(Boolean isNew) {
        Mode.isNew = isNew;
    }

    public static void setSortByDate(Boolean sortByDate) {
        Mode.sortByDate = sortByDate;
    }
}
