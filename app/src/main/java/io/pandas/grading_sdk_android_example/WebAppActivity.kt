package io.pandas.grading_sdk_android_example

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class WebAppActivity : AppCompatActivity() {
    lateinit var myWebView : WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_app)
        this.myWebView = findViewById(R.id.webappView);
        val settings: WebSettings = this.myWebView.settings
        settings.javaScriptEnabled = true
        settings.allowContentAccess = true
        settings.domStorageEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        this.myWebView.settings.mediaPlaybackRequiresUserGesture = false
        this.myWebView.webViewClient = CustomWebView()
        this.myWebView.setWebChromeClient(CustomWebChromeClient())
        val url = intent.getStringExtra("url").toString();
        Log.d("webViewUrl", url);
        this.myWebView.loadUrl(url);
    }
    override fun onBackPressed() {
        if (this.myWebView.copyBackForwardList().currentIndex> 0) {
            this.myWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}