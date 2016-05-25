package crashreporter.crittercism;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.crittercism.app.Crittercism;
import com.crittercism.app.CrittercismConfig;
import com.icapps.crashreporter.CrashReporter;

import java.net.URL;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
public class CrittercismCrashReporter implements CrashReporter {

	public CrittercismCrashReporter(@NonNull final Context context, @NonNull final String apiKey, @NonNull final Config config) {
		final CrittercismConfig critterConfig = new CrittercismConfig();
		critterConfig.setServiceMonitoringEnabled(config.logNetworkCalls);
		critterConfig.setVersionCodeToBeIncludedInVersionString(config.includeVersionCode);
		Crittercism.initialize(context.getApplicationContext(), apiKey, critterConfig);
	}

	@Override
	public void setUserIdentifier(@Nullable final String userIdentifier) {
		if (userIdentifier == null) {
			return;
		}
		Crittercism.setUsername(userIdentifier);
	}

	@Override
	public void leaveBreadcrumb(@Nullable final String breadCrumb) {
		if (breadCrumb == null) {
			return;
		}
		Crittercism.leaveBreadcrumb(breadCrumb);
	}

	@Override
	public void logEvent(final String event) {
		//Not supported
	}

	@Override
	public void logHandledException(@Nullable final Throwable handledError) {
		if (handledError == null) {
			return;
		}
		Crittercism.logHandledException(handledError);
	}

	@Override
	public void logData(final String key, @Nullable final Object value) {
		//Not supported
	}

	@Override
	public boolean didCrashLastSession() {
		return Crittercism.didCrashOnLastLoad();
	}

	@Override
	public void logNetworkRequest(@Nullable final String method, @Nullable final String url, final int responseCode) {
		try {
			Crittercism.logNetworkRequest(method == null ? "Unknown" : method, new URL(url == null ? "http://unknown" : url), 0, 0, 0, responseCode, null);
		} catch (final Throwable ignore) {
		}
	}

	public static class Config {
		public boolean logNetworkCalls = true;
		public boolean includeVersionCode = true;
	}
}