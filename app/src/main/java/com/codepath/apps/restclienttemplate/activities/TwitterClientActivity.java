package com.codepath.apps.restclienttemplate.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.adapters.TwitterUserAdapter;
import com.codepath.apps.restclienttemplate.models.CurrentUserInfo;
import com.codepath.apps.restclienttemplate.models.TwitterUser;
import com.codepath.apps.restclienttemplate.restclients.RestApplication;
import com.codepath.apps.restclienttemplate.restclients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TwitterClientActivity extends Activity {

    private TwitterClient twitterClient;
    private ListView lstView;
    private TwitterUserAdapter adapter;
    private List<TwitterUser> users = new ArrayList<TwitterUser>();
    String userId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_client);
        twitterClient = RestApplication.getTwitterClient();
        lstView = (ListView) findViewById(R.id.lstView);
        lstView.setAdapter(adapter);

        // get full list of all users
//        getProfileInfo(userId);
//        populateUserInfo();

        //followUserOnTwitter("43526264");
    }



    private void populateUserInfo() {
        adapter.addAll(users);
    }

    private void getProfileInfo(String userId) {
        twitterClient.getProfileInfoForUser(userId,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                TwitterUser user = new TwitterUser(response);
                users.add(user);
            }
        });
    }



}
