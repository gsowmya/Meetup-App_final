package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.MeetupEvent;

import java.util.List;

/**
 * Take the Tweet objects and turn them into views displayed in the list
 */
public class MeetupsArrayAdapter extends ArrayAdapter<MeetupEvent> {
    public MeetupsArrayAdapter(Context context, List<MeetupEvent> meetups) {
        super(context, R.layout.item_meetups, meetups);
    }

    // Override and setup custom template
    // ViewHolder pattern: http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Get the tweet
        MeetupEvent event = getItem(position);
        // 2. Find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_meetups, parent, false);
        }
        // 3. Find the subviews to fill with data in the template


        TextView tvEventTitle = (TextView) convertView.findViewById(R.id.tvEventTitle);
        TextView tvEventDescription = (TextView) convertView.findViewById(R.id.tvEventDescription);
        // 4. Populate data into the subviews
        tvEventTitle.setText(event.getEventTitle());
        /*if(event.getEventDescription() != null && !event.getEventDescription().isEmpty())
            tvEventDescription.setText(Html.fromHtml(event.getEventDescription()).toString());*/
        // 5. Return the view to be inserted into the list
        return convertView;
    }
}
