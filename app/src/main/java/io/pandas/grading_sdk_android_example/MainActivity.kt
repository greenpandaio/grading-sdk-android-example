package io.pandas.grading_sdk_android_example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.Settings
import android.widget.Button
import io.pandas.grading.Grading
import io.pandas.grading.config.ConfigData
import io.pandas.grading.config.ConfigEvaluationNames
import io.pandas.grading.config.data_access.Colors
import io.pandas.grading.config.data_access.DropOffOptions
import io.pandas.grading.config.data_access.Environment
import io.pandas.grading.config.data_access.Partner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val config = ConfigData(
            evaluations = arrayListOf(
                ConfigEvaluationNames.DIGITIZER,
                ConfigEvaluationNames.FRONT_CAMERA,
                ConfigEvaluationNames.MULTITOUCH
            ),
            colors = Colors(primary = "#cccccc"),
            partner = Partner(
                id = "eb7c5e49-a4af-4426-93e4-4d1dd800b9ad",
                name = "pandas",
                storeLocationsURL = "https://www.pandas.io/el-GR/map"
            ),
            environment = Environment.STAGING,
            deviceImei = "350504685294602",
            dropOffOptions = arrayListOf(
                DropOffOptions.AT_STORE,
                DropOffOptions.COURIER_AT_STORE
            )
        )
        Grading.setConfig(applicationContext, config)

        findViewById<Button>(R.id.startbtn).setOnClickListener { Grading.start(this) }
        findViewById<Button>(R.id.viewImeiBtn).setOnClickListener { startActivity(Intent(Settings.ACTION_DEVICE_INFO_SETTINGS)) }
    }
}