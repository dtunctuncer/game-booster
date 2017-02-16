package com.dtunctuncer.booster.utils.analytics;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class AnalyticsUtils {
    public static final String RELEASE = "release";

    public static synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTracker analyticsTrackers = AnalyticsTracker.getInstance();
        return analyticsTrackers.get(AnalyticsTracker.Target.APP);
    }

    public static void trackEvent(String category, String action, String label) {
        Tracker tracker = getGoogleAnalyticsTracker();
        tracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }

    public static void trackException(Exception e) {
        if (e != null) {
            Tracker tracker = getGoogleAnalyticsTracker();
            tracker.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(e.getMessage())
                    .setFatal(false)
                    .build()
            );
        }
    }

    public static void trackScreenView(String screenName, Context context) {
        Tracker tracker = getGoogleAnalyticsTracker();
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        GoogleAnalytics.getInstance(context).dispatchLocalHits();
    }
}