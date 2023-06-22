package io.pandas.grading_sdk_android_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import io.pandas.grading.Grading
import io.pandas.grading.config.ConfigData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Grading.setConfig(applicationContext, ConfigData())
        var btn = findViewById<Button>(R.id.startbtn)
        btn.setOnClickListener{ Grading.start(this)}
    }
}