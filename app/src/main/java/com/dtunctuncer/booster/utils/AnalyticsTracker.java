package com.dtunctuncer.booster.utils;

import android.content.Context;

import com.dtunctuncer.booster.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.Map;

public final class AnalyticsTracker {
    public enum Target {
        APP
    }
    private static AnalyticsTracker sInstance;
    public static synchronized void initialize(Context context) {
        if (sInstance != null) {
            throw new IllegalStateException("Extra call to initialize analytics trackers");
        }
        sInstance = new AnalyticsTracker(context);
    }
    public static synchronized AnalyticsTracker getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Call initialize() before getInstance()");
        }
        return sInstance;
    }
    private final Map<Target, Tracker> mTrackers = new HashMap<Target, Tracker>();
    private final Context mContext;


    private AnalyticsTracker(Context context) {
        mContext = context.getApplicationContext();
    }
    public synchronized Tracker get(Target target) {
        if (!mTrackers.containsKey(target)) {
            Tracker tracker;
            switch (target) {
                case APP:
                    tracker = GoogleAnalytics.getInstance(mContext).newTracker(R.xml.global_tracker);
                    break;
                default:
                    throw new IllegalArgumentException("Unhandled analytics target " + target);
            }
            mTrackers.put(target, tracker);
        }
        return mTrackers.get(target);
    }
}