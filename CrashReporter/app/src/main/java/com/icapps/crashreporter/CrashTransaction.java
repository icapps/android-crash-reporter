package com.icapps.crashreporter;

/**
 * Created by Jeroen Mols on 21/05/15.
 */
public class CrashTransaction {

    private final CrashReporter crashReporter;
    private final String        transactionName;

    public CrashTransaction(CrashReporter crashReporter, String transactionName) {
        this.crashReporter = crashReporter;
        this.transactionName = String.format("%s-%s", java.util.UUID.randomUUID().toString() + transactionName);
    }

    public void startTransaction() {
        crashReporter.startTransaction(transactionName);
    }

    public void stopTransaction() {
        crashReporter.stopTransaction(transactionName);
    }

    public void cancelTransation() {
        crashReporter.cancelTransaction(transactionName);
    }
}
