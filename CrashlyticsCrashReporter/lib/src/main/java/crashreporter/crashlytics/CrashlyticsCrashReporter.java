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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsListener;
import com.icapps.crashreporter.CrashReporter;

import io.fabric.sdk.android.Fabric;

public class CrashlyticsCrashReporter implements CrashReporter {

    private static final String TAG = CrashlyticsCrashReporter.class.getSimpleName();

    private boolean mHasCrashed = false;
    private final boolean mLogNetworkRequests;

    public CrashlyticsCrashReporter(@NonNull final Context context, @NonNull final Config config) {
        if (config.debugging) {
            Log.d(TAG, "Initializing crashlytics crash reporter");
        }
        final CrashlyticsListener listener = new CrashlyticsListener() {
            @Override
            public void crashlyticsDidDetectCrashDuringPreviousExecution() {
                mHasCrashed = true;
            }
        };

        if (config.debugging) {
            Log.d(TAG, "Creating core with crash session listener");
        }
        final CrashlyticsCore core = new CrashlyticsCore.Builder()
                .listener(listener)
                .build();

        if (config.debugging) {
            Log.d(TAG, "Creating builder");
        }
        final Crashlytics.Builder builder = new Crashlytics.Builder();
        if (config.debugging) {
            Log.d(TAG, "Setting core");
        }
        builder.core(core);
        if (config.debugging) {
            Log.d(TAG, "Checking if we need answers: " + config.enableAnswers);
        }
        if (config.enableAnswers) {
            builder.answers(new Answers());
        }
        mLogNetworkRequests = config.logNetworkCalls;
        if (config.debugging) {
            Log.d(TAG, "Init fabric");
        }
        Fabric.with(new Fabric.Builder(context)
                .debuggable(config.debugging)
                .kits(builder.build())
                .build());
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
        if (event == null) {
            return;
        }

        final Answers answers = Answers.getInstance();
        if (answers == null) {
            return;
        }
        answers.logCustom(new CustomEvent(event));
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
        Crashlytics.log(Log.DEBUG, key, String.valueOf(value));
    }

    @Override
    public boolean didCrashLastSession() {
        return mHasCrashed;
    }

    @Override
    public void logNetworkRequest(@Nullable final String method, @Nullable final String url, final int responseCode) {
        if (mLogNetworkRequests) {
            final Answers answers = Answers.getInstance();
            if (answers == null) {
                return;
            }

            final CustomEvent event = new CustomEvent("network");
            event.putCustomAttribute("method", (method == null) ? "Unknown" : method);
            event.putCustomAttribute("url", (url == null) ? "Unknown" : url);
            event.putCustomAttribute("response", responseCode);

            answers.logCustom(event);
        }
    }

    public static class Config {
        public boolean logNetworkCalls = false;
        public boolean enableAnswers = true;
        public boolean debugging = false;
    }
}
