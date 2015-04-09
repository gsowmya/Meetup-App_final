package com.codepath.apps.restclienttemplate.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.activities.EventDetailsActivity;
import com.codepath.apps.restclienttemplate.adapters.MeetupsArrayAdapter;
import com.codepath.apps.restclienttemplate.models.MeetupEvent;
import com.codepath.apps.restclienttemplate.restclients.MeetupClient;
import com.codepath.apps.restclienttemplate.restclients.RestApplication;

import java.util.ArrayList;

/**
 * Created by loudl_000 on 3/16/2015.
 */
public abstract class EventFragment extends Fragment {
    protected ArrayList<MeetupEvent> _meetups;
    protected MeetupsArrayAdapter _aMeetups;
    protected ListView _lvTweets;
    protected MeetupClient meetupClient;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_meetups_list, parent, false);
        // Find the listview
        _lvTweets = (ListView) v.findViewById(R.id.lvMeetups);
      _lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               MeetupEvent event = (MeetupEvent) parent.getItemAtPosition(position);
               Intent i = new Intent(getActivity(),EventDetailsActivity.class);
               i.putExtra("Event",event);
               startActivity(i);
          }
      });
        // Connect adapter to listview
        _lvTweets.setAdapter(_aMeetups);
        return v;
    }

    // Creation lifecycle event
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ArrayList from data source
        _meetups = new ArrayList<>();
        // Construct the adapter from data source
        _aMeetups = new MeetupsArrayAdapter(getActivity(), _meetups);
        meetupClient = RestApplication.getMeetupClient();
        getMeetupEvents();
    }




    protected abstract void getMeetupEvents();
    /*public void addAll(List<SampleModel> meetups) {
        _aMeetups.addAll(meetups);
    }*/
}
