package com.headlessjs.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.headlessjs.DataLogger;
import com.headlessjs.LogEvent;
import com.headlessjs.service.HeadlessTaskService;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;

import static com.headlessjs.alarm.Actions.REPEATING_ALARM;
import static com.headlessjs.alarm.Actions.SELF_SCHEDULING_ALARM;
import static com.headlessjs.alarm.Extras.EXPECTED_FIRING_TIME;
import static com.headlessjs.alarm.Extras.EXPECTED_INTERVAL;
import static com.headlessjs.alarm.Extras.SCHEDULING_TIME;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Hawk.init(context).build();

        String action = intent.getAction();
        if (action == null) return;
        if (REPEATING_ALARM.equals(action)) {
            processRepeatingAlarm(intent);
        } else if (SELF_SCHEDULING_ALARM.equals(action)) {
            processSelfSchedulingAlarm(intent);
        }

        //if (!AppDetector.isAppInForeground(mContext)) {
            Intent intentService = new Intent(mContext, HeadlessTaskService.class);
            mContext.startService(intentService);
            //HeadlessJsTaskService.acquireWakeLockNow(mContext);
        //}
    }

    private void processSelfSchedulingAlarm(Intent intent) {
        logSelfSchedulingAlarmMessage(intent);
        scheduleNextAlarm();
    }

    private void logSelfSchedulingAlarmMessage(Intent intent) {
        final String message = String.format(Locale.US,
                "Scheduled at %tT, expected firing at %tT",
                intent.getLongExtra(SCHEDULING_TIME, 0),
                intent.getLongExtra(EXPECTED_FIRING_TIME, 0));

        final String event = new LogEvent(
                "Native Event Detected",
                getLocationInfo(),
                message
        ).toString();
        DataLogger.saveEvent(event);

        Log.d(TAG, "logSelfSchedulingAlarmMessage: " + event);
    }

    private void scheduleNextAlarm() {
        AlarmHelper alarmHelper = new AlarmHelper(mContext);
        alarmHelper.setNextSelfSchedulingAlarm();
    }

    private void processRepeatingAlarm(Intent intent) {
        final String message = String.format(Locale.US,
                "Scheduled at %tT, expected first firing at %tT, repeating every %d minutes",
                intent.getLongExtra(SCHEDULING_TIME, 0),
                intent.getLongExtra(EXPECTED_FIRING_TIME, 0),
                intent.getIntExtra(EXPECTED_INTERVAL, 0));

        final String event = new LogEvent(
                "Native Event Detected",
                getLocationInfo(),
                message
        ).toString();
        DataLogger.saveEvent(event);

        Log.d(TAG, "processRepeatingAlarm: " + event);
    }

    private String getLocationInfo() {
        Context context = mContext.getApplicationContext();
        if (context instanceof ReactApplication) {
            ReactNativeHost host = ((ReactApplication) context).getReactNativeHost();
            if (host.hasInstance()) {
                return "On Main thread";
            }
        }
        return "On Native background service";
    }
}
