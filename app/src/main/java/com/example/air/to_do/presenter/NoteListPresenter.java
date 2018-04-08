package com.example.air.to_do.presenter;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;

import com.example.air.to_do.NoteListContract;
import com.example.air.to_do.fragments.EditNotesFragment;
import com.example.air.to_do.fragments.NoteListFragment;
import com.example.air.to_do.model.Mode;
import com.example.air.to_do.model.Note;
import com.example.air.to_do.R;

import java.util.HashSet;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class NoteListPresenter implements NoteListContract.presenter {
    private Realm realm = Realm.getDefaultInstance();
    String emailMessage = "";
    RealmResults<Note> results;
    Handler handler;
    private NoteListFragment nLFragment;
    private static Mode mode = Mode.getInstance();

    public NoteListPresenter(NoteListFragment fragment) {
        nLFragment = fragment;
    }

    @Override
    public void onInitilizeViews() {
        if (mode.getSortByDate()) {
            requestSortData("date", Sort.DESCENDING);
        } else {
            requestSortData("title", Sort.ASCENDING);
        }
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onOptionsSendClick(HashSet<Integer> notesToDeleteIds) {
        if (notesToDeleteIds.isEmpty()) {
            nLFragment.showError(nLFragment.getString(R.string.error_message_if_selected_none));
        } else {
            emailMessage = "";
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    Realm realm = Realm.getDefaultInstance();
                    int j=1;
                    for (int i : notesToDeleteIds) {
                        emailMessage = emailMessage + "\r\n Note №" + j + "\r\n" + realm.where(Note.class).equalTo("id", i).findFirst().getText()+"\r\n";
                        j++;
                    }
                    Message msg;
                    msg = handler.obtainMessage(1, emailMessage);
                    handler.sendMessage(msg);
                    realm.close();
                }
            });
            thread.start();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 1) {
                        nLFragment.exitSendDeleteMode();
                        nLFragment.sendEmail((String) msg.obj);
                    }
                }
            };
        }


    }



    @Override
    public void onRowClicked(int id) {
        mode.setIsNew(false);
        mode.setEditNotePosition(id);
        nLFragment.changeFragment(new EditNotesFragment());
    }

    @Override
    public void onOptionAddNote() {
        mode.setIsNew(true);
        nLFragment.changeFragment(new EditNotesFragment());
    }

    @Override
    public void onOptionSortByDate() {
        mode.setSortByDate(true);
        requestSortData("date", Sort.DESCENDING);
    }

    @Override
    public void onOptionSortByTitle() {
        mode.setSortByDate(false);
        requestSortData("title", Sort.ASCENDING);
    }

    @Override
    public void requestSortData(String fieldName, Sort sort) {
        results = realm.where(Note.class).sort(fieldName, sort).findAllAsync();
        results.addChangeListener(new RealmChangeListener<RealmResults<Note>>() {
            @Override
            public void onChange(RealmResults<Note> notes) {
                nLFragment.refreshAdapter(notes);
            }
        });

    }


    @Override
    public void onOptionAboutClick() {
        nLFragment.showAbout();
    }

    @Override
    public void onOptionsDeleteClick(HashSet<Integer> notesToDeleteIds) {
        if (notesToDeleteIds.isEmpty()) {
            nLFragment.showError(nLFragment.getString(R.string.error_message_if_selected_none));
        } else {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    Realm realm = Realm.getDefaultInstance();
                    for (int i : notesToDeleteIds) {
                        realm.executeTransaction(r -> {
                            realm.where(Note.class).equalTo("id", i).findFirst().deleteFromRealm();
                        });
                    }
                }
            });
            thread.start();
            nLFragment.exitSendDeleteMode();
        }
    }

    @Override
    public void onBackPressed() {
        if (mode.getSendDeleteMode()) {
            mode.setSendDeleteMode(false);
            nLFragment.refreshAdapter(null);
        }
    }

    @Override
    public void onDetach() {
        if (results != null) {
            results.removeAllChangeListeners();
        }
        realm.close();
    }

}
