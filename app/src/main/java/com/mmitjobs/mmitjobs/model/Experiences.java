package com.mmitjobs.mmitjobs.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Experiences {

    @SerializedName("jobs")

    public List<Experience> experiences;

    public List<Experience> getExperiences() {
        return experiences;
    }
}
