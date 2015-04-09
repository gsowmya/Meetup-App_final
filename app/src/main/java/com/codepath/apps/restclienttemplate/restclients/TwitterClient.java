package com.codepath.apps.restclienttemplate.restclients;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

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
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "TJ4EB9WX9mCLAZ9rLxjhGBvPu";       // Change this
    public static final String REST_CONSUMER_SECRET = "jcW40RjfWIEMLOgq5DNCP0emakUz2dfuJN55EgDOq9mQcY3bPK"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://meettweets"; // Change this (here and in manifest)

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        getClient().get(apiUrl, params, handler);
    }

    public void getCurrentUserInfo(AsyncHttpResponseHandler handler) {
        String apiurl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiurl,handler);
    }

    public void getProfileInfoForUser(String userId, AsyncHttpResponseHandler handler) {
        String url = "https://api.twitter.com/1.1/users/show.json?user_id="+ userId;
        getClient().get(url, handler);
    }

    public void followUserOnTwitter(String userId, AsyncHttpResponseHandler handler) {
        String url = "https://api.twitter.com/1.1/friendships/create.json?user_id="+userId+"&follow=true";
        getClient().post(url, handler);
    }

    public void getFriendsList(AsyncHttpResponseHandler handler) {
        String url = "https://api.twitter.com/1.1/friends/ids.json?count=200";
        getClient().get(url, handler);
    }

}