# grading-sdk-android-example

Open Android studio and create a new **"Empty views Activity Project"**

There is some kind of incompartibillity with appcompat

## Installing the SDK

[Download pandas-grading-sdk.aar](/app/libs/pandas-grading-sdk.aar) file and copy it in the `{projectroot}/app/libs/` folder.

On the `{projectroot}/build.gradle` file add:

```apache
buildscript {
    repositories {
        google()
    }
    ext {
        kotlin_version = "1.9.0"
        lifecycle_version = "2.6.1"
        nav_version = "2.5.3"
        camerax_version = "1.1.0-beta03"
        retrofit_version = "2.9.0"
        coroutine_version = "1.6.4"
        biometric_version = "1.2.0-alpha05"
        sandwich_version = "1.3.5"
        lottie_version = "3.4.0"
        play_services_map_version = "18.1.0"
        play_services_location = "21.0.1"
        amazon_s3_client_version = "0.25.0-beta"
        ably_version = "1.2.30"
    }
    dependencies {
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
    }
}
```

On the `{projectroot}/app/build.gradle` file and add the following lines:

In the `plugins` section

```apache
plugins {
    ....
    id 'androidx.navigation.safeargs'
}

```

In the `depedencies` section

```apache
dependencies {

    implementation files("libs/pandas-grading-sdk.aar")

    // Amazon S3 Client
    implementation "aws.sdk.kotlin:s3:$amazon_s3_client_version"

    // Ably realtime SDK
    implementation "io.ably:ably-android:$ably_version"

    // Navigation
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
    implementation "androidx.navigation:navigation-fragment:$nav_version"
    implementation "androidx.navigation:navigation-ui:$nav_version"
    implementation "androidx.navigation:navigation-compose:$nav_version"
    implementation "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"

    // Biometrics
    implementation "androidx.biometric:biometric-ktx:$biometric_version"

    // CameraX core library
    implementation "androidx.camera:camera-core:$camerax_version"

    // CameraX Camera2 extensions
    implementation "androidx.camera:camera-camera2:$camerax_version"

    // CameraX Lifecycle library
    implementation "androidx.camera:camera-lifecycle:$camerax_version"

    // CameraX View class
    implementation "androidx.camera:camera-view:$camerax_version"

    // UI for PageViewer card swipe indicator
    implementation "com.tbuonomo:dotsindicator:4.3"

    // UI for dots loader
    implementation "com.airbnb.android:lottie:$lottie_version"

    // Object Detection
    implementation 'org.tensorflow:tensorflow-lite-task-vision-play-services:0.4.2'
    implementation 'com.google.android.gms:play-services-tflite-gpu:16.1.0'

    // Face Detection
    implementation 'com.google.mediapipe:solution-core:latest.release'
    implementation 'com.google.mediapipe:facedetection:latest.release'

    // Wrapper for Log
    implementation 'com.jakewharton.timber:timber:5.0.1'

    // Retrofit
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"

    // Coroutine
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version"

    // A standard for network calls
    implementation "com.github.skydoves:sandwich:$sandwich_version"

    // Dependency to include Maps SDK for Android
    implementation "com.google.android.gms:play-services-maps:$play_services_map_version"

    // Google Location
    implementation "com.google.android.gms:play-services-location:$play_services_location"

    implementation 'com.google.android.libraries.places:places:3.1.0'
}
```

In the `android` section

```apache
    buildFeatures {
        viewBinding true
    }
```

## Using the SDK

Add the folloing imports:

```apache
import io.pandas.grading.Grading
import io.pandas.grading.config.ConfigData
```

Config once:

```apache
Grading.setConfig(applicationContext, ConfigData())
```

Then start a new grading activity

```apache
Grading.start(this)
```
