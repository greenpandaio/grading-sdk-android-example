package io.pandas.grading_sdk_android_example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import io.pandas.grading.Grading
import io.pandas.grading.config.ConfigData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Grading.setConfig(applicationContext, ConfigData())

        findViewById<Button>(R.id.startbtn).setOnClickListener{ Grading.start(this)}
        findViewById<Button>(R.id.viewImeiBtn).setOnClickListener{startActivity(Intent(Settings.ACTION_DEVICE_INFO_SETTINGS))}
    }
}