package com.adrzdv.mtocp.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.adrzdv.mtocp.ui.screen.NewRevisionScreen

class NewRevisionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        enableEdgeToEdge()
        setContent {
            NewRevisionScreen(
                onBackClick = { finish() },
                onTrainRevisionClick = {},
                onTicketOfficeRevisionClick = {},
                onCoachRevisionClick = {}
            )
        }
    }
}