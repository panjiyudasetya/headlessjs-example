package com.headlessjs;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panjiyudasetya on 6/8/17.
 */

public class DataLogger {
    private static final String DATA_KEY = "DATA";

    private DataLogger() { }

    public static List<String> getEvents() {
        return Hawk.get(DATA_KEY, new ArrayList<String>());
    }

    public static void saveEvent(String event) {
        List<String> data = getEvents();
        if (data.size() == 0) data.add(event);
        else data.add(data.size(), event);
        Hawk.put(DATA_KEY, data);
    }
}
