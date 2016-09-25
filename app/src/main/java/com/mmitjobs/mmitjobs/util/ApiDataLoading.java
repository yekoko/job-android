package com.mmitjobs.mmitjobs.util;

import android.util.Log;

import com.mmitjobs.mmitjobs.MainApplication;
import com.mmitjobs.mmitjobs.api.MainApi;
import com.mmitjobs.mmitjobs.api.MainService;
import com.mmitjobs.mmitjobs.event.JobsEvent;
import com.mmitjobs.mmitjobs.model.Category;
import com.mmitjobs.mmitjobs.model.Companies;
import com.mmitjobs.mmitjobs.model.Company;
import com.mmitjobs.mmitjobs.model.Experience;
import com.mmitjobs.mmitjobs.model.Experiences;
import com.mmitjobs.mmitjobs.model.Job;
import com.mmitjobs.mmitjobs.model.Jobs;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mmitjobs.mmitjobs.MainApplication.realm;

public class ApiDataLoading {

    public void CompanyDataLoading() {
        Call<Companies> companiesCall = MainApi.createService(MainService.class).getAllCompanies();
        companiesCall.enqueue(new Callback<Companies>() {
            @Override
            public void onResponse(Call<Companies> call, Response<Companies> response) {
                if(response.isSuccessful()) {
                    List<Company> companies = response.body().getCompanies();
                    if(companies != null) {
                        MainApplication.realm.beginTransaction();
                        boolean CompanieUpdated = false;
                        for (Company company : companies) {
                            Company rcompany = MainApplication.realm.createObject(Company.class);
                            if (CompanieIfNotExists(company.getId())) {
                                CompanieUpdated = true;
                                rcompany.setId(company.getId());
                                rcompany.setUserId(company.getUserId());
                                rcompany.setName(company.getName());
                                rcompany.setAddress(company.getAddress());
                                rcompany.setPhone(company.getPhone());
                                rcompany.setCompanySize(company.getCompanySize());
                                rcompany.setImage(company.getImage());
                                rcompany.setRegistrationNo(company.getRegistrationNo());
                                rcompany.setWebsite(company.getWebsite());
                                rcompany.setWorkingHours(company.getWorkingHours());
                                rcompany.setIndustryId(company.getIndustryId());
                                rcompany.setOverview(company.getOverview());
                                rcompany.setCreatedAt(company.getCreatedAt());
                                rcompany.setUpdatedAt(company.getUpdatedAt());
                            }
                        }
                        if(CompanieUpdated){
                            MainApplication.realm.commitTransaction();
                        } else {
                            MainApplication.realm.cancelTransaction();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<Companies> call, Throwable t) {

            }
        });
    }

    public void JobDataLoading() {
        Call<Jobs> jobsCall = MainApi.createService(MainService.class).getAllJobs();
        jobsCall.enqueue(new Callback<Jobs>() {

            @Override
            public void onResponse(Call<Jobs> call, Response<Jobs> response) {
                Log.d("Gmodel", response.body().toString());
                if(response.isSuccessful()) {
                    List<Job> jobs = response.body().getJobs();

                    Log.d("Realm", "realmpath: " + realm.getPath());
                    boolean JobUpdated = false;
                    MainApplication.realm.beginTransaction();
                    if(jobs.size() > 0) {
                        for (Job job : jobs) {

                            if(JobExistModified(job.getId(), job.getUpdatedAt())) {

                                Job rjob = MainApplication.realm.createObject(Job.class);
                                rjob.setId(job.getId());
                                rjob.setTitle(job.getTitle());
                                rjob.setCompanyId(job.getCompanyId());
                                rjob.setCategoryId(job.getCategoryId());
                                rjob.setExperienceId(job.getExperienceId());
                                rjob.setSalary(job.getSalary());
                                rjob.setRequirements(job.getRequirements());
                                rjob.setResponsibilities(job.getResponsibilities());
                                rjob.setDescription(job.getDescription());
                                rjob.setEmail(job.getEmail());
                                rjob.setPhoneNo(job.getPhoneNo());
                                rjob.setAddress(job.getAddress());
                                rjob.setEndDate(job.getEndDate());
                                rjob.setCreatedAt(job.getCreatedAt());
                                rjob.setUpdatedAt(job.getUpdatedAt());

                                Category rcategory = MainApplication.realm.createObject(Category.class);
                                rcategory.setId(job.getCategory().getId());
                                rcategory.setName(job.getCategory().getName());
                                rcategory.setCreatedAt(job.getCategory().getCreatedAt());
                                rcategory.setUpdatedAt(job.getCategory().getUpdatedAt());
                                rjob.setCategory(rcategory);

                                Experience rexperience = MainApplication.realm.createObject(Experience.class);
                                rexperience.setId(job.getExperience().getId());
                                rexperience.setName(job.getExperience().getName());
                                rexperience.setCreatedAt(job.getExperience().getCreatedAt());
                                rexperience.setUpdatedAt(job.getExperience().getUpdatedAt());
                                rjob.setExperience(rexperience);

                                Company rcompany = MainApplication.realm.createObject(Company.class);
                                rcompany.setId(job.getCompany().getId());
                                rcompany.setUserId(job.getCompany().getUserId());
                                rcompany.setName(job.getCompany().getName());
                                rcompany.setAddress(job.getCompany().getAddress());
                                rcompany.setPhone(job.getCompany().getPhone());
                                rcompany.setCompanySize(job.getCompany().getCompanySize());
                                rcompany.setImage(job.getCompany().getImage());
                                rcompany.setRegistrationNo(job.getCompany().getRegistrationNo());
                                rcompany.setWebsite(job.getCompany().getWebsite());
                                rcompany.setWorkingHours(job.getCompany().getWorkingHours());
                                rcompany.setIndustryId(job.getCompany().getIndustryId());
                                rcompany.setOverview(job.getCompany().getOverview());
                                rcompany.setCreatedAt(job.getCompany().getCreatedAt());
                                rcompany.setUpdatedAt(job.getCompany().getUpdatedAt());
                                rjob.setCompany(rcompany);

                                JobUpdated = true;
                            }

                        }
                    }

                    if(JobUpdated){
                        MainApplication.realm.commitTransaction();
                        List<Job> rjobs = MainApplication.realm.where(Job.class).findAll();
                        ArrayList<Job> mjobsList = new ArrayList<>(rjobs);
                        EventBus.getDefault().post(new JobsEvent(mjobsList));

                    } else {
                        MainApplication.realm.cancelTransaction();
                    }

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

    public void ExperienceDataLoading() {
        Call<Experiences> experiencesCall = MainApi.createService(MainService.class).getAllExperiences();
        experiencesCall.enqueue(new Callback<Experiences>() {
            @Override
            public void onResponse(Call<Experiences> call, Response<Experiences> response) {
                if(response.isSuccessful()) {
                    List<Experience> experiences = response.body().getExperiences();
                    if(experiences != null) {
                        MainApplication.realm.beginTransaction();
                        boolean ExperienceUpdated = false;
                        for (Experience experience : experiences) {
                            if(ExperienceIfNotExists(experience.getId())) {
                                ExperienceUpdated = true;
                                Experience rexperience = MainApplication.realm.createObject(Experience.class);
                                rexperience.setId(experience.getId());
                                rexperience.setName(experience.getName());
                                rexperience.setCreatedAt(experience.getCreatedAt());
                                rexperience.setUpdatedAt(experience.getUpdatedAt());
                            }
                        }
                        if(ExperienceUpdated){
                            MainApplication.realm.commitTransaction();
                        } else {
                            MainApplication.realm.cancelTransaction();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<Experiences> call, Throwable t) {

            }
        });
    }

    public void AllAPILoading() {
        this.ExperienceDataLoading();
        this.CompanyDataLoading();
        this.JobDataLoading();
    }


    private boolean JobExistModified(int id, String UpdateAt) {
        Job job = MainApplication.realm.where(Job.class).equalTo("id", id).findFirst();

        // check job is exist or not
        if(job == null) {
            return true;
        }
        // check current update date is lower than new
        if(job.getUpdatedAt() != UpdateAt) {
            job.deleteFromRealm();
            //MainApplication.realm.commitTransaction();
            //MainApplication.realm.beginTransaction();
            return true;
        }
        return false;
    }

    private boolean CompanieIfNotExists(int id) {
        List<Company> companies = MainApplication.realm.where(Company.class).equalTo("id", id).findAll();
        return companies.size() == 0;
    }

    private boolean ExperienceIfNotExists(int id) {
        List<Experience> experiences = MainApplication.realm.where(Experience.class).equalTo("id", id).findAll();
        return experiences.size() == 0;
    }

}
