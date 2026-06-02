package com.clinic.radar

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private var localServer: LocalWebServer? = null

    companion object {
        private const val LOCAL_PORT = 8787
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        localServer = LocalWebServer(this, LOCAL_PORT)
        localServer?.start()

        webView = findViewById(R.id.webView)

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        webView.webViewClient = WebViewClient()

        webView.loadUrl("http://localhost:$LOCAL_PORT/")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        localServer?.stop()
    }
}
