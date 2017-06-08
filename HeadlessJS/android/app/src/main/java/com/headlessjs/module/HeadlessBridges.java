package com.headlessjs.module;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;
import com.headlessjs.DataLogger;
import com.headlessjs.service.HeadlessTaskService;

/**
 * Created by panjiyudasetya on 6/7/17.
 */

public class HeadlessBridges extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "HeadlessJsModule";
    private static final String TAG = MODULE_NAME;
    private static final Gson GSON = new Gson();
    private Context mContext;

    public HeadlessBridges(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void logEvent(String description, Promise promise) {
        Log.d(TAG, "logEvent: " + description);
        DataLogger.saveEvent(description);
        promise.resolve("New event has been stored.");
    }

    @ReactMethod
    public void getEvents(Promise promise) {
        String events = GSON.toJson(DataLogger.getEvents());
        promise.resolve(events);
        Log.d(TAG, "getEvents: " + events);
    }

    @ReactMethod
    public void startService() {
        Intent intent = new Intent(mContext, HeadlessTaskService.class);
        mContext.startService(intent);
    }

    @ReactMethod
    public void stopService() {
        Intent intent = new Intent(mContext, HeadlessTaskService.class);
        mContext.stopService(intent);
    }
}
