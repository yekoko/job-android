package com.mmitjobs.mmitjobs;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.mmitjobs.mmitjobs.provider.JobSearchProvider;

public class JobSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);

        Toolbar msearchAppbar = (Toolbar) findViewById(R.id.search_result_appbar);
        setSupportActionBar(msearchAppbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent searchIntent = getIntent();
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle(query);
            Toast.makeText(JobSearchActivity.this, query, Toast.LENGTH_SHORT).show();

            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this, JobSearchProvider.AUTHORITY, JobSearchProvider.MODE);
            searchRecentSuggestions.saveRecentQuery(query, null);

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
