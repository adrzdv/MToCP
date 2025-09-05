package com.adrzdv.mtocp.ui.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.adrzdv.mtocp.ui.screen.StartMenuScreen

class MainMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        val version = packageManager.getPackageInfo(packageName, 0).versionName ?: "unknown"

        enableEdgeToEdge()
        setContent {
            StartMenuScreen(
                onStartRevisionClick = {
                    startActivity(
                        Intent(
                            this, StartRevisionActivity::class.java
                        )
                    )
                },
                onOpenViolationCatalogClick = {
                    startActivity(
                        Intent(
                            this, CatalogActivity::class.java
                        )
                    )
                },
                onServiceMenuClick = {
                    startActivity(
                        Intent(
                            this, ServiceMenuActivity::class.java
                        )
                    )
                },
                onExitClick = {
                    finish()
                },
                onRequestWebClick = {
                    startActivity(
                        Intent(
                            this,
                            RequestWebActivity::class.java
                        )
                    )
                },
                appVersion = version
            )
        }
    }
}