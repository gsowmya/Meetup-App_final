package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by amundada on 3/25/15.
 */
public class MeetupEvent implements Serializable {
    private String eventId;
    private String eventTitle;
    private String eventDate;
    private String eventStartTime;
    private String eventDuration;
    private String eventDescription;

    public MeetupEvent() {}

    public String getEventId() {
        return eventId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventDuration() {
        return eventDuration;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    private static String getDate(Long epoch) {
        Date date = new Date(epoch);

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = formatter.format(date);
        return dateString;
    }

    private static String getTime(Long epoch) {
        Date date = new Date(epoch);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        String time = formatter.format(date);
        return time;
    }

    public static MeetupEvent fromJSON(JSONObject json) {

        final MeetupEvent event = new MeetupEvent();
        try {
            event.eventId = json.getString("id");
            event.eventDuration = json.getString("duration");
            event.eventTitle = json.getString("name");
            event.eventDescription = json.getString("description");
            event.eventDate = getDate(json.getLong("time"));
            event.eventStartTime = getTime(json.getLong("time"));

           //TODO: Get Venue also
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return event;
    }



    public static ArrayList<MeetupEvent> fromJSONArray(JSONObject jsonObject) {
        ArrayList<MeetupEvent> events = new ArrayList<MeetupEvent>();
        try {
            JSONArray resultArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject json = resultArray.getJSONObject(i);
                MeetupEvent event = fromJSON(json);
                if(event != null && event.getEventTitle() != null && !event.getEventDescription().isEmpty()) {
                    events.add(event);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return events;
    }




}
