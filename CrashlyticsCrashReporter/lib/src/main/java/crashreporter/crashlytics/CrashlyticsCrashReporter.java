package crashreporter.crashlytics;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsListener;
import com.icapps.crashreporter.CrashReporter;

import io.fabric.sdk.android.Fabric;

public class CrashlyticsCrashReporter implements CrashReporter {

	private boolean mHasCrashed = false;

	public CrashlyticsCrashReporter(@NonNull final Context context) {
		final CrashlyticsListener listener = new CrashlyticsListener() {
			@Override
			public void crashlyticsDidDetectCrashDuringPreviousExecution() {
				mHasCrashed = true;
			}
		};

		final CrashlyticsCore core = new CrashlyticsCore.Builder().listener(listener).build();
		Fabric.with(context, new Crashlytics.Builder().core(core).build());
	}

	@Override
	public void setUserIdentifier(@Nullable final String userIdentifier) {
		Crashlytics.setUserIdentifier(userIdentifier);
	}

	@Override
	public void leaveBreadcrumb(@Nullable final String breadCrumb) {
		if (breadCrumb == null) {
			return;
		}
		Crashlytics.log(breadCrumb);
	}

	@Override
	public void logEvent(@Nullable final String event) {
		//Not supported
	}

	@Override
	public void logHandledException(@Nullable final Throwable handledError) {
		if (handledError == null) {
			return;
		}
		Crashlytics.logException(handledError);
	}

	@Override
	public void logData(final String key, @Nullable final Object value) {
		//Not supported
	}

	@Override
	public boolean didCrashLastSession() {
		return mHasCrashed;
	}

	@Override
	public void logNetworkRequest(@Nullable final String method, @Nullable final String url, final int responseCode) {
		//Not supported
	}
}
