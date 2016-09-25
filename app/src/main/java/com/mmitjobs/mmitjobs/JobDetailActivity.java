package com.mmitjobs.mmitjobs;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mmitjobs.mmitjobs.model.Job;
import com.mmitjobs.mmitjobs.util.Constant;

public class JobDetailActivity extends AppCompatActivity {

    TextView mTextJobTitle;
    TextView mCompanyName;
    TextView mJobExperience;
    TextView mJobLocation;
    SimpleDraweeView mImgCard;
    TextView mResponsibiliteisText;
    TextView mRequirementsText;
    TextView mMessageToCandidate;
    TextView mPhones;
    Button mButtonSaveJob;
    Button mButtonApplyNow;
    int id;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle args = getIntent().getExtras();
        id = args.getInt("job_id");

        Job job = MainApplication.realm.where(Job.class).equalTo("id", id).findFirst();

        mTextJobTitle = (TextView) findViewById(R.id.text_job_title);
        mCompanyName = (TextView) findViewById(R.id.text_job_company_name);
        mJobExperience = (TextView) findViewById(R.id.text_job_experience);
        mJobLocation = (TextView) findViewById(R.id.text_job_location);
        mImgCard = (SimpleDraweeView) findViewById(R.id.img_card);
        mResponsibiliteisText = (TextView) findViewById(R.id.responsibilities_text);
        mRequirementsText = (TextView) findViewById(R.id.requirements_text);
        mMessageToCandidate = (TextView) findViewById(R.id.message_to_candidate);
        mPhones = (TextView) findViewById(R.id.phones);
        mButtonSaveJob = (Button) findViewById(R.id.button_save_job);
        mButtonApplyNow = (Button) findViewById(R.id.button_apply_now);

        mTextJobTitle.setText(job.getTitle());
        mCompanyName.setText(job.getCompany().getName());
        mJobExperience.setText(job.getExperience().getName());
        mJobLocation.setText(job.getAddress());
        mImgCard.setImageURI(Uri.parse(Constant.Image_URL + job.getCompany().getImage()));
        mResponsibiliteisText.setText(job.getResponsibilities());
        mRequirementsText.setText(job.getRequirements());
        mMessageToCandidate.setText("Interested Candidates, who wish to apply for the above position; please send in your resume to " + job.getEmail() + " or click the \"Apply Now\" below.");
        mPhones.setText(job.getPhoneNo());

        mButtonSaveJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobDetailActivity.this, "Job saved.", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonApplyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobDetailActivity.this, "Job applied.", Toast.LENGTH_SHORT).show();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.move));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
