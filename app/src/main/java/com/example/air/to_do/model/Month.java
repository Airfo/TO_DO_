package com.example.air.to_do.model;

import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;

public final class Month {
    private static final String[] MONTHS_NAMES = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    public static final String getMonth(int i) {
        return MONTHS_NAMES[i];
    }
}
