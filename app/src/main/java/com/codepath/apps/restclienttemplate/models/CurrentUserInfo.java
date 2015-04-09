package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sowmya on 3/7/15.
 *
 * This is the current user in the app.
 *     - declared singleton
 *     - use the getInstance to grab the user
 */
public class CurrentUserInfo {

    private static CurrentUserInfo currentUser = new CurrentUserInfo();

    private CurrentUserInfo() {
    }

    public static CurrentUserInfo getInstance() {
        return currentUser;
    }

    /* Twitter Info */
    String userName;
    String screenName;
    String profileImage;
    String userTwitterId;

    /* Meetup Info - TBD - Anand to add */


    /* Sensor info */
    long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /* Twitter Info Getters */
    public String getUserName() {
        return userName;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getUserTwitterId() {
        return userTwitterId;
    }


    public static void updateTwitterCurrentUserfromJSON(JSONObject jsonObject){
        try {
            currentUser.userName = jsonObject.getString("name").toString();
            currentUser.screenName = jsonObject.getString("screen_name").toString();
            currentUser.profileImage = jsonObject.getString("profile_image_url").toString();
            currentUser.userTwitterId = jsonObject.getString("id").toString();
            currentUser.timestamp = 0;
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}


