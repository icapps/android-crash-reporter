package com.icapps.crashreporter;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
public interface CrashReporter {

	void setUserIdentifier(final String userIdentifier);

	void leaveBreadcrumb(final String breadCrumb);

	void logEvent(final String event);

	void logHandledException(final Throwable handledError);

	void logData(final String key, final Object value);

	boolean didCrashLastSession();

}
