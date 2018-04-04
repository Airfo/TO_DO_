package com.example.air.to_do.model;


import android.support.annotation.NonNull;

import java.util.Calendar;


public class Note implements Comparable<Note>{
    private String title;
    private String text;
    private Calendar calendar;
    private String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    public Note(String text, Calendar calendar) {
        this.text = text;
        this.calendar=calendar;
        if(text.length()>10){
            title=text.substring(0,10);
        }else{
            title=text;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }



    public String getFormatedDateString() {
        if (calendar != null) {
            return Integer.toString(calendar.get(Calendar.DAY_OF_MONTH))
                    + " " + monthNames[calendar.get(Calendar.MONTH)]
                    + " " + calendar.get(Calendar.YEAR);
        } else {
            return "";
        }
    }

    @Override
    public int compareTo(@NonNull Note o) {
        return calendar.compareTo(o.calendar);
    }

}
