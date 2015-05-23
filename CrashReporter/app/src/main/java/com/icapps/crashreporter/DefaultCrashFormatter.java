package com.icapps.crashreporter;

import java.net.HttpURLConnection;

/**
 * Created by Jeroen Mols on 23/05/15.
 */
public class DefaultCrashFormatter implements CrashFormatter {

    @Override
    public String formatUserIdentifier(String userIdentifier) {
        // TODO
        return userIdentifier;
    }

    @Override
    public String formatBreadCrumb(String viewName) {
        // TODO
        return viewName;
    }

    @Override
    public String formatHttpFailure(HttpURLConnection httpURLConnection) {
        // TODO
        return "";
    }

    @Override
    public String formatEvent(String event) {
        // TODO
        return event;
    }

    @Override
    public String formatException(Exception exception) {
        // TODO
        return "";
    }

    @Override
    public String formatExtraKey(String key) {
        // TODO
        return key;
    }

    @Override
    public String formatExtraValue(String value) {
        // TODO
        return value;
    }
}
