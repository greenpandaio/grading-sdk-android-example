package io.pandas.grading_sdk_android_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class WebAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_app)
        val myWebView: WebView = findViewById(R.id.webappView)
        val settings: WebSettings = myWebView.settings
        settings.javaScriptEnabled = true
        settings.allowContentAccess = true
        settings.domStorageEnabled = true
        myWebView.webViewClient = WebViewClient()
        val url = intent.getStringExtra("url").toString();
        Log.d("webViewUrl", url);
        myWebView.loadUrl(url);
    }
}