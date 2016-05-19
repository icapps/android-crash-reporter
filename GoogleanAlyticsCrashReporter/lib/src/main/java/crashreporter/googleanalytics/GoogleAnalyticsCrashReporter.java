package crashreporter.googleanalytics;

import android.content.Context;
import android.support.annotation.NonNull;

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

	@Override
	public void setUserIdentifier(final String userIdentifier) {
		mTracker.set("&uid", userIdentifier);
	}

	@Override
	public void leaveBreadcrumb(final String breadCrumb) {
		mTracker.send(new HitBuilders.EventBuilder().setCategory("breadcrumb").setAction(breadCrumb).setLabel("").build());
	}

	@Override
	public void logEvent(final String event) {
		mTracker.send(new HitBuilders.EventBuilder().setCategory("event").setAction(event).setLabel("").build());
	}

	@Override
	public void logHandledException(final Throwable handledError) {
		mTracker.send(new HitBuilders.ExceptionBuilder().setDescription(constructMessage(handledError)).setFatal(false).build());
	}

	@Override
	public void logData(final String key, final Object value) {
		mTracker.send(new HitBuilders.EventBuilder().setCategory(key).setAction(value.toString()).setLabel("").build());
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
