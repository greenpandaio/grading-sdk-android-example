package io.pandas.grading_sdk_android_example
import android.content.SharedPreferences
import android.content.Context
import android.R.attr.value
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.pandas.grading.Grading
import io.pandas.grading.config.ConfigData
import io.pandas.grading.config.ConfigEvaluationNames
import io.pandas.grading.config.data_access.Colors
import io.pandas.grading.config.data_access.DropOffOptions
import io.pandas.grading.config.data_access.Environment
import io.pandas.grading.config.data_access.Flows
import io.pandas.grading.config.data_access.Partner


class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!initPandasGradingDeeplinkConfig()) {
            configureSdk()
        }

        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.webviewUrl)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val lastEnteredUrl = sharedPreferences.getString("lastUrl", "https://webapp-stg.pandas.io/t-mobile-demo?webview=true")
        editText.setText(lastEnteredUrl)
        findViewById<Button>(R.id.startbtn).setOnClickListener { Grading.start(this) }
        findViewById<Button>(R.id.viewImeiBtn).setOnClickListener { startActivity(Intent(Settings.ACTION_DEVICE_INFO_SETTINGS)) }

        findViewById<Button>(R.id.openWebViewBtn).setOnClickListener {
            val myIntent = Intent(baseContext, WebAppActivity::class.java)
            val url = editText.text.toString()
            saveUrl(url)
            myIntent.putExtra("url", url.toString())
            startActivity(myIntent)
        }
    }

    private fun saveUrl(url: String) {
        with(sharedPreferences.edit()) {
            putString("lastUrl", url)
            apply()
        }
    }
        private fun configureSdk(sessionId: String? = null, flow: Flows = Flows.HOME) {
            val config = ConfigData(
                evaluations = arrayListOf(
                    ConfigEvaluationNames.DIGITIZER,
                    ConfigEvaluationNames.SOUND_PERFORMANCE,
                    ConfigEvaluationNames.DEVICE_MOTION,
                    ConfigEvaluationNames.MULTITOUCH,
                    ConfigEvaluationNames.FRONT_CAMERA,
                    ConfigEvaluationNames.BACK_CAMERA,
                    ConfigEvaluationNames.FACE_ID,
                    ),
                colors = Colors(primary = "#E20074"),
                partner = Partner(
                    id = "82562551-eb28-4801-a0ca-e3b5c79c5101",
                    name = "t_mobile_demo",
                    storeLocationsURL = "https://www.t-mobile.com/stores/wa"
                ),
                environment = Environment.STAGING,
                flow = flow,
                sessionId = sessionId,
                deviceImei = null,
                dropOffOptions = arrayListOf(
                    DropOffOptions.AT_STORE
                ),
                contactUs = false,
                tutorial = false,
                faq = false,
                manifesto = false,
                emailSubmission = false,
                ourStory = false,
                )
            Grading.setConfig(applicationContext, config)
        }

        private fun initPandasGradingDeeplinkConfig(): Boolean {
            val intent = intent
            if (intent != null && intent.data != null) {
                val deepLinkUri = intent.data

                if (!deepLinkUri.toString().startsWith("https://m.pandas.io")) {
                    return false
                }

                deepLinkUri?.pathSegments?.let { uriSegments ->
                    if (uriSegments[0] == "grade") {
                        val partnerId = uriSegments[1]
                        val sessionId = deepLinkUri.getQueryParameter("sessionId")
                        configureSdk(sessionId, Flows.STORE)
                        Grading.start(this)
                    }
                }
                return true
            }
            return false
        }
    }