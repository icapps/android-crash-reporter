package crashreporter.googleanalytics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.icapps.crashreporter.CrashReporter;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
public class GoogleAnalyticsCrashReporter implements CrashReporter {

	private final Tracker mTracker;

	public GoogleAnalyticsCrashReporter(@NonNull final Context context, @NonNull final String apiKey) {
		final GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(context.getApplicationContext());

		mTracker = googleAnalytics.newTracker(apiKey);
		mTracker.enableExceptionReporting(true);
	}

	public GoogleAnalyticsCrashReporter(@NonNull final Tracker tracker) {
		mTracker = tracker;
		mTracker.enableExceptionReporting(true);
	}

	@Override
	public void setUserIdentifier(final String userIdentifier) {
		mTracker.set("&uid", userIdentifier);
	}

	@Override
	public void leaveBreadcrumb(final String breadCrumb) {
		if (breadCrumb == null) {
			return;
		}
		mTracker.send(new HitBuilders.EventBuilder().setCategory("breadcrumb").setAction(breadCrumb).setLabel("").build());
	}

	@Override
	public void logEvent(@Nullable final String event) {
		if (event == null) {
			return;
		}
		mTracker.send(new HitBuilders.EventBuilder().setCategory("event").setAction(event).setLabel("").build());
	}

	@Override
	public void logHandledException(@Nullable final Throwable handledError) {
		if (handledError == null) {
			return;
		}
		mTracker.send(new HitBuilders.ExceptionBuilder().setDescription(constructMessage(handledError)).setFatal(false).build());
	}

	@Override
	public void logData(final String key, @Nullable final Object value) {
		final HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder().setCategory(key);
		if (value != null) {
			builder.setAction(value.toString());
		}
		builder.setLabel("");
		mTracker.send(builder.build());
	}

	@Override
	public boolean didCrashLastSession() {
		return false;
	}

	@Override
	public void logNetworkRequest(@Nullable final String method, @Nullable final String url, final int responseCode) {
		if (method == null && url == null) {
			return;
		}

		final HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder().setCategory("network");
		builder.setAction(method == null ? "Unknown" : method);
		builder.setLabel(url == null ? "Unknown" : url);

		mTracker.send(builder.build());
	}

	private String constructMessage(final Throwable error) {
		try {
			final StringWriter writer = new StringWriter();
			final PrintWriter printer = new PrintWriter(writer);
			error.printStackTrace(printer);

			return writer.toString();
		} catch (final Throwable e) {
			return "";
		}
	}
}
