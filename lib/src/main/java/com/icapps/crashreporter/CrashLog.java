package com.icapps.crashreporter;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
public class CrashLog {

	private static CrashReporter mCrashReporter;

	public static void initialize(final CrashReporter reporter) {
		mCrashReporter = reporter;
	}

	public static void setUserIdentifier(final String userIdentifier) {
		if (mCrashReporter != null) {
			mCrashReporter.setUserIdentifier(userIdentifier);
		}
	}

	public static void leaveBreadcrumb(final String breadCrumb) {
		if (mCrashReporter != null) {
			mCrashReporter.leaveBreadcrumb(breadCrumb);
		}
	}

	public static void logEvent(final String event) {
		if (mCrashReporter != null) {
			mCrashReporter.logEvent(event);
		}
	}

	public static void logHandledException(final Throwable handledError) {
		if (mCrashReporter != null) {
			mCrashReporter.logHandledException(handledError);
		}
	}

	public static void logData(final String key, final Object value) {
		if (mCrashReporter != null) {
			mCrashReporter.logData(key, value);
		}
	}

}
