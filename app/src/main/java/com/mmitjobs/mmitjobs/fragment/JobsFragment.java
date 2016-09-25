package com.mmitjobs.mmitjobs.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mmitjobs.mmitjobs.MainApplication;
import com.mmitjobs.mmitjobs.R;
import com.mmitjobs.mmitjobs.adapter.RecyclerJobsAdapter;
import com.mmitjobs.mmitjobs.model.Job;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);

        mjobsList = null;
        List<Job> jobs = MainApplication.realm.where(Job.class).findAll();
        mjobsList = new ArrayList<>(jobs);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_jobs);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(new RecyclerJobsAdapter(mjobsList, getContext(), this));

        return view;
    }

    @Override
    public void OnItemClick(Job itemClicked) {
        Bundle args = new Bundle();
        args.putString("job_name", itemClicked.getTitle());

        Toast.makeText(getContext(), itemClicked.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
