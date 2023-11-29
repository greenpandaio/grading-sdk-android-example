package io.pandas.grading_sdk_android_example

import android.net.http.SslError
import android.os.Build
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient


public class CustomWebView() : WebViewClient() {
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError?) {
        handler.proceed() // Ignore SSL certificate errors
    }
}
public class  CustomWebChromeClient() : WebChromeClient()
{
    override fun onPermissionRequest(request: PermissionRequest) {
            request.grant(request.resources)
    }
}