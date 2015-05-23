package com.icapps.crashreporter;

import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * Static interface which will redirect all crash logging
 * calls to all registered CrashReporters.
 * <p/>
 * Created by Jeroen Mols on 21/05/15.
 */
public class CrashLog {

    private static       CrashFormatter                 crashFormatter = new DefaultCrashFormatter();
    private static final HashMap<String, CrashReporter> crashReporters = new HashMap<>();

    public static void setCrashFormatter(CrashFormatter customCrashFormatter) {
        crashFormatter = customCrashFormatter;
    }

    public static void add(CrashReporter crashReporter) {
        if (crashReporters.containsKey(crashReporter.getIdentifier())) return;

        crashReporter.initialize();
        crashReporters.put(crashReporter.getIdentifier(), crashReporter);
    }

    public static void setSetUserIdentifier(String userIdentifier) {
        String formattedUserIdentifier = crashFormatter.formatUserIdentifier(userIdentifier);
        for (CrashReporter reporter : crashReporters.values()) {
            reporter.setSetUserIdentifier(formattedUserIdentifier);
        }
    }

    public static void logBreadcrumb(String viewName) {
        String formattedBreadcrumb = crashFormatter.formatBreadCrumb(viewName);
        for (CrashReporter reporter : crashReporters.values()) {
            reporter.logBreadcrumb(formattedBreadcrumb);
        }
    }

    public static void logHTTPFailure(HttpURLConnection httpURLConnection) {
        String formattedHttpFailure = crashFormatter.formatHttpFailure(httpURLConnection);
        for (CrashReporter reporter : crashReporters.values()) {
            reporter.logEvent(formattedHttpFailure);
        }
    }

    public static void logEvent(String event) {
        String formattedEvent = crashFormatter.formatEvent(event);
        for (CrashReporter reporter : crashReporters.values()) {
            reporter.logEvent(formattedEvent);
        }
    }

    public static void logException(Exception exception) {
        for (CrashReporter reporter : crashReporters.values()) {
            reporter.logException(exception);
        }
    }

    public static void logExtraData(String key, String value) {
        String formattedKey = crashFormatter.formatExtraKey(key);
        String formattedValue = crashFormatter.formatExtraValue(value);
        for (CrashReporter reporter : crashReporters.values()) {
            reporter.logExtraData(formattedKey, formattedValue);
        }
    }

    /*
     * Need for TransactionController -> wraps around transaction name
     */
    public static CrashTransaction startTransaction() {
        // TODO
        return null;
    }

    public static CrashTransaction stopTransaction() {
        // TODO
        return null;
    }

    public static CrashTransaction cancelTransaction() {
        // TODO
        return null;
    }
}
