package com.example.air.to_do.model;


import android.support.annotation.NonNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;


public class Note extends RealmObject implements Comparable<Note>{


    @PrimaryKey
    private long id;
    @Index
    private String title;

    private String text;
    private Date date;

    public Calendar getCalendar() {
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(date);
        return  calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.date=calendar.getTime();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
        if(text.length()>10){
            this.title=text.substring(0,10);
        }else{
            this.title=text;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormatedDateString() {
        if (this.getCalendar() != null) {
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            return df.format(this.getCalendar().getTime());
        } else {
            return "";
        }
    }

    @Override
    public int compareTo(@NonNull Note o) {
        return (this.getCalendar()).compareTo(o.getCalendar());
    }

}
