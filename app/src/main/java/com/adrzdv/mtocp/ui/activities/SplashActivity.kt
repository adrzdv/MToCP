package com.adrzdv.mtocp.ui.activities

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.adrzdv.mtocp.App
import com.adrzdv.mtocp.ui.navigation.NavigationGraph

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        val ver = packageManager.getPackageInfo(packageName, 0)?.versionName ?: "unknown"
        val appDependencies = (application as App).appDependencies

        setContent {
            NavigationGraph(
                appDependencies = appDependencies,
                version = ver
            )
        }
    }
}