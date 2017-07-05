# iCapps Crash Log #
Wrapper library around different crash reporters to enable crash reporting in your project.

## Installation ##
### Maven dependency
Add the following to your top-level `build.gradle` file:

```groovy

 buildscript {
     repositories {
        maven { url 'https://maven.fabric.io/public' } //only when you use fabric crashlytics
     }
     dependencies {
        classpath 'io.fabric.tools:gradle:1.21.6' //only when you use fabric crashlytics
     }
 }

allprojects {
    repositories {
        jcenter()

        maven { url 'https://mint.splunk.com/gradle/' }
        
        maven { url 'https://maven.fabric.io/public' } //only when you use fabric crashlytics
    }
}
```

Add a dependency on `CrashLog` to your `build.gradle` file:

```groovy
dependencies {
    compile "com.icapps.crashreporter:crashlog:<version>"
		
	//One or more:
	compile "com.icapps.crashreporter:crashlog-crittercism:<version>"
    compile "com.icapps.crashreporter:crashlog-crashlytics:<version>"
	compile "com.icapps.crashreporter:crashlog-googleanalytics:<version>"
	
	//When you use the crittercism logger
	compile 'com.crittercism:crittercism-android-agent:<version>' //The library currently works verified against 5.6.4
	
	//When you use the google analytics logger
	compile 'com.google.android.gms:play-services-analytics:<version>' //The library is currently verified against 11.0.2

	//When you use the crashlytics crash logger
	compile('com.crashlytics.sdk.android:crashlytics:<version>@aar') { //The library is currently verified against 2.6.8
		transitive = true;
	}

}
```

For Fabric's Crashlytics, apply the plugin in your `build.gradle` file:
```groovy
apply plugin: 'io.fabric'
```

For Fabric's Crashlytics, add meta-data to your AndroidManifest file:
```groovy
<meta-data android:name="io.fabric.ApiKey" android:value="xxx" />
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
- CrashlyticsCrashReporter
