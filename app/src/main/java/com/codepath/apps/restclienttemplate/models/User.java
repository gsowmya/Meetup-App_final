package com.codepath.apps.restclienttemplate.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by moulib on 3/25/15.
 */
@ParseClassName("User")
public class User extends ParseObject {
    // Define table fields
    private String meetupId;

    private long twitterId;

    private long timestamp;

    public String getMeetupId() {
        return getString("meetupId");
    }

    public String getTwitterId() {
        return getString("twitterId");
    }

    public long getTimestamp() {
        return getLong("timestamp");
    }

    public void setMeetupId(String meetupId) {
        put("meetupId", meetupId);
    }

    public void setTwitterId(String twitterId) {
        put("twitterId", twitterId);
    }

    public void setTimestamp(long timestamp) {
        put("timestamp", timestamp);
    }

}
