package com.mmitjobs.mmitjobs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mmitjobs.mmitjobs.fragment.CompanyFragment;
import com.mmitjobs.mmitjobs.fragment.JobsFragment;
import com.mmitjobs.mmitjobs.fragment.NearbyjobsFragment;
import com.mmitjobs.mmitjobs.model.Job;
import com.mmitjobs.mmitjobs.util.ApiDataLoading;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by pyaehein on 4/9/16.
 */
public class TabAdapter extends FragmentStatePagerAdapter{

    int mNumOfTabs;

    public TabAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                check_before_calling_fragment(position);
                return new JobsFragment();
            case 1:
                return new CompanyFragment();
            case 2:
                return new NearbyjobsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    private Fragment check_before_calling_fragment(int position) {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Job> results = realm.where(Job.class).findAll();
        if(results.isEmpty()) {
            ApiDataLoading apiDataLoading = new ApiDataLoading();
            apiDataLoading.JobDataLoading();
        }
        return new JobsFragment();
    }
}
