/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package crashreporter.hockeyapp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.icapps.crashreporter.CrashReporter;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.metrics.MetricsManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
public class HockeyAppCrashReporter implements CrashReporter {

	private final boolean mLogNetworkCalls;
	private final BreadcrumbCrashManagerListener mBreadcrumbHelper;

	public HockeyAppCrashReporter(@NonNull final Context context, @NonNull final Config config) {
		CrashManager.register(context, mBreadcrumbHelper = new BreadcrumbCrashManagerListener(config.maxNumBreadcrumbs));

		mLogNetworkCalls = config.logNetworkCalls;
		MetricsManager.register((Application) context.getApplicationContext());
		if (config.trackMetrics) {
			MetricsManager.enableUserMetrics();
		} else {
			MetricsManager.disableUserMetrics();
		}
	}

	@Override
	public void setUserIdentifier(@Nullable final String userIdentifier) {
		mBreadcrumbHelper.setUserId(userIdentifier);
	}

	@Override
	public void leaveBreadcrumb(@Nullable final String breadCrumb) {
		if (breadCrumb == null) {
			return;
		}
		mBreadcrumbHelper.addBreadcrumb(breadCrumb);
	}

	@Override
	public void logEvent(final String event) {
		//Not supported
		MetricsManager.trackEvent(event);
	}

	@Override
	public void logHandledException(@Nullable final Throwable handledError) {
		if (handledError == null) {
			return;
		}
		final StringWriter buffer = new StringWriter();
		PrintWriter writer = new PrintWriter(buffer);
		handledError.printStackTrace(writer);
		writer.flush();
		MetricsManager.trackEvent("Handled exception - " + buffer.toString());
	}

	@Override
	public void logData(final String key, @Nullable final Object value) {
		//Not supported
		MetricsManager.trackEvent(key + " [" + value + "]");
	}

	@Override
	public boolean didCrashLastSession() {
		try {
			return CrashManager.didCrashInLastSession().get();
		} catch (final Throwable e) {
			return false;
		}
	}

	@Override
	public void logNetworkRequest(@Nullable final String method, @Nullable final String url, final int responseCode) {
		if (mLogNetworkCalls) {
			final Map<String, String> properties = new HashMap<>();
			properties.put("method", (method == null) ? "Unknown" : method);
			properties.put("url", (url == null) ? "Unknown" : url);
			properties.put("response", responseCode + "");

			MetricsManager.trackEvent("Network", properties);
		}
	}

	public static class Config {
		public boolean logNetworkCalls = true;
		public boolean trackMetrics = true;
		public int maxNumBreadcrumbs = 100;
	}
}
