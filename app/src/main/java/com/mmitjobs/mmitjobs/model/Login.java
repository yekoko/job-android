package com.mmitjobs.mmitjobs.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yekokooo on 23/9/16.
 */
public class Login {

    @SerializedName("email")
    String mEmail;

    @SerializedName("password")
    String mPassword;

    public Login(String email, String password)
    {

        this.mEmail = email;
        this.mPassword= password;

    }
}
