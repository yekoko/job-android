package com.mmitjobs.mmitjobs.event;

import com.mmitjobs.mmitjobs.model.Job;

import java.util.ArrayList;

/**
 * Created by pyaehein on 23/9/16.
 */

public class JobsEvent {

    public final ArrayList<Job> mjobsList;

    public JobsEvent(ArrayList<Job> mjobsList) {
        this.mjobsList = mjobsList;
    }
}
