package com.adrzdv.mtocp.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.adrzdv.mtocp.ui.screen.RequestWebScreen

class RequestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val username = prefs.getString("username","null")

        setContent {
            RequestWebScreen(
                username = username ?: "Unknown",
                onBackClick = { finish() }
            )
        }
    }
}