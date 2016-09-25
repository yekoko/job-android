package com.mmitjobs.mmitjobs.event;

/**
 * Created by pyaehein on 24/9/16.
 */

public class InternetConnectionEvent {

    public final boolean ConnectionExist;

    public InternetConnectionEvent(boolean connectionExist) {
        this.ConnectionExist = connectionExist;
    }
}
