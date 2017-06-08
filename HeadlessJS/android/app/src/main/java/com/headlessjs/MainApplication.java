package com.headlessjs;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.headlessjs.alarm.AlarmHelper;
import com.headlessjs.module.HeadlessPackager;
import com.orhanobut.hawk.Hawk;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new HeadlessPackager()
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
        SoLoader.init(this, /* native exopackage */ false);

        AlarmHelper alarm = new AlarmHelper(this);
        alarm.startRepeatingAlarm();
        alarm.setNextSelfSchedulingAlarm();
    }
}
