package com.mmitjobs.mmitjobs.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Companies {

    @SerializedName("companies")

    public List<Company> companies;

    public List<Company> getCompanies() {
        return companies;
    }
}
