package com.icapps.crashreporter;

import android.support.annotation.Nullable;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
public class CrashLog {

	@Nullable
	private static CrashReporter mCrashReporter;

	public static void initialize(@Nullable final CrashReporter reporter) {
		mCrashReporter = reporter;
	}

	public static void setUserIdentifier(@Nullable final String userIdentifier) {
		if (mCrashReporter != null) {
			mCrashReporter.setUserIdentifier(userIdentifier);
		}
	}

	public static void leaveBreadcrumb(@Nullable final String breadCrumb) {
		if (mCrashReporter != null) {
			mCrashReporter.leaveBreadcrumb(breadCrumb);
		}
	}

	public static void logEvent(@Nullable final String event) {
		if (mCrashReporter != null) {
			mCrashReporter.logEvent(event);
		}
	}

	public static void logHandledException(@Nullable final Throwable handledError) {
		if (mCrashReporter != null) {
			mCrashReporter.logHandledException(handledError);
		}
	}

	public static void logData(final String key, @Nullable final Object value) {
		if (mCrashReporter != null) {
			mCrashReporter.logData(key, value);
		}
	}

	public static boolean didCrashLastSession() {
		if (mCrashReporter != null) {
			return mCrashReporter.didCrashLastSession();
		}
		return false;
	}

	public static void logNetworkRequest(@Nullable final String method, @Nullable final String url, final int responseCode) {
		if (mCrashReporter != null) {
			mCrashReporter.logNetworkRequest(method, url, responseCode);
		}
	}

}
