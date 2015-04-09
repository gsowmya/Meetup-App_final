package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.MeetupEventUser;
import com.codepath.apps.restclienttemplate.restclients.RestApplication;
import com.codepath.apps.restclienttemplate.restclients.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sowmya on 3/31/15.
 */
public class EventDetailsAdapter extends ArrayAdapter<MeetupEventUser> {

    private TwitterClient twitterClient;
    ArrayList<String> friendList;
    public EventDetailsAdapter(Context context, List<MeetupEventUser> users) {
        super(context, android.R.layout.simple_list_item_1, users);
        twitterClient = RestApplication.getTwitterClient();
        friendList = null;
        twitterClient.getFriendsList(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray arr = response.getJSONArray("ids");
                    friendList = new ArrayList<String>();
                    for (int i = 0; i < arr.length(); i++) {
                        String id = arr.getString(i);
                        friendList.add(id);
                    }
                    Log.d("debug:", "Got Friend List");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MeetupUserViewHolder holder;
        final MeetupEventUser user = getItem(position);
        if (convertView == null) {
            holder = new MeetupUserViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.meetup_user, parent, false);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.follow = (ImageButton) convertView.findViewById(R.id.ivTwitterFollow);
            holder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfilePic);

            convertView.setTag(holder);
        } else {
            holder = (MeetupUserViewHolder) convertView.getTag();
        }

        holder.txtName.setText(user.getName());
        holder.imgProfile.setImageResource(0);
        Picasso.with(getContext()).load(user.getPictureUrl()).into(holder.imgProfile);
        holder.follow.setImageResource(0);
        if (friendList == null || !friendList.contains(user.getMeetupId())) {
            Picasso.with(getContext()).load(R.drawable.follow).resize(200, 75).into(holder.follow);
        } else {
            Picasso.with(getContext()).load(R.drawable.bird).resize(100, 75).into(holder.follow);
        }

        holder.follow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //get twitter id using meetup id --> getMeetupId
                String twitterId = user.getMeetupId();
                if (friendList == null || !friendList.contains(twitterId)) {
                    twitterClient.followUserOnTwitter(twitterId, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d("Follow", "Followed user on twitter");
                            Toast.makeText(getContext(), "Following User", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Already Following", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }


    class MeetupUserViewHolder {
        public TextView txtName;
        public ImageView imgProfile;
        public ImageButton follow;
    }
}