package com.codepath.apps.restclienttemplate.restclients;

import android.content.Context;
import android.os.Message;

import com.codepath.apps.restclienttemplate.models.User;
import com.parse.Parse;
import com.parse.ParseObject;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     MeetupClient client = RestApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class RestApplication extends com.activeandroid.app.Application {
	private static Context context;

    final String MOULI_APP_ID = "WnBZ7TSRZIKHgoKXKNuokOoax3mzodQh84My7UuI";
    final String MOULI_CLIENT_KEY = "2aLhOft12ZdEdQniKq0TJVj5jeuyb73lI2Xj7kBM";

    final String APP_ID = "krhTThxiLSjT6xAmU0YcN4bHaK1G3SGjPMYvVusV";
    final String CLIENT_KEY = "94A4GUZYX333xriq3O9fJmp5ka0bGVluXhdI31yt";

	@Override
	public void onCreate() {
		super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(User.class);
        Parse.initialize(this, MOULI_APP_ID, MOULI_CLIENT_KEY);

		RestApplication.context = this;
	}

	public static MeetupClient getMeetupClient() {
		return (MeetupClient) MeetupClient.getInstance(MeetupClient.class, RestApplication.context);
	}

    public static TwitterClient getTwitterClient() {
        return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, RestApplication.context);
    }
}