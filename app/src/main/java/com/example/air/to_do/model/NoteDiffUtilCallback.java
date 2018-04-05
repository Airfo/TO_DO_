package com.example.air.to_do.model;

import android.support.v7.util.DiffUtil;

import io.realm.RealmResults;


public class NoteDiffUtilCallback extends DiffUtil.Callback {

    private final RealmResults<Note> oldList;
    private final RealmResults<Note> newList;

    public NoteDiffUtilCallback(RealmResults<Note> oldList, RealmResults<Note> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Note oldProduct = oldList.get(oldItemPosition);
        Note newProduct = newList.get(newItemPosition);
        return oldProduct.getId() == newProduct.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Note oldProduct = oldList.get(oldItemPosition);
        Note newProduct = newList.get(newItemPosition);
        return oldProduct.getText().equals(newProduct.getText())
                && oldProduct.getCalendar().toString().equals(newProduct.getCalendar());
    }
}

