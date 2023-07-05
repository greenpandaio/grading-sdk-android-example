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

## Configuring the SDK
Initially configuration is optional. If nothing is configured the SDK will run with all the default values.

### Base config
Basic configuration is done by passing the `ConfigData` object to the `Grading.setConfig` function.
For example to customise the assessment tests that will be included and the primary color you can pass the following config object.
```
         val config = ConfigData(
            evaluations = arrayListOf(
                ConfigEvaluationNames.DIGITIZER,
                ConfigEvaluationNames.FRONT_CAMERA,
                ConfigEvaluationNames.MULTITOUCH
            ),
            colors = Colors(primary = "#1a1a1a")
        )
        Grading.setConfig(applicationContext, config)
```
Colors, Images, Fonts , and Strings can all be modified by inserting the corresponding .xml files in the values && drawable directories
 
`/app/src/main/res/values/{valuesFile}.xml`

### Strings 
Default language strings reside in the values folder. You can copy [pandas-sdk-strings.xml](/app/src/main/res/values/pandas-sdk-strings.xml) file to your values folder and edit the entries.
`/app/src/main/res/values/pandas-sdk-strings.xml`
#### Multilingual support
Enter the values for each language to the language corresponding folder by locale. For example for Greek language support insert a [pandas-sdk-strings.xml ](/app/src/main/res/values-el/pandas-sdk-strings.xml)file to the following path:

`/app/src/main/res/values-el/pandas-sdk-strings.xml`

### Colors
Copy the [pandas-sdk-colors.xml](/app/src/main/res/values/pandas-sdk-colors.xml) to values folder and edit the corresponding entries.

### Images
You can replace any pandas grading image with your preferred image. Just place the image with the corresponding name in the drawable folder.

`/app/src/main/res/drawable`

You can see a list of all supported drawables [here](/app/src/main/res/drawable).

### Font scheme

The font scheme is updated by providing a **.ttf/.otf** file in **res/font** directory. 

**Sample**

Check the latest font names [here](/app/src/main/res/font).

```
// lib: file structure

res/
   font/
       pandas_grading_primary_bold.otf
       pandas_grading_primary_regular.otf
       pandas_grading_secondary_bold.ttf
       pandas_grading_secondary_extra_bold.ttf
       pandas_grading_secondary_extra_light.ttf
       pandas_grading_secondary_light.ttf
       pandas_grading_secondary_medium.ttf
       pandas_grading_secondary_regular.ttf
       pandas_grading_secondary_semi_bold.ttf
       pandas_grading_tertiary_bold.ttf
       pandas_grading_tertiary_medium.ttf

```

**Let's override the primary bold font from the app, like this**

Copy-paste your font into the **res/font** directory and rename the file according to the font you want to override.

```
// app: file structure

res/
   font/
       ...
       pandas_grading_primary_bold.otf
       ...

```
