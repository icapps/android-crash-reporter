package com.icapps.crashreporter;

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
		mCrashReporters.add(reporter);
	}

	@Override
	public void setUserIdentifier(final String userIdentifier) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.setUserIdentifier(userIdentifier);
		}
	}

	@Override
	public void leaveBreadcrumb(final String breadCrumb) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.leaveBreadcrumb(breadCrumb);
		}
	}

	@Override
	public void logEvent(final String event) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logEvent(event);
		}
	}

	@Override
	public void logHandledException(final Throwable handledError) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logHandledException(handledError);
		}
	}

	@Override
	public void logData(final String key, final Object value) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logData(key, value);
		}
	}
}
