package com.adrzdv.mtocp.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.adrzdv.mtocp.App
import com.adrzdv.mtocp.data.repository.UserDataStorage
import com.adrzdv.mtocp.ui.navigation.NavigationGraph
import com.adrzdv.mtocp.ui.screen.SplashScreen

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val userDataStorage = UserDataStorage(prefs)
        val ver = packageManager.getPackageInfo(packageName, 0)?.versionName ?: "unknown"
        val username = prefs.getString("username", "null")
        val appDependencies = (application as App).appDependencies

        setContent {
            val activity = this
            NavigationGraph(
                appDependencies = appDependencies,
                activity = activity,
                hasToken = userDataStorage.getToken()?.isNotEmpty() ?: false,
                version = ver,
                username = username ?: "Unknown",
                userDataStorage = userDataStorage
            )
        }
    }
}