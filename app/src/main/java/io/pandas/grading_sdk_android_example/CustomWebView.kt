package io.pandas.grading_sdk_android_example

import android.app.Activity
import android.net.http.SslError
import android.webkit.PermissionRequest
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

public class CustomWebView() : WebViewClient() {
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError?) {
        handler.proceed() // Ignore SSL certificate errors
    }
}
class CustomWebChromeClient(private val parentActivity: Activity) : WebChromeClient() {

    private val permissionMapping: Map<String, String> = mapOf(
        "android.webkit.resource.VIDEO_CAPTURE" to android.Manifest.permission.CAMERA,
        "android.webkit.resource.AUDIO_CAPTURE" to android.Manifest.permission.RECORD_AUDIO,
        "android.webkit.resource.GEOLOCATION" to android.Manifest.permission.ACCESS_FINE_LOCATION,
        "android.webkit.resource.FILESYSTEM" to android.Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    private var permissionRequest: PermissionRequest? = null


    override fun onPermissionRequest(request: PermissionRequest) {
        permissionRequest = request // Store the permission request
        if(request.resources == null)
            return;
        val permissions = request.resources.mapNotNull {p -> permissionMapping[p] }
        var hasEveryRight = permissions?.all{ p -> ContextCompat.checkSelfPermission(parentActivity, p) == PackageManager.PERMISSION_GRANTED} == true
        if (!hasEveryRight) {
            ActivityCompat.requestPermissions(
                parentActivity,
                permissions.toTypedArray(),
                1
            )
        } else {
            request.grant(request.resources);
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            val allGranted = grantResults.all { r -> r == PackageManager.PERMISSION_GRANTED }
            if (allGranted) {
                permissionRequest?.grant(permissionRequest?.resources)
            } else {
                permissionRequest?.deny() // Deny the request if permission was not granted
            }
    }
}
