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

package crashreporter.crashlytics;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.icapps.crashreporter.CrashReporter;

public class FirebaseCrashReporter implements CrashReporter {

    private static final String TAG = FirebaseCrashReporter.class.getSimpleName();

    private final boolean mHasCrashed;

    public FirebaseCrashReporter(@NonNull final Config config) {
        if (config.debugging) {
            Log.d(TAG, "Initializing firebase crash reporter");
        }
        final FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();

        mHasCrashed = crashlytics.didCrashOnPreviousExecution();
    }

    @Override
    public void setUserIdentifier(@Nullable final String userIdentifier) {
        if (userIdentifier != null)
            FirebaseCrashlytics.getInstance().setUserId(userIdentifier);
    }

    @Override
    public void leaveBreadcrumb(@Nullable final String breadCrumb) {
        if (breadCrumb == null) {
            return;
        }
        FirebaseCrashlytics.getInstance().log(breadCrumb);
    }

    @Override
    public void logEvent(@Nullable final String event) {
        if (event == null) {
            return;
        }

        FirebaseCrashlytics.getInstance().log(event);
    }

    @Override
    public void logHandledException(@Nullable final Throwable handledError) {
        if (handledError == null) {
            return;
        }
        FirebaseCrashlytics.getInstance().recordException(handledError);
    }

    @Override
    public void logData(final String key, @Nullable final Object value) {
        FirebaseCrashlytics.getInstance().setCustomKey(key, String.valueOf(value));
    }

    @Override
    public boolean didCrashLastSession() {
        return mHasCrashed;
    }

    @Override
    public void logNetworkRequest(@Nullable final String method, @Nullable final String url, final int responseCode) {
    }

    public static class Config {
        public boolean debugging = false;
    }
}
