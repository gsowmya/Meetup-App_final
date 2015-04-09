package com.codepath.apps.restclienttemplate.fragments;

import com.codepath.apps.restclienttemplate.models.MeetupEvent;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by loudl_000 on 3/16/2015.
 */
public class RSVPsFragment extends EventFragment {

    protected void getMeetupEvents() {

        meetupClient.getEventsForMember("self", "yes", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                _aMeetups.addAll(MeetupEvent.fromJSONArray(response));
                //Log.d("debug", events.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
