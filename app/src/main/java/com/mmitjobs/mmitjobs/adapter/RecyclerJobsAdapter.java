package com.mmitjobs.mmitjobs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mmitjobs.mmitjobs.R;
import com.mmitjobs.mmitjobs.model.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyaehein on 4/9/16.
 */
public class RecyclerJobsAdapter extends RecyclerView.Adapter<RecyclerJobsAdapter.RecyclerJobsViewHolder>{


    ArrayList<Job> jobs;
    List<String> mJobsImgs;
    Context mContext;
    RecyclerJobsItemClickListener mListener;

    public RecyclerJobsAdapter(ArrayList<Job> jobs, Context mContext, RecyclerJobsItemClickListener mListener) {
        this.jobs = jobs;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public RecyclerJobsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_jobs, parent, false);
        return new RecyclerJobsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerJobsViewHolder holder, int position) {
        holder.mTxtJobsName.setText(jobs.get(position).getTitle());
        //holder.mImgJobsImg.setImageURI(Uri.parse(jobs.get(position).getAddress()));
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class RecyclerJobsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTxtJobsName;
        SimpleDraweeView mImgJobsImg;
        public RecyclerJobsViewHolder(View itemView) {
            super(itemView);

            mTxtJobsName = (TextView) itemView.findViewById(R.id.txt_card_jobs_name);
            mImgJobsImg = (SimpleDraweeView) itemView.findViewById(R.id.img_card);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.OnItemClick(jobs.get(getLayoutPosition()));
        }
    }

    public interface RecyclerJobsItemClickListener {
        void OnItemClick(Job itemClicked);
    }
}
