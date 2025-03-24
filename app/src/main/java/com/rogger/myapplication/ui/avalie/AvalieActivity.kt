package com.rogger.myapplication.ui.avalie

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.rogger.myapplication.R

class AvalieActivity :AppCompatActivity(){
    //private lateinit var binding: ActivityAvalieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_avalie)

       val webView = findViewById<WebView>(R.id.web_view)
        webView.loadUrl("https://play.google.com/store/apps/details?id=com.einnovation.temu")

    }
}