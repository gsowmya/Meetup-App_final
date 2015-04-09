package com.codepath.apps.restclienttemplate.models;

import org.json.JSONObject;

/**
 * Created by amundada on 3/28/15.
 */
public class MeetupUser {
    private String meetupId;
    private String name;
    private String city;
    private String lat;
    private String lon;
    private String pictureUrl;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getMeetupId() {
        return meetupId;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public static MeetupUser fromJsonObject(JSONObject json) {
        final MeetupUser user = new MeetupUser();
        try {
            user.meetupId = json.getString("id");
            if (json.has("city"))
                user.city = json.getString("city") ;

            if (json.has("lat") && json.has("lon")) {
                user.lat = json.getString("lat");
                user.lon = json.getString("lon");
            }
            user.name = json.getString("name");
            if (json.has("photo")) {
                JSONObject photo = json.getJSONObject("photo");
                user.pictureUrl = photo.getString("thumb_link");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
