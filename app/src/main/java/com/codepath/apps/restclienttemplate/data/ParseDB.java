package com.codepath.apps.restclienttemplate.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.CurrentUserInfo;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.restclients.RestApplication;
import com.codepath.apps.restclienttemplate.restclients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Felix on 3/28/2015.
 *
 * Singleton class
 */
public class ParseDB {

    private static ParseDB parseDB = null;
    protected ParseDB() {
        // Dummy constructor for singleton
    }

    private Context pDbCtx;
    private long connect_threshold;
    private int max_users;

    private TwitterClient twitterClient = RestApplication.getTwitterClient();
    private CurrentUserInfo userinfo = CurrentUserInfo.getInstance();

    public static ParseDB getInstance() {
        if (parseDB == null) {
            parseDB = new ParseDB();
        }

        return parseDB;
    }

    public void setContext(Context pCtx) {
        pDbCtx = pCtx;
    }

    public void setSenseParams(long thresh, int mUsers) {
        connect_threshold = thresh;
        max_users = mUsers;
    }

    public String addTimeStamp (String meetupId, Long ts) {
        String lastMeetupId = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserIdPair");
        try {
            ParseObject parseObj = query.get("34Jvr1VMDh");
            String line = parseObj.getString("timestamp");
            if (line != null) {
                String[] a = line.split(" ");
                Long lastTimeStamp = Long.parseLong(a[0]);
                if (ts - lastTimeStamp < connect_threshold) {
                    lastMeetupId = a[1];
                }
            }
            parseObj.put("timestamp", "" + ts + " " + meetupId);
            parseObj.save();
        } catch (ParseException e) {
            e.getStackTrace();
        }

        return lastMeetupId;
    }

    public void addUser (String meetupId, String twitterId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserIdPair");
        try {
            ParseObject parseObj = query.get("bIeSfOACpJ");
            parseObj.put(meetupId, twitterId);
            parseObj.save();
        } catch (ParseException e) {
            e.getStackTrace();
        }
    }

    public String queryByMeetupId (String meetupId) {
        // ctDbg = context;
        String twitterId = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserIdPair");
        try {
            ParseObject parseObj = query.get("bIeSfOACpJ");
            twitterId = parseObj.getString(meetupId);
        } catch (ParseException e) {
            e.getStackTrace();
        }

        return twitterId;
    }


    // XXX - can we move this out of the DB code - cleanup needed!
    private void followUserOnTwitter(final String userId) {
        twitterClient.followUserOnTwitter(userId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //followed user on twitter
                Toast.makeText(pDbCtx, "following " + userId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Q_ERR", "Failed to follow user - " + userId);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private boolean checkTimestampGranularity(long ts1, long ts2) {
        if (Math.abs(ts1 - ts2) <= connect_threshold) {
            return true;
        }
        return false;
    }

    // Query message from Parse and connect with them!
    public void queryAndConnectUsers() {
        //Construct a query
        ParseQuery<User> query = ParseQuery.getQuery(User.class);

        // Configure limits and sort criteria
        query.setLimit(max_users);
        query.orderByDescending("timestamp");
        query.whereNotEqualTo("twitterId", userinfo.getUserTwitterId());

        //Run the query to fetch the last few users!
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> potUsers, ParseException e) {
                if (e == null) {
                    for (int index = 0; index < potUsers.size(); index++) {
                        User potUser = potUsers.get(index);
                        if (checkTimestampGranularity(potUser.getTimestamp(), userinfo.getTimestamp())) {
                            Log.d("Q_DBG", "Following user" + potUser.getTwitterId());
                            followUserOnTwitter(potUser.getTwitterId());
                            //makeConnections("43526264");
                        }
                    }
                } else {
                    Log.d("Q_ERROR", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void updateTsSaveUser(User pUser) {

        // Reset the twitter Id - as this code is common for new and existing users!
        pUser.setTwitterId(userinfo.getUserTwitterId());

        final long unixCurrTime = System.currentTimeMillis();
        // Save TS for current user - so we can compare if any other user had the same time
        userinfo.setTimestamp(unixCurrTime);
        pUser.setTimestamp(unixCurrTime);
        pUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.d("Q_DEBUG", "Successfully created message on Parse");
                // Connect if anyone else posted a high five
                queryAndConnectUsers();
            }
        });
    }

    public void postAHighFive() {

        ParseQuery<User> query = ParseQuery.getQuery(User.class);

        /* Find the user with the current userId */
        query.whereEqualTo("twitterId", userinfo.getUserTwitterId());

        Log.d("Q_DEBUG", "preparing query");

        //There should only be one instance of this record!!! XXX ---- query.setLimit(1);
        query.getFirstInBackground(new GetCallback<User>() {
            @Override
            public void done(User user, ParseException e) {
                User potUser;
                if ((user == null) ||
                    ((e!= null) && (e.getCode() == ParseException.OBJECT_NOT_FOUND))) {
                    // Create new user
                    potUser = new User();
                } else {
                    potUser = user;
                }
                updateTsSaveUser(potUser);
            }
        });

        return;
    }
}
