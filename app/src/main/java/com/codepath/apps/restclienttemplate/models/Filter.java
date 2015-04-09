package com.codepath.apps.restclienttemplate.models;

/**
 * Created by amundada on 4/1/15.
 */
public class Filter {

    private String topic;
    private String zipCode;
    private String radius;

    private Filter() {
        topic = "";
        zipCode = "";
        radius = "25";
    }

    private static Filter filter = null;

    public String getRadius() {
        return radius;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getTopic() {
        return topic;
    }

    public static Filter getInstance() {
        if (filter == null) {
            filter = new Filter();
        }
        return filter;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
