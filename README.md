# grading-sdk-android-example

Open Android studio and create a new **"Empty views Activity Project"**

## Installing the SDK
### Add private maven repository.
Add the PandasGrading private repository to your `settings.gradle` file along with your github access credentials (You can hardcode them for debug purposes but absolutely avoid pushing to your repo)
Use environment variables or project properties instead.

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/greenpandaio/grading-sdk-android")
            credentials {
                username = project.findProperty("gpr.user") ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") ?: System.getenv("TOKEN")
            }
        }
    }
}
```

### Add pandas-grading-sdk depedency.
In your app level `build.gradle` add `'io.pandas.grading:pandas-grading-sdk:0.1.1'` as a depedency

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

## Compose compatibility
For newer apps that use modern android compose follow [this](https://developer.android.com/jetpack/compose/libraries) guide on how to integrate the library. 
### Example:
```
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Grading.setConfig(applicationContext, ConfigData(
            evaluations = listOf(
                ConfigEvaluationNames.DIGITIZER,
                ConfigEvaluationNames.IMEI),
            colors = Colors(primary = "#1a1a1a")
        ))


        setContent {
            ComposeEmptyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GradingSDKButton(applicationContext)
                    }
                }
            }
        }
    }

}

@Composable
fun GradingSDKButton(context: Context) {
    Button(onClick = {
        Grading.start(context)
    }
    ) {
        Text("Click Me")
    }
}
```

Then in `AndroidManifest.xml` inside the `<application>` tag add:
```
<activity
   android:name="io.pandas.grading.MainActivity"
   android:theme="@style/Theme.MaterialComponents">
</activity>
```