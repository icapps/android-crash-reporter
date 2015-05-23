package com.icapps.crashreporter;

import java.net.HttpURLConnection;

/**
 * Created by Jeroen Mols on 23/05/15.
 */
public interface CrashFormatter {

    String formatUserIdentifier(String userIdentifier);

    String formatBreadCrumb(String viewName);

    String formatHttpFailure(HttpURLConnection httpURLConnection);

    String formatEvent(String event);

    String formatExtraKey(String key);

    String formatExtraValue(String value);

}
