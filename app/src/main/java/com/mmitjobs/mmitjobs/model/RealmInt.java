package com.mmitjobs.mmitjobs.model;

import io.realm.RealmObject;

/**
 * Created by pyaehein on 12/9/16.
 */
public class RealmInt extends RealmObject {

    private int number;

    public RealmInt() {
    }

    public RealmInt(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
