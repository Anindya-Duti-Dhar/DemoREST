package com.quickblox.sample.core;

import android.app.Application;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.sample.core.utils.ActivityLifecycle;


public class CoreApp extends Application {

    private static CoreApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifecycle.init(this);
        instance = this;
    }

    public static synchronized CoreApp getInstance() {
        return instance;
    }

    public void initCredentials(String APP_ID, String AUTH_KEY, String AUTH_SECRET, String ACCOUNT_KEY) {
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }
}