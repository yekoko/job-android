package com.mmitjobs.mmitjobs.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Jobs {

    @SerializedName("jobs")

    public List<Job> jobs;

    public List<Job> getJobs() {
        return jobs;
    }
}
