package com.mmitjobs.mmitjobs;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmitjobs.mmitjobs.adapter.TabAdapter;
import com.mmitjobs.mmitjobs.event.InternetConnectionEvent;
import com.mmitjobs.mmitjobs.event.JobsEvent;
import com.mmitjobs.mmitjobs.model.Job;
import com.mmitjobs.mmitjobs.provider.JobSearchProvider;
import com.mmitjobs.mmitjobs.util.Helper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView mConnectionTextView;
    FloatingActionButton fab;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mConnectionTextView = (TextView) findViewById(R.id.connection_textview);
        ConnectionUpdate(Helper.checkInterConnection(this));

        View header = navigationView.getHeaderView(0);

        TextView sign_inTextView = (TextView) header.findViewById(R.id.txt_sing_in);
        sign_inTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Jobs"));
        tabLayout.addTab(tabLayout.newTab().setText("Company"));
        tabLayout.addTab(tabLayout.newTab().setText("Nearby Jobs"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        final TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                changeFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    public void changeFab(int tab) {
        switch (tab) {
            case 0:
                fab.hide(new FloatingActionButton.OnVisibilityChangedListener(){
                    @Override
                    public void onHidden(FloatingActionButton fab) {
                        fab.show();
                    }
                });
                break;
            case 1:
                fab.hide(new FloatingActionButton.OnVisibilityChangedListener(){
                    @Override
                    public void onHidden(FloatingActionButton fab) {
                        fab.show();
                    }
                });
                break;
            case 2:
                fab.hide(new FloatingActionButton.OnVisibilityChangedListener(){
                    @Override
                    public void onHidden(FloatingActionButton fab) {
                        fab.show();
                    }
                });
                break;
            default:
                fab.hide();
                fab.show();
        }
    }

    private void ConnectionUpdate(boolean ConnectionExist) {
        if(ConnectionExist) {
            if(!mConnectionTextView.getText().toString().equals("Connected")) {
                mConnectionTextView.setText(R.string.InternetConnected);
                mConnectionTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
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
            mConnectionTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            mConnectionTextView.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void InternetConnectionUpdateEvent(InternetConnectionEvent event) {
        ConnectionUpdate(event.ConnectionExist);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        final CursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.suggestion_jobs, null, new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{R.id.suggestion_job_text},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        searchView.setSuggestionsAdapter(cursorAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor c = getSuggestions(newText);
                cursorAdapter.changeCursor(c);

                if (newText.isEmpty()){
                    List<Job> rjobs = MainApplication.realm.where(Job.class).findAll();
                    ArrayList<Job> mjobsList = new ArrayList<>(rjobs);
                    EventBus.getDefault().post(new JobsEvent(mjobsList));
                    return false;
                }

                if(newText.length() >= 1) {
                    List<Job> jobs = MainApplication.realm
                            .where(Job.class)
                            .contains("title", newText, Case.INSENSITIVE)
                            .findAll();
                    ArrayList<Job> mjobsList = new ArrayList<>(jobs);
                    EventBus.getDefault().post(new JobsEvent(mjobsList));
                }
                return false;
            }

        });

        // id of AutoCompleteTextView
        int searchEditTextId = R.id.search_src_text; // for AppCompat

        // get AutoCompleteTextView from SearchView
        final AutoCompleteTextView searchEditText = (AutoCompleteTextView) searchView.findViewById(searchEditTextId);
        final View dropDownAnchor = searchView.findViewById(searchEditText.getDropDownAnchor());
        if (dropDownAnchor != null) {
            dropDownAnchor.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {

                    // calculate width of DropdownView
                    int point[] = new int[2];
                    dropDownAnchor.getLocationOnScreen(point);
                    // x coordinate of DropDownView
                    int dropDownPadding = point[0] + searchEditText.getDropDownHorizontalOffset();

                    Rect screenSize = new Rect();
                    getWindowManager().getDefaultDisplay().getRectSize(screenSize);
                    // screen width
                    int screenWidth = screenSize.width();

                    // set DropDownView width
                    //searchEditText.setDropDownWidth(screenWidth - dropDownPadding * 2);
                    searchEditText.setDropDownWidth(screenWidth);
                }
            });
        }

        return true;
    }

    private Cursor getSuggestions(String newText) {
        Cursor c = null;
        ContentResolver contentResolver = getContentResolver();
        String strUri = "content://" + JobSearchProvider.AUTHORITY + "/" + SearchManager.SUGGEST_URI_PATH_QUERY;
        c = contentResolver.query(Uri.parse(strUri), null, null, new String[]{newText}, null);
        return c;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear_history) {
            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this, JobSearchProvider.AUTHORITY, JobSearchProvider.MODE);
            searchRecentSuggestions.clearHistory();
            Toast.makeText(this, "Cleared search history", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if(id == R.id.action_language) {
            Toast.makeText(this, "Language clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if(id == R.id.action_aboutus) {
            Toast.makeText(this, "About Us clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
