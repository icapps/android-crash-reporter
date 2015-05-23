package com.icapps.crashreporter;

/**
 * Created by Jeroen Mols on 21/05/15.
 */
public abstract class CrashReporter {

    private final String apiKey;

    /**
     * Do not initialize CrashReporter in constructor.
     * This must be done in the initialize method to avoid multiple instantiations.
     */
    public CrashReporter(String apiKey) {
        this.apiKey = apiKey;
    }

    protected String getIdentifier() {
        return getClass().getSimpleName();
    }

    protected abstract void initialize();

    protected abstract void setSetUserIdentifier(String userIdentifier);

    protected abstract void logBreadcrumb(String breadcrumb);

    protected abstract void logEvent(String event);

    protected abstract void logException(Exception exception);

    protected abstract void logExtraData(String key, String value);

    protected abstract void startTransaction(String transactionName);

    protected abstract void stopTransaction(String transactionName);

    protected abstract void cancelTransaction(String transactionName);

}
