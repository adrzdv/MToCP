package com.adrzdv.mtocp.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.adrzdv.mtocp.data.repository.UserDataStorage
import com.adrzdv.mtocp.ui.screen.SplashScreen

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val userDataStorage = UserDataStorage(prefs)

        setContent {
            SplashScreen(
                onTimeout = {
                    val token = userDataStorage.getToken()
                    val nextActivity = if (!token.isNullOrEmpty()) {
                        MainMenuActivity::class.java
                    } else {
                        RegisterActivity::class.java
                    }
                    startActivity(Intent(this, nextActivity))
                    finish()
                }
            )
        }
    }
}