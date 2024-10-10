package io.pandas.grading_sdk_android_example
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import io.pandas.grading.Grading
import io.pandas.grading.GradingEventsListener
import io.pandas.grading.config.ConfigData
import io.pandas.grading.config.ConfigEvaluationNames
import io.pandas.grading.config.data_access.Colors
import io.pandas.grading.config.data_access.DropOffOptions
import io.pandas.grading.config.data_access.Environment
import io.pandas.grading.config.data_access.Flow
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
        val lastEnteredUrl = sharedPreferences.getString("lastUrl", "https://webapp-stg.pandas.io/pandas?webview=true&locale=el-gr")
        editText.setText(lastEnteredUrl)
        findViewById<Button>(R.id.startbtn).setOnClickListener {
            Grading.start(
                context = this,
                flowType = Flow.Type.ELIGIBILITY_PEER,
                imei = "352836110046381",
                sessionId = null
            )
        }
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
        private fun configureSdk(sessionId: String? = null, flow: Flow.Type = Flow.Type.ELIGIBILITY) {
//            val backgroundImage = BitmapFactory.decodeResource(resources, R.drawable.pandas_grading_splash_screen)
            val digitizerBackgroundImage =
                BitmapFactory.decodeResource(resources, R.drawable.digitizer_custom)
            val qrLogo = ContextCompat.getDrawable(this, R.drawable.pandas_grading_qr_logo)
            val config = ConfigData(
                evaluations = arrayListOf(
                    ConfigEvaluationNames.DIGITIZER,
                    ConfigEvaluationNames.DEVICE_MOTION,
                    ConfigEvaluationNames.MULTITOUCH,
                    ConfigEvaluationNames.SOUND_PERFORMANCE,
                    ConfigEvaluationNames.FRONT_CAMERA,
                    ConfigEvaluationNames.BACK_CAMERA,
                    ConfigEvaluationNames.FACE_ID,
                ),
                colors = Colors(
//                primary = "#198754",
                    primary = "#E20074",
                    background = "#FFFFFF", //defaults to primary
                    loader = "#000000" //defaults to #B3B3B3
                ),
                partner = Partner(
                    id = "eb7c5e49-a4af-4426-93e4-4d1dd800b9ad",
                    name = "greenpandas",
                    code = "pandas",
                    storeLocationsURL = "https://www.pandas.io/el-GR/map"
                ),
                environment = Environment.STAGING,
                deviceImei = "352836110046381",
                dropOffOptions = arrayListOf(
                    DropOffOptions.AT_STORE,
                    DropOffOptions.COURIER_AT_STORE
                ),
                emailSubmission = false,
//                backgroundImage = backgroundImage,
                digitizerBackgroundImage = digitizerBackgroundImage,
                showTerms = true,
                qrLogo = qrLogo
            )
            Grading.setConfig(
                config = config,
                eventsListener = object : GradingEventsListener {
                    override fun onEvent(event: GradingEventsListener.Event) {
                        // TODO handle events
                        Toast.makeText(
                            applicationContext,
                            "TODO handle event: $event",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
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
                        configureSdk(sessionId, Flow.Type.ELIGIBILITY)
                        Grading.start(
                            context = this,
                            sessionId = sessionId,
                            flowType = Flow.Type.ELIGIBILITY,
                            imei = "352836110046381"
                        )
                    }
                }
                return true
            }
            return false
        }
    }