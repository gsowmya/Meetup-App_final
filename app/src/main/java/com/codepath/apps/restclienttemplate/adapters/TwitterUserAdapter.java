package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.TwitterUser;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TwitterUserAdapter extends ArrayAdapter<TwitterUser> {

    public TwitterUserAdapter(Context context, List<TwitterUser> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TweetViewHolder holder;
        TwitterUser tweet = getItem(position);
        if (convertView == null) {
            holder = new TweetViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtScreenName = (TextView) convertView.findViewById(R.id.txtScreenName);
            holder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfilePic);

            convertView.setTag(holder);
        } else {
            holder = (TweetViewHolder) convertView.getTag();
        }

        holder.txtName.setText(tweet.getUserName());
        holder.txtScreenName.setText(tweet.getScreenName());
        holder.imgProfile.setImageResource(0);
        Picasso.with(getContext()).load(tweet.getProfilePic()).into(holder.imgProfile);

        return convertView;
    }



    class TweetViewHolder {
        public TextView txtName;
        public ImageView imgProfile;
        public TextView txtScreenName;
    }
}
