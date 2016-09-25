package com.mmitjobs.mmitjobs.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mmitjobs.mmitjobs.JobDetailActivity;
import com.mmitjobs.mmitjobs.MainApplication;
import com.mmitjobs.mmitjobs.R;
import com.mmitjobs.mmitjobs.adapter.RecyclerJobsAdapter;
import com.mmitjobs.mmitjobs.event.JobsEvent;
import com.mmitjobs.mmitjobs.model.Job;
import com.mmitjobs.mmitjobs.util.ApiDataLoading;
import com.mmitjobs.mmitjobs.util.Helper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsFragment extends Fragment implements RecyclerJobsAdapter.RecyclerJobsItemClickListener{


    public JobsFragment() {
        // Required empty public constructor
    }

    RecyclerView mRecyclerView;
    ArrayList<Job> mjobsList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerJobsAdapter mRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_jobs, container, false);

        List<Job> jobs = MainApplication.realm.where(Job.class).findAll();
        mjobsList = new ArrayList<>(jobs);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_jobs);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewAdapter = new RecyclerJobsAdapter(mjobsList, getContext(), this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        if (jobs == null || jobs.isEmpty()) {
            ApiDataLoading apiDataLoading = new ApiDataLoading();
            apiDataLoading.JobDataLoading();
        }
        JobsRecyclerUpdate();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_recycler_jobs);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshJobs();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void JobsDataUpdateEvent(JobsEvent event) {
        mRecyclerViewAdapter.swapData(event.mjobsList);
    }

    private void refreshJobs() {
        NoInternetNotificationCheck();
        //Load Items
        ApiDataLoading apiDataLoading = new ApiDataLoading();
        apiDataLoading.JobDataLoading();

        //Load complte
        JobsLoadComplete();
    }

    private void JobsLoadComplete() {
        // Update the adapter and notify data set changed
        JobsRecyclerUpdate();

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), "Jobs Updated", Toast.LENGTH_SHORT).show();
    }

    private void JobsRecyclerUpdate() {
        List<Job> jobs = MainApplication.realm.where(Job.class).findAll();
        mjobsList = new ArrayList<>(jobs);
        mRecyclerViewAdapter.swapData(mjobsList);
    }

    private void NoInternetNotificationCheck() {

        final TextView mConnectionTextView = (TextView) getActivity().findViewById(R.id.connection_textview);

        if(Helper.checkInterConnection(getActivity().getApplicationContext())) {
            if(!mConnectionTextView.getText().toString().equals("Connected")) {
                mConnectionTextView.setText(R.string.InternetConnected);
                mConnectionTextView.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.green));
                mConnectionTextView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation fadeOut = new AlphaAnimation(1, 0);
                        fadeOut.setInterpolator(new AccelerateInterpolator());
                        fadeOut.setStartOffset(1000);
                        fadeOut.setDuration(1000);
                        mConnectionTextView.setAnimation(fadeOut);
                        mConnectionTextView.setVisibility(View.GONE);
                    }
                }, 3000L);
            }
        } else {
            mConnectionTextView.setText(R.string.NoInternetConnection);
            mConnectionTextView.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.red));
            mConnectionTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void OnItemClick(Job itemClicked, SimpleDraweeView mJobImageView) {
        Bundle args = new Bundle();
        args.putInt("job_id", itemClicked.getId());
        Intent intent = new Intent(getActivity().getApplicationContext(), JobDetailActivity.class);
        intent.putExtras(args);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), mJobImageView, mJobImageView.getTransitionName()).toBundle());
        } else {
            startActivity(intent);
        }
    }
}
