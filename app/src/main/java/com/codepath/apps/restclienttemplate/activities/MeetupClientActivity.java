package com.codepath.apps.restclienttemplate.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.MeetupEvent;
import com.codepath.apps.restclienttemplate.models.MeetupEventUser;
import com.codepath.apps.restclienttemplate.models.MeetupUser;
import com.codepath.apps.restclienttemplate.restclients.MeetupClient;
import com.codepath.apps.restclienttemplate.restclients.RestApplication;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

public class MeetupClientActivity extends ActionBarActivity {

    MeetupClient meetupClient;
    MeetupUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetup_client);
        meetupClient = RestApplication.getMeetupClient();
        getMeetupEvents();
        //220510259
        //getRSVPUsers("220510259");
        getCurrentUserInfo();
        getEventList();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meetup_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getMeetupEvents() {
        meetupClient.getOpenEvents("android", "94040", 25.00, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               ArrayList<MeetupEvent> events =  MeetupEvent.fromJSONArray(response);
               Log.d("debug", events.toString());
               for (MeetupEvent event : events) {
                   getRSVPUsers(event.getEventId());
               }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void getRSVPUsers(String eventId) {
        meetupClient.getrsvpMembers(eventId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ArrayList<MeetupEventUser> users = MeetupEventUser.fromJSONArray(response);
                Log.d("debug", users.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void getCurrentUserInfo() {
        meetupClient.getMemberInfo("self", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentUser = MeetupUser.fromJsonObject(response);
                Log.d("debug", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void getEventList() {
        meetupClient.getEventsForMember("self", "yes", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ArrayList<MeetupEvent> events = MeetupEvent.fromJSONArray(response);
                Log.d("debug", events.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}
