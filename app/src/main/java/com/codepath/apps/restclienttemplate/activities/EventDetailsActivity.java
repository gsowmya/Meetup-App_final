package com.codepath.apps.restclienttemplate.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.adapters.EventDetailsAdapter;
import com.codepath.apps.restclienttemplate.models.MeetupEvent;
import com.codepath.apps.restclienttemplate.models.MeetupEventUser;
import com.codepath.apps.restclienttemplate.models.TwitterUser;
import com.codepath.apps.restclienttemplate.restclients.RestApplication;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventDetailsActivity extends ActionBarActivity {

    TextView title,date,description,startTime,duration,address;
    ListView lstView;
    EventDetailsAdapter adapter = null;
    List<MeetupEventUser> users= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        MeetupEvent event = (MeetupEvent) getIntent().getSerializableExtra("Event");
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);
        startTime = (TextView) findViewById(R.id.startTime);
        lstView = (ListView) findViewById(R.id.lstView);
        setLabels(event);
        getRSVPUsers(event.getEventId());

    }

    private void setLabels(MeetupEvent event){
        title.setText(event.getEventTitle());
        date.setText(event.getEventDate());
        description.setText(Html.fromHtml(event.getEventDescription()));
        startTime.setText("Start Time: "+event.getEventStartTime());

    }

    private void getRSVPUsers(String eventId) {
        RestApplication.getMeetupClient().getrsvpMembers(eventId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                users = MeetupEventUser.fromJSONArray(response);
                adapter = new EventDetailsAdapter(getApplicationContext(), users);
                lstView.setAdapter(adapter);
                Log.d("seuccess", users.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("failed", String.valueOf(statusCode));
            }
        });
    }


}
