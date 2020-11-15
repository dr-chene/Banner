package com.example.bannerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        findViewById<WebView>(R.id.web).loadUrl(
                intent.getStringExtra("url") ?: "https://cn.bing.com/"
        )
    }
}