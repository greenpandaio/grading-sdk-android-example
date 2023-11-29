package io.pandas.grading_sdk_android_example

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!initPandasGradingDeeplinkConfig()) {
            configureSdk()
        }
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.startbtn).setOnClickListener { Grading.start(this) }
        findViewById<Button>(R.id.viewImeiBtn).setOnClickListener { startActivity(Intent(Settings.ACTION_DEVICE_INFO_SETTINGS)) }
        findViewById<Button>(R.id.openWebViewBtn).setOnClickListener {
            val myIntent = Intent(baseContext, WebAppActivity ::class.java)
            val url = findViewById<EditText>(R.id.webviewUrl).text.toString()
            myIntent.putExtra("url", url)
            startActivity(myIntent)
        }
    }
    private fun configureSdk(sessionId: String? = null ,flow: Flows = Flows.HOME ){
        val config = ConfigData(
            evaluations = arrayListOf(
                ConfigEvaluationNames.DIGITIZER,

            ),
            colors = Colors(primary = "#222222"),
            partner = Partner(
                id = "eb7c5e49-a4af-4426-93e4-4d1dd800b9ad",
                name = "pandas",
                storeLocationsURL = "https://www.pandas.io/el-GR/map"
            ),
            environment = Environment.STAGING,
            flow = flow,
            sessionId = sessionId,
            deviceImei = null,
            dropOffOptions = arrayListOf(
                DropOffOptions.AT_STORE,
                DropOffOptions.COURIER_AT_STORE
            ),
            contactUs = true,
            tutorial = true,
            faq = true,
            manifesto = true,
            emailSubmission = true,
            ourStory = true,

            )
        Grading.setConfig(applicationContext, config)
    }
    private fun initPandasGradingDeeplinkConfig(): Boolean {
        val intent = intent
        if (intent != null && intent.data != null) {
            val deepLinkUri = intent.data

            if(!deepLinkUri.toString().startsWith("https://m.pandas.io")) { return false }

            deepLinkUri?.pathSegments?.let { uriSegments ->
                if(uriSegments[0] == "grade") {
                    val partnerId = uriSegments[1]
                    val sessionId = deepLinkUri.getQueryParameter("sessionId")
                    configureSdk(sessionId,Flows.STORE)
                    Grading.start(this)
                }
            }
            return true
        }
        return false
    }
}