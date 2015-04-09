package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amundada on 3/25/15.
 */
public class MeetupEventUser {
    private String meetupId;
    private String pictureUrl;
    private String name;

    public String getMeetupId() {
        return meetupId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getName() {
        return name;
    }

    public static MeetupEventUser fromJSON(JSONObject json) {

        final MeetupEventUser meetupUser = new MeetupEventUser();
        try {
            JSONObject member = json.getJSONObject("member");
            meetupUser.meetupId = member.getString("member_id");
            meetupUser.name = member.getString("name");
            if(json.has("member_photo")) {
                JSONObject memberPic = json.getJSONObject("member_photo");
                meetupUser.pictureUrl = memberPic.getString("thumb_link");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return meetupUser;
    }

    // this will return list of all users who rsvp to a specific event
    public static ArrayList<MeetupEventUser> fromJSONArray(JSONObject jsonObject) {

        ArrayList<MeetupEventUser> users = new ArrayList<MeetupEventUser>();
        try {
            JSONArray resultArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject json = resultArray.getJSONObject(i);
                MeetupEventUser user = fromJSON(json);
                if(user != null) {
                    users.add(user);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }



}
