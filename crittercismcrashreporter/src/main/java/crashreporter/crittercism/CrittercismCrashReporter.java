package crashreporter.crittercism;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crittercism.app.Crittercism;
import com.crittercism.app.CrittercismConfig;
import com.icapps.crashreporter.CrashReporter;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
public class CrittercismCrashReporter implements CrashReporter {

	public CrittercismCrashReporter(@NonNull final Context context, @NonNull final String apiKey, final Config config) {
		final CrittercismConfig critterConfig = new CrittercismConfig();
		critterConfig.setServiceMonitoringEnabled(config.logNetworkCalls);
		critterConfig.setVersionCodeToBeIncludedInVersionString(config.includeVersionCode);
		Crittercism.initialize(context.getApplicationContext(), apiKey, critterConfig);
	}

	@Override
	public void setUserIdentifier(final String userIdentifier) {
		Crittercism.setUsername(userIdentifier);
	}

	@Override
	public void leaveBreadcrumb(final String breadCrumb) {
		Crittercism.leaveBreadcrumb(breadCrumb);
	}

	@Override
	public void logEvent(final String event) {
		//Not supported
	}

	@Override
	public void logHandledException(final Throwable handledError) {
		Crittercism.logHandledException(handledError);
	}

	@Override
	public void logData(final String key, final Object value) {
		//Not supported
	}

	public static class Config {
		public boolean logNetworkCalls = true;
		public boolean includeVersionCode = true;
	}
}
