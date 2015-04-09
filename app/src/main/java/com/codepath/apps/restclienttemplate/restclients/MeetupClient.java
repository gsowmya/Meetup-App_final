package com.codepath.apps.restclienttemplate.restclients;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.MeetupApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class MeetupClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = MeetupApi.class; // Change this
	public static final String REST_URL = "https://api.meetup.com"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "14u3eqhq7l3q0sgqvvrj27fquk";       // Change this
	public static final String REST_CONSUMER_SECRET = "dbrt91pdr2lobj2ehfmoqadgi5"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://meetup"; // Change this (here and in manifest)

	public MeetupClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

    // Get List of all events with a specific topic, zipcode, radius
    public void getOpenEvents(String topic, String zipCode, double radius, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("2/open_events");

        RequestParams params = new RequestParams();
        params.put("sign", true);
        params.put("photo-host", "public");
        params.put("page",20);
        if(topic.isEmpty())
            topic = "Android";

        params.put("topic", topic);
        if(zipCode.isEmpty())
           zipCode = "94040";

        params.put("zip", zipCode);
        params.put("radius", radius);
        client.get(apiUrl, params, handler);
    }

    // Get List of all events with a specific topic, zipcode, radius
    public void getrsvpMembers(String eventId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("2/rsvps");

        RequestParams params = new RequestParams();
        params.put("sign", true);
        params.put("photo-host", "public");
        params.put("page",20);
        params.put("event_id", eventId);
        client.get(apiUrl, params, handler);
    }

    public void getMemberInfo(String userId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("2/member/" + userId);

        RequestParams params = new RequestParams();
        params.put("sign", true);
        params.put("photo-host", "public");
        params.put("page",20);
        client.get(apiUrl, params, handler);
    }

    public void getEventsForMember(String userId, String RSVPStatus, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("2/events/");

        RequestParams params = new RequestParams();
        params.put("sign", true);
        params.put("photo-host", "public");
        params.put("page",20);
        params.put("member_id", userId);
        params.put("rsvp", RSVPStatus);
        client.get(apiUrl, params, handler);
    }

    
}