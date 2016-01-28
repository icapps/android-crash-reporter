package com.icapps.crashreporter;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Jeroen Mols on 04/08/15.
 */
public class GoogleAnalyticsCrashReporter extends CrashReporter {

	private final Context applicationContext;
	private final boolean trackActivities;

	private Tracker tracker;

	public GoogleAnalyticsCrashReporter(Context applicationContext, String apiKey, boolean trackActivities) {
		super(apiKey);
		this.applicationContext = applicationContext;
		this.trackActivities = trackActivities;
	}

	public GoogleAnalyticsCrashReporter(Context applicationContext, int trackerId) {
		super(trackerId);
		this.applicationContext = applicationContext;
		this.trackActivities = false;
	}

	@Override
	protected void initialize() {
		GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(applicationContext);
		if (apiKey != null) {
			tracker = googleAnalytics.newTracker(apiKey);
			tracker.enableExceptionReporting(true);
			tracker.enableAutoActivityTracking(trackActivities);
		} else {
			tracker = googleAnalytics.newTracker(apiResId);
		}
	}

	@Override
	protected void setUserIdentifier(String userIdentifier) {
		tracker.set("&uid", userIdentifier);
		tracker.send(new HitBuilders.EventBuilder().setCategory("UX").setAction("User Signed In").setLabel(null).build());
	}

	@Override
	protected void logBreadcrumb(String breadcrumb) {
		logGoogleAnalyticsCategory("breadcrumb", breadcrumb);
	}

	@Override
	protected void logEvent(String event) {
		logGoogleAnalyticsCategory("event", event);
	}

	@Override
	protected void logException(Exception exception) {
		tracker.send(new HitBuilders.ExceptionBuilder().setDescription(exception.getLocalizedMessage()).setFatal(false).build());
	}

	@Override
	protected void logExtraData(String key, String value) {
		logGoogleAnalyticsCategory(key, value);
	}

	@Override
	protected void startTransaction(String transactionName) {
		// not supported
	}

	@Override
	protected void stopTransaction(String transactionName) {
		// not supported
	}

	@Override
	protected void cancelTransaction(String transactionName) {
		// not supported
	}

	private void logGoogleAnalyticsCategory(String category, String action) {
		tracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel("").build());
	}
}
