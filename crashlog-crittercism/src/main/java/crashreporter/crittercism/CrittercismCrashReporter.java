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

package crashreporter.crittercism;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    public void logData(@NonNull final String key, @Nullable final Object value) {
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
