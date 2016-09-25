package com.mmitjobs.mmitjobs.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by pyaehein on 9/9/16.
 */
public class JobSearchProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = ".provider.JobSearchProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public JobSearchProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
