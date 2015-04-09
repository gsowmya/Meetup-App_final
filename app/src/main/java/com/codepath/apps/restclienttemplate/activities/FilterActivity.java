package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Filter;

public class FilterActivity extends ActionBarActivity {

    private EditText etTopic;
    private EditText etZip;
    private EditText etDistance;
    private Filter filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        etTopic = (EditText) findViewById(R.id.etTopic);
        etZip = (EditText) findViewById(R.id.etZip);
        etDistance = (EditText) findViewById(R.id.etDistance);
        filter = Filter.getInstance();

        etTopic.setText(filter.getTopic());
        etZip.setText(filter.getZipCode());
        etDistance.setText(filter.getRadius());
    }

    public void onSetFilter(View view) {
        String topic = etTopic.getText().toString();
        String zip = etZip.getText().toString();
        String distance = etDistance.getText().toString();

        filter.setRadius(distance);
        filter.setTopic(topic);
        filter.setZipCode(zip);

        // Prepare data intent
        /*Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("topic", etTopic.getText().toString());
        data.putExtra("zip", etZip.getText().toString());
        data.putExtra("distance", etDistance.getText().toString());
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response*/
        //finish(); // closes the activity, pass data to parent
        Intent i = new Intent(this, EventListActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
