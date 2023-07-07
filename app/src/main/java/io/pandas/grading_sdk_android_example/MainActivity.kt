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
            colors = Colors(primary = "#cccccc")
        )
        Grading.setConfig(applicationContext, config)

        findViewById<Button>(R.id.startbtn).setOnClickListener{ Grading.start(this)}
        findViewById<Button>(R.id.viewImeiBtn).setOnClickListener{startActivity(Intent(Settings.ACTION_DEVICE_INFO_SETTINGS))}
    }
}