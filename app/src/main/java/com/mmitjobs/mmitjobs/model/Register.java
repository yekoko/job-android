package com.mmitjobs.mmitjobs.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yekokooo on 24/9/16.
 */
public class Register {
    @SerializedName("name")
    String mName;

    @SerializedName("email")
    String mEmail;

    @SerializedName("password")
    String mPassword;

    @SerializedName("phone")
    String mPhone;

    public Register(String name, String email, String password , String phone)
    {
        this.mName = name;
        this.mEmail = email;
        this.mPassword= password;
        this.mPhone = phone;
    }
}
