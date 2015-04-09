package com.codepath.apps.restclienttemplate.models;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Sowmya on 3/25/15.
 */
public class TwitterUser {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getScreenName() {
        return profilePic;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String userName;
    public String profilePic;
    public String screenName;

    public TwitterUser(JSONObject jsonObject){
        try {
            this.userName = jsonObject.getString("name");
            this.profilePic = jsonObject.getString("profile_image_url");
            this.screenName = jsonObject.getString("screen_name");
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}

