package com.icapps.crashreporter;

import android.content.Context;

import com.splunk.mint.Mint;

/**
 * Created by woutergoossens on 27/06/15.
 */
public class SplunkCrashReporter extends CrashReporter {

    private Context mContext;

    /**
     * Do not initialize CrashReporter in constructor.
     * This must be done in the initialize method to avoid multiple instantiations.
     *
     * @param apiKey
     */
    public SplunkCrashReporter(Context context, String apiKey) {
        super(apiKey);
        mContext = context;
    }

    @Override
    protected void initialize() {
        Mint.initAndStartSession(mContext, this.apiKey);
    }

    @Override
    protected void setSetUserIdentifier(String userIdentifier) {
        Mint.setUserIdentifier(userIdentifier);
    }

    @Override
    protected void logBreadcrumb(String breadcrumb) {
        Mint.leaveBreadcrumb(breadcrumb);
    }

    @Override
    protected void logEvent(String event) {
        Mint.logEvent(event);
    }

    @Override
    protected void logException(Exception exception) {
        Mint.logException(exception);
    }

    @Override
    protected void logExtraData(String key, String value) {
        Mint.addExtraData(key, value);
    }

    @Override
    protected void startTransaction(String transactionName) {
        Mint.transactionStart(transactionName);
    }

    @Override
    protected void stopTransaction(String transactionName) {
        Mint.transactionStop(transactionName);
    }

    @Override
    protected void cancelTransaction(String transactionName) {
        Mint.transactionCancel(transactionName, "cancelled");
    }
}
