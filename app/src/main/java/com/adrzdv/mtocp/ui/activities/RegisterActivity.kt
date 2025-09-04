package com.adrzdv.mtocp.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.adrzdv.mtocp.data.api.RetrofitClient
import com.adrzdv.mtocp.data.repository.AuthRepositoryImpl
import com.adrzdv.mtocp.data.repository.UserDataStorage
import com.adrzdv.mtocp.ui.screen.RegisterScreen
import com.adrzdv.mtocp.ui.viewmodel.AssistedViewModelFactory
import com.adrzdv.mtocp.ui.viewmodel.AuthViewModel

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val userDataStorage = UserDataStorage(prefs)

        val authRepo = AuthRepositoryImpl(RetrofitClient.authApi, userDataStorage)

        val viewModel = ViewModelProvider(
            this,
            AssistedViewModelFactory { AuthViewModel(authRepo) }
        ).get(AuthViewModel::class.java)


        enableEdgeToEdge()
        setContent {
            RegisterScreen(viewModel = viewModel)
        }
    }
}