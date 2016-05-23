package com.icapps.crashreporter;

import android.support.annotation.Nullable;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
public interface CrashReporter {

	void setUserIdentifier(@Nullable final String userIdentifier);

	void leaveBreadcrumb(@Nullable final String breadCrumb);

	void logEvent(@Nullable final String event);

	void logHandledException(@Nullable final Throwable handledError);

	void logData(final String key, @Nullable final Object value);

	boolean didCrashLastSession();

	void logNetworkRequest(@Nullable final String method, @Nullable final String url, final int responseCode);

}
