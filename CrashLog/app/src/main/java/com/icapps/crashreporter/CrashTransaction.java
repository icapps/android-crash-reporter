package com.icapps.crashreporter;

import java.util.Collection;

/**
 * Created by Jeroen Mols on 21/05/15.
 */
public class CrashTransaction {

    private final Collection<CrashReporter> crashReporters;
    private final String                    transactionName;

    public CrashTransaction(Collection<CrashReporter> crashReporters, String transactionName) {
        this.crashReporters = crashReporters;
        this.transactionName = String.format("%s-%s", java.util.UUID.randomUUID().toString(), transactionName);
    }

    public void startTransaction() {
        for (CrashReporter reporter : crashReporters) {
            reporter.startTransaction(transactionName);
        }
    }

    public void stopTransaction() {
        for (CrashReporter reporter : crashReporters) {
            reporter.stopTransaction(transactionName);
        }
    }

    public void cancelTransation() {
        for (CrashReporter reporter : crashReporters) {
            reporter.cancelTransaction(transactionName);
        }
    }
}
