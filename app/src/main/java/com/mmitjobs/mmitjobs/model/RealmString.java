package com.mmitjobs.mmitjobs.model;

import io.realm.RealmObject;

/**
 * Created by pyaehein on 12/9/16.
 */
public class RealmString extends RealmObject {

    private String value;

    public RealmString() {
    }

    public RealmString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
