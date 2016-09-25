package com.mmitjobs.mmitjobs.api;

import com.mmitjobs.mmitjobs.model.Jobs;
import com.mmitjobs.mmitjobs.model.Login;
import com.mmitjobs.mmitjobs.model.Register;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MainService {

    @GET("jobs.json")
    Call<Jobs> getAllJobs();

    @POST("login")
    Call<Login> postLogin(@Body Login login);

    @POST("register")
    Call<Register> postRegister(@Body Register register);



}
