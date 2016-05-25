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
		
	//One or more:
	compile "com.icapps.crashreporter:crashlog-crittercism:<version>"
	compile "com.icapps.crashreporter:crashlog-googleanalytics:<version>"
	
	//When you use the crittercism logger
	compile 'com.crittercism:crittercism-android-agent:<version>' //The library currently works verified against 5.5.5
	
	//When you use the google analytics logger
	compile 'com.google.android.gms:play-services-analytics:<version>' //The library is currently verified against 8.4.0
}
```

For the latest version, please have a look at the `gradle.properties` file or check the [maven_repository](https://bitbucket.org/icapps/maven_repository)


## Usage ##

The component consists of three major parts:

- `CrashLog`: Static class to interface with the component. To start reporting crashes, configure it during application startup with a crash reporter
- `CrashReporter`: Interface for crash loggers
- `CompoundCrashReporter`: Helper CrashReporter that dispatches the crashes to multiple configured crash loggers, usefull when using multiple crash loggers in the same project

### Functions: ###

Currently the following `CrashReporters` are supported:

- CrittercismCrashreporter
- GoogleAnalyticsCrashReporter