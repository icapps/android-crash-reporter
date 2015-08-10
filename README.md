# iCapps Crash Log #
Wrapper library around different crash reporters to enable crash reporting in your project.

## Installation ##
### Maven dependency
Add the following to your top-level `build.gradle` file:

```groovy
allprojects {
    repositories {
        jcenter()

        maven {
            credentials {
                username 'maven_readonly'
                password 'RphfFukDEeuvbPhvxFsu'
            }
            url "https://api.bitbucket.org/1.0/repositories/icapps/maven_repository/raw/releases"
        }
        maven { url 'https://mint.splunk.com/gradle/' }
    }
}
```

Add a dependency on `CrashLog` to your `build.gradle` file:

```groovy
dependencies {
    compile "com.icapps.crashreporter:crashlog:<version>"
}
```

For the latest version, please have a look at the `gradle.properties` file or check the [maven_repository](https://bitbucket.org/icapps/maven_repository)


### Git Submodule
For development you can also included it as a git submodule, but make sure to step over to a maven dependency when the app goes into production.

- Add the android_crashlog as a git submodule to your project (via ssh) into a directory called "crashlog".
- Add the following to your `settings.gradle` file": `include ':crashlog/crashlog'`
- Add the following to your `build.gradle` file: `compile project(':crashlog/crashlog')`
- Add additional behaviours to your Jenkins configuration:
	- Under Git add `Additional behaviours` -> ` Advanced submodule behaviours`
	- Select `recursively update submodules`

## Usage ##

The component consists of three major parts:

- `CrashLog`: Static class to interface with the component. To start reporting crashes, at least one `CrashReporter` needs to be added. When reporting crashes, all of the added `CrashReporters` will report the crash to their service.
- `CrashReporter`: Generic class that interfaces with an actual crash reporting service. Add one or more subclasses to `CrashLog` to start reporting crashes.
- `CrashFormatter`: Utility class to apply a custom formatting to HTTP failures or breadcrumbs. When not set on the `CrashLog`, the `DefaultCrashFormatter` will be used.

Note that `CrashLog` does not require any initialisation call, all you need to do is add `CrashReporter`s to start reporting crashes. Every `CrashReporter` can only be added once, adding it more than once (or a second instance of the same class), won't have any effect.

### Functions: ###

Start the `CrashLog` by adding a `CrashReporter` and optionally specifying a `CrashFormatter`.

```
CrashLog.add(new SplunkCrashReporter(<applicationcontext>, "splunkkey"));
CrashLog.setCrashFormatter(new CustomCrashFormatter());
```
Currently the following `CrashReporters` are supported:

- SplunkCrashReporter
- GoogleAnalyticsCrashReporter

**Set the user identifier:**
```
CrashLog.setUserIdentifier(String userIdentifier);
```
**Breadcrumb logging:**
```
CrashLog.logBreadcrumb(String viewName);
```

**Service failure logging:**
```
CrashLog.logHTTPFailure(HttpURLConnection httpUrlConnection);
```

Directly pass an HttpUrlConnect object. This will log an event in the following format:
<HTTPMETHOD>: <ERRORCODE>: <SERVICEURL> 
(for example: GET: 404: http://test.com/function)

**Log event:**
```
CrashLog.logEvent(String event);
```

**Log handled exception:**
```
CrashLog.logException(Exception caughtException);
```
Takes an Exception object as argument

**Logging additional information:**
```
CrashLog.logExtraData(String key, String value);
```

**Transactions**
```
//Create and start a new transaction
CrashTransaction transaction = CrashLog.startTransaction(String genericTransactionName);
//Stopping the transaction
transaction.stopTransaction();
//Cancelling the transaction
transaction.cancelTransaction();
```