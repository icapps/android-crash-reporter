package com.icapps.crashreporter;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
public class CompoundCrashReporter implements CrashReporter {

	private final List<CrashReporter> mCrashReporters;

	public CompoundCrashReporter() {
		mCrashReporters = new ArrayList<>(2);
	}

	public void addCrashLogger(final CrashReporter reporter) {
		if (reporter != null) {
			mCrashReporters.add(reporter);
		}
	}

	@Override
	public void setUserIdentifier(@Nullable final String userIdentifier) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.setUserIdentifier(userIdentifier);
		}
	}

	@Override
	public void leaveBreadcrumb(@Nullable final String breadCrumb) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.leaveBreadcrumb(breadCrumb);
		}
	}

	@Override
	public void logEvent(@Nullable final String event) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logEvent(event);
		}
	}

	@Override
	public void logHandledException(@Nullable final Throwable handledError) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logHandledException(handledError);
		}
	}

	@Override
	public void logData(final String key, @Nullable final Object value) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logData(key, value);
		}
	}

	@Override
	public boolean didCrashLastSession() {
		for (final CrashReporter crashReporter : mCrashReporters) {
			if (crashReporter.didCrashLastSession()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void logNetworkRequest(@Nullable final String method, @Nullable final String url, final int responseCode) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logNetworkRequest(method, url, responseCode);
		}
	}
}
