package com.headlessjs;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by panjiyudasetya on 6/8/17.
 */

public class LogEvent {
    private static final Gson GSON = new Gson();

    private String name;
    private String location;
    private String extra;
    private String timestamp;

    public LogEvent(String name, String location, String extra) {
        this.name = name;
        this.location = location;
        this.extra = extra;
        this.timestamp = DateFormat.getDateTimeInstance().format(new Date());
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
