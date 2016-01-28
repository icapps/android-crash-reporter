package com.icapps.crashreporter;

/**
 * Created by Jeroen Mols on 21/05/15.
 */
public abstract class CrashReporter {

	protected final String apiKey;
	protected final int    apiResId;

	/**
	 * Do not initialize CrashReporter in constructor.
	 * This must be done in the initialize method to avoid multiple instantiations.
	 */
	public CrashReporter(String apiKey) {
		this.apiKey = apiKey;
		this.apiResId = 0;
	}

	public CrashReporter(int apiResId) {
		this.apiKey = null;
		this.apiResId = 0;
	}

	protected String getIdentifier() {
		return getClass().getSimpleName();
	}

	protected abstract void initialize();

	protected abstract void setUserIdentifier(String userIdentifier);

	protected abstract void logBreadcrumb(String breadcrumb);

	protected abstract void logEvent(String event);

	protected abstract void logException(Exception exception);

	protected abstract void logExtraData(String key, String value);

	protected abstract void startTransaction(String transactionName);

	protected abstract void stopTransaction(String transactionName);

	protected abstract void cancelTransaction(String transactionName);

}
