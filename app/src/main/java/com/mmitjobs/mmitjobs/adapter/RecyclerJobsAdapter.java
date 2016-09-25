package com.mmitjobs.mmitjobs.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mmitjobs.mmitjobs.R;
import com.mmitjobs.mmitjobs.model.Job;
import com.mmitjobs.mmitjobs.util.Constant;

import java.util.ArrayList;

/**
 * Created by pyaehein on 4/9/16.
 */
public class RecyclerJobsAdapter extends RecyclerView.Adapter<RecyclerJobsAdapter.RecyclerJobsViewHolder>{


    ArrayList<Job> jobs;
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

        holder.mImgJobsImg.setImageURI(Uri.parse(Constant.Image_URL + jobs.get(position).getCompany().getImage()));
        holder.mTxtJobsName.setText(jobs.get(position).getTitle());
        holder.mTxtCompanyName.setText(jobs.get(position).getCompany().getName());
        holder.mTxtJobsExperience.setText(jobs.get(position).getExperience().getName());
        holder.mTxtJobsSalary.setText(jobs.get(position).getSalary());
        holder.mTxtJobsTag.setText(jobs.get(position).getCategory().getName());
        holder.mTxtJobsTimer.setText(jobs.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class RecyclerJobsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SimpleDraweeView mImgJobsImg;
        TextView mTxtJobsName;
        TextView mTxtCompanyName;
        TextView mTxtJobsExperience;
        TextView mTxtJobsSalary;
        TextView mTxtJobsTag;
        TextView mTxtJobsTimer;

        public RecyclerJobsViewHolder(View itemView) {
            super(itemView);

            mImgJobsImg = (SimpleDraweeView) itemView.findViewById(R.id.img_card);
            mTxtJobsName = (TextView) itemView.findViewById(R.id.txt_card_jobs_name);
            mTxtCompanyName = (TextView) itemView.findViewById(R.id.txt_card_jobs_companyname);
            mTxtJobsExperience = (TextView) itemView.findViewById(R.id.txt_card_jobs_experience);
            mTxtJobsSalary = (TextView) itemView.findViewById(R.id.txt_card_jobs_salary);
            mTxtJobsTag = (TextView) itemView.findViewById(R.id.txt_card_jobs_tag);
            mTxtJobsTimer = (TextView) itemView.findViewById(R.id.txt_card_jobs_timer);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.OnItemClick(jobs.get(getLayoutPosition()), mImgJobsImg);
        }
    }

    public interface RecyclerJobsItemClickListener {
        void OnItemClick(Job itemClicked, SimpleDraweeView mImgJobsImg);
    }

    public void swapData(ArrayList<Job> mjobsList) {
        this.jobs = null;
        this.jobs = mjobsList;
        notifyDataSetChanged();
    }
}
