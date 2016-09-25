package com.mmitjobs.mmitjobs.util;

import android.util.Log;

import com.mmitjobs.mmitjobs.MainApplication;
import com.mmitjobs.mmitjobs.api.MainApi;
import com.mmitjobs.mmitjobs.api.MainService;
import com.mmitjobs.mmitjobs.model.Jobs;
import com.mmitjobs.mmitjobs.model.Job;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiDataLoading {

    public void JobDataLoading() {

//        Call<Jobs> jobsCall = MainApi.createService(MainService.class).getAllJobs();
//        Log.d("PH:", this.toString());
//        jobsCall.enqueue(new Callback<Jobs>() {
//            @Override
//            public void onResponse(Call<Jobs> call, Response<Jobs> response) {
//                Log.d("Gmodel", response.body().toString());
//                if(response.isSuccessful()) {
//                    List<Job> jobs = response.body().getJobs();
//                    DeleteAllData();
//                    MainApplication.realm.beginTransaction();
//                    Log.d("Realmph", response.body().getJobs().get(0).getTitle());
//                    Log.d("Realm", "realmpath: " + MainApplication.realm.getPath());
//                    for (Job job : jobs) {
//                        com.mmitjobs.mmitjobs.rmodel.Job rjob = MainApplication.realm.createObject(com.mmitjobs.mmitjobs.rmodel.Job.class);
//                        rjob.setTitle(job.getTitle());
//                        Log.d("Realm:", job.getTitle());
//                    }
//                    MainApplication.realm.commitTransaction();
//                }
//                else {
//                    Log.d("response fail", "shit");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Jobs> call, Throwable t) {
//
//            }
//        });

        Call<Jobs> jobsCall = MainApi.createService(MainService.class).getAllJobs();
        Log.d("PH:", this.toString());
        jobsCall.enqueue(new Callback<Jobs>() {
            @Override
            public void onResponse(Call<Jobs> call, Response<Jobs> response) {
                Log.d("Gmodel", response.body().toString());
                if(response.isSuccessful()) {
                    List<Job> jobs = response.body().getJobs();
                    DeleteAllData();
                    MainApplication.realm.beginTransaction();
                    Log.d("Realmph", response.body().getJobs().get(0).getTitle());
                    Log.d("Realm", "realmpath: " + MainApplication.realm.getPath());
                    for (Job job : jobs) {
                        Job rjob = MainApplication.realm.createObject(Job.class);
                        rjob.setTitle(job.getTitle());
                        Log.d("Realm:", job.getTitle());
                    }
                    MainApplication.realm.commitTransaction();
                }
                else {
                    Log.d("response fail", "shit");
                }

            }

            @Override
            public void onFailure(Call<Jobs> call, Throwable t) {

            }
        });
    }



    private void DisconnectedFragment() {
//        DisconnectedFragment disconnectedFragment = new DisconnectedFragment();
//        Bundle args = new Bundle();
//        args.putString("return", returnFragment);
//        disconnectedFragment.setArguments(args);
//        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_data_loading, disconnectedFragment, null);
//        ft.commit();
    }

    private void DeleteAllData() {
        MainApplication.realm.beginTransaction();
        MainApplication.realm.deleteAll();
        MainApplication.realm.commitTransaction();
    }
}
