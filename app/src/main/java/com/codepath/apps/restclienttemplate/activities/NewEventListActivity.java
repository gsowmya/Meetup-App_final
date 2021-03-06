package com.codepath.apps.restclienttemplate.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.Utilities.HighFiveSensor;
import com.codepath.apps.restclienttemplate.adapters.MeetupsArrayAdapter;
import com.codepath.apps.restclienttemplate.data.ParseDB;
import com.codepath.apps.restclienttemplate.fragments.MeetupsFragment;
import com.codepath.apps.restclienttemplate.fragments.RSVPsFragment;
import com.codepath.apps.restclienttemplate.models.CurrentUserInfo;
import com.codepath.apps.restclienttemplate.restclients.RestApplication;
import com.codepath.apps.restclienttemplate.restclients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.apache.http.Header;
import org.json.JSONObject;


public class NewEventListActivity extends ActionBarActivity {
    private static final String TAG = MeetupLoginActivity.class.getName();
    private static String sUserId;
    public static final String USER_ID_KEY = "userId";

    public ParseDB parseDB;
    private HighFiveSensor highFiveSensor;
    private TwitterClient twitterClient = RestApplication.getTwitterClient();
    private CurrentUserInfo userInfo = CurrentUserInfo.getInstance();

    private ViewPager vpPager;
   // private MeetupsPagerAdapter pagerAdapter;
    private void getCurrentUserTwitterInfo() {
        twitterClient.getCurrentUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                userInfo.updateTwitterCurrentUserfromJSON(response);
                Log.d("USER_DEBUG","Twitter User " + userInfo.getUserTwitterId());
                //Toast.makeText(getApplicationContext(), "UserID " + userInfo.getUserTwitterId() + userInfo.getUserName(), Toast.LENGTH_SHORT).show();

                /* User info obtained - start sensing */
                highFiveSensor.resumeHighFiveSensing();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("USER_ERR", "Could not obtain twitter id");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        highFiveSensor.resumeHighFiveSensing();
    }

    @Override
    protected void onPause() {
        super.onPause();
        highFiveSensor.pauseHighFiveSensing();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        /* Enable the sensors */
        highFiveSensor = new HighFiveSensor(getApplicationContext());

        /* Pause Sensing till user details are determined */
        highFiveSensor.pauseHighFiveSensing();

        // Get Current User for Twitter
        getCurrentUserTwitterInfo();

        login();

        parseDB = ParseDB.getInstance();
        parseDB.setContext(getApplicationContext());

//        // Get the ViewPager
//        vpPager = (ViewPager) findViewById(R.id.viewpager);
//        // Set the ViewPager adapter for the pager
//        pagerAdapter = new MeetupsPagerAdapter(getSupportFragmentManager());
//        vpPager.setAdapter(pagerAdapter);
        // Find the pager sliding tabs
       // PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        // Attach the tabstrip to the ViewPager
       // tabStrip.setViewPager(vpPager);

        initActionBar();
    }

    @SuppressWarnings("deprecation")
    private void initActionBar() {
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab()
                    .setText("Events")
                    .setTabListener(new ActionBar.TabListener() {
                        @Override
                        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            fragmentTransaction.replace(android.R.id.content, new MeetupsFragment());
                        }

                        @Override
                        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }

                        @Override
                        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }
                    }));
            actionBar.addTab(actionBar.newTab()
                    .setText("RSVP")
                    .setTabListener(new ActionBar.TabListener() {
                        @Override
                        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            fragmentTransaction.replace(android.R.id.content, new RSVPsFragment());
                        }

                        @Override
                        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }

                        @Override
                        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                        }
                    }));
        }
    }

    // Get the userId from the cached currentUser object
    public void startWithCurrentUser() {
        sUserId = ParseUser.getCurrentUser().getObjectId();
    }

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    public void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Anonymous login failed: " + e.toString());
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_filters) {
            Intent i = new Intent(this, FilterActivity.class);
            startActivityForResult(i, RESULT_OK);

        }
        return super.onOptionsItemSelected(item);
    }


//    // Return the order of the fragments in the view pager
//    public class MeetupsPagerAdapter extends FragmentPagerAdapter {
//        private String _tabTitles[] = {getString(R.string.meetups_tab), getString(R.string.rsvps_tab)};
//
//        // Adapter gets the manager to insert or remove fragments from the activity
//        public MeetupsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        // Controls the order and creation of fragments within the pager
//        @Override
//        public Fragment getItem(int position) {
//            if (position == 0) {
//                return new MeetupsFragment();
//            } else if (position == 1) {
//                return new RSVPsFragment();
//            } else {
//                return null;
//            }
//        }
//
//        // Return the tab title
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return _tabTitles[position];
//        }
//
//        // Returns how many fragments there are to swipe between
//        @Override
//        public int getCount() {
//            return _tabTitles.length;
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

}




