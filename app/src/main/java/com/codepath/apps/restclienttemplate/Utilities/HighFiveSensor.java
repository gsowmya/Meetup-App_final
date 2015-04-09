package com.codepath.apps.restclienttemplate.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.data.ParseDB;

/**
 * Created by moulib on 3/29/15.
 */
public class HighFiveSensor {

    Context highFiveContext;

    ParseDB pDB = ParseDB.getInstance();

    private final int MAX_USERS_TO_CONNECT = 5;             /* Number of users to follow at once */
    private final long USER_CONNECT_THRESHOLD_TIME = 3000;  /* Milliseconds */

    private SensorManager mSensorManager;
    private Sensor mAccel;

    private boolean mInitialized = false;
    private float mLastX, mLastY, mLastZ;
    private final float MOVEMENT_THRESHOLD = (float) 9.0;   /* Relative movement tolerance */

    private static long lastPost = 0;
    private final long SENSE_THRESHOLD = 100; /* in Milliseconds */


    private SensorEventListener mAccelSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (highFiveDetected(event)) {
                pDB.postAHighFive();
                updateLastPost();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d("HighFiveSensor", sensor.toString() + " - " + accuracy);
        }
    };

    public void resumeHighFiveSensing() {
        if (mAccel != null) {
            mSensorManager.registerListener(mAccelSensorListener, mAccel,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void pauseHighFiveSensing() {
        if (mAccel != null) {
            mSensorManager.unregisterListener(mAccelSensorListener);
        }
    }

    private void updateLastPost() {
        lastPost = System.currentTimeMillis();
    }

    private boolean tooQuick() {
        long currtime = System.currentTimeMillis();

        if (currtime - lastPost > SENSE_THRESHOLD) {
            return false;
        } else {
            //Log.d("S_DBG", "Too quick");
            return true;
        }
    }

    private boolean highFiveDetected(SensorEvent event) {

        if (tooQuick()) {
            // Too quick - don't bother processing the event
            return false;
        }

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            mInitialized = true;
        } else {
            float deltaX = (mLastX - x);
            float deltaY = (mLastY - y);
            float deltaZ = (mLastZ - z);
            if (deltaX < MOVEMENT_THRESHOLD) deltaX = (float) 0.0;
            if (deltaY < MOVEMENT_THRESHOLD) deltaY = (float) 0.0;
            if (deltaZ < MOVEMENT_THRESHOLD) deltaZ = (float) 0.0;
            mLastX = x;
            mLastY = y;
            mLastZ = z;

            if ((deltaX > MOVEMENT_THRESHOLD) && (deltaX > deltaY) && (deltaX > deltaZ)) {
                //Log.d("HighFiveSensor", event.toString() + " horizontal move");
                Toast.makeText(highFiveContext, "Hand Shake Detected", Toast.LENGTH_SHORT).show();
                return true;
            } else if ((deltaY > MOVEMENT_THRESHOLD) && (deltaY > deltaX) && (deltaY > deltaZ)) {
                //Log.d("HighFiveSensor", event.toString() + " vertical move");
                Toast.makeText(highFiveContext, "High Five Detected", Toast.LENGTH_SHORT).show();
                return true;
            } else if ((deltaZ > MOVEMENT_THRESHOLD) && (deltaZ > deltaX) && (deltaZ > deltaY)) {
                //Log.d("HighFiveSensor", event.toString() + " vertical move");
                Toast.makeText(highFiveContext, "Hand Wave Detected", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }


    public HighFiveSensor(Context hContext) {

        this.highFiveContext = hContext;

        pDB.setSenseParams(USER_CONNECT_THRESHOLD_TIME, MAX_USERS_TO_CONNECT);

        // Get sensor manager
        mSensorManager = (SensorManager)highFiveContext.getSystemService(Context.SENSOR_SERVICE);
        // Get the default sensor of specified type
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mAccel != null) {
            mSensorManager.registerListener(mAccelSensorListener, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}


