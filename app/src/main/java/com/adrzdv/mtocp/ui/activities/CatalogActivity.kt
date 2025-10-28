package com.adrzdv.mtocp.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.ui.screen.InfoCatalogScreen
import com.adrzdv.mtocp.ui.viewmodel.CompanyViewModel
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel
import com.adrzdv.mtocp.ui.viewmodel.TrainInfoViewModel
import com.adrzdv.mtocp.ui.viewmodel.ViewModelFactoryProvider
import com.adrzdv.mtocp.ui.viewmodel.ViolationViewModel

class CatalogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        enableEdgeToEdge()
        setContent {
            InfoCatalogScreen(
                onBackClick = { finish() },
                violationViewModel = ViewModelProvider(
                    this,
                    ViewModelFactoryProvider.provideFactory()
                ).get(
                    ViolationViewModel::class.java
                ),
                revisionTypes = RevisionType.getListOfTypes(),
                depotViewModel = ViewModelProvider(
                    this,
                    ViewModelFactoryProvider.provideFactory()
                ).get(
                    DepotViewModel::class.java
                ),
                companyViewMode = ViewModelProvider(
                    this,
                    ViewModelFactoryProvider.provideFactory()
                ).get(
                    CompanyViewModel::class.java
                ),
                trainInfoViewModel = ViewModelProvider(
                    this,
                    ViewModelFactoryProvider.provideFactory()
                ).get(
                    TrainInfoViewModel::class.java
                )
            )
        }
    }
}