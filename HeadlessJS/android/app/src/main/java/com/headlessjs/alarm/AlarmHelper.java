package com.headlessjs.alarm;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import static android.app.AlarmManager.RTC_WAKEUP;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Context.ALARM_SERVICE;
import static android.os.Build.VERSION.SDK_INT;
import static java.util.concurrent.TimeUnit.MINUTES;
import static com.headlessjs.alarm.Actions.REPEATING_ALARM;
import static com.headlessjs.alarm.Actions.SELF_SCHEDULING_ALARM;
import static com.headlessjs.alarm.Extras.EXPECTED_FIRING_TIME;
import static com.headlessjs.alarm.Extras.EXPECTED_INTERVAL;
import static com.headlessjs.alarm.Extras.SCHEDULING_TIME;

public class AlarmHelper {
    private static final int PI_SELF_SCHEDULED_ALARM = 1000;
    private static final int PI_REPEATING_ALARM = 1001;

    private final Context context;
    private AlarmManager alarmManager;

    public AlarmHelper(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }

    public void setNextSelfSchedulingAlarm() {
        final long fiveMinutesFromNow = System.currentTimeMillis() + MINUTES.toMillis(5);
        Intent exactIntent = new Intent(context, AlarmReceiver.class)
                .setAction(SELF_SCHEDULING_ALARM)
                .putExtra(SCHEDULING_TIME, System.currentTimeMillis())
                .putExtra(EXPECTED_FIRING_TIME, fiveMinutesFromNow);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, PI_SELF_SCHEDULED_ALARM,
                exactIntent, FLAG_UPDATE_CURRENT);
        if (SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(RTC_WAKEUP, fiveMinutesFromNow, pendingIntent);
        } else if (SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(RTC_WAKEUP, fiveMinutesFromNow, pendingIntent);
        } else {
            alarmManager.set(RTC_WAKEUP, fiveMinutesFromNow, pendingIntent);
        }
    }

    public void startRepeatingAlarm() {
        final int repeatingIntervalInMinutes = 5;
        final long fiveMinutesFromNow = System.currentTimeMillis() + MINUTES.toMillis(5);
        Intent repeatingIntent = new Intent(context, AlarmReceiver.class)
                .setAction(REPEATING_ALARM)
                .putExtra(SCHEDULING_TIME, System.currentTimeMillis())
                .putExtra(EXPECTED_FIRING_TIME, fiveMinutesFromNow)
                .putExtra(EXPECTED_INTERVAL, repeatingIntervalInMinutes);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, PI_REPEATING_ALARM,
                repeatingIntent, FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(RTC_WAKEUP, fiveMinutesFromNow, MINUTES.toMillis(repeatingIntervalInMinutes), pendingIntent);

    }
}
