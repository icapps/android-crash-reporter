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
 *
 */

package com.icapps.crashreporter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicola Verbeeck
 * @version 1
 */
public class CompoundCrashReporter implements CrashReporter {

	private final List<CrashReporter> mCrashReporters;

	public CompoundCrashReporter() {
		mCrashReporters = new ArrayList<>(2);
	}

	public void addCrashLogger(@Nullable final CrashReporter reporter) {
		if (reporter != null) {
			mCrashReporters.add(reporter);
		}
	}

	@Override
	public void setUserIdentifier(@Nullable final String userIdentifier) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.setUserIdentifier(userIdentifier);
		}
	}

	@Override
	public void leaveBreadcrumb(@Nullable final String breadCrumb) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.leaveBreadcrumb(breadCrumb);
		}
	}

	@Override
	public void logEvent(@Nullable final String event) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logEvent(event);
		}
	}

	@Override
	public void logHandledException(@Nullable final Throwable handledError) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logHandledException(handledError);
		}
	}

	@Override
	public void logData(@NonNull final String key, @Nullable final Object value) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logData(key, value);
		}
	}

	@Override
	public boolean didCrashLastSession() {
		for (final CrashReporter crashReporter : mCrashReporters) {
			if (crashReporter.didCrashLastSession()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void logNetworkRequest(@Nullable final String method, @Nullable final String url, final int responseCode) {
		for (final CrashReporter crashReporter : mCrashReporters) {
			crashReporter.logNetworkRequest(method, url, responseCode);
		}
	}
}
