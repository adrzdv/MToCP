package com.adrzdv.mtocp.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import com.adrzdv.mtocp.BuildConfig
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.api.RetrofitClient
import com.adrzdv.mtocp.data.api.UpdateManager
import com.adrzdv.mtocp.data.model.UpdateConfig
import com.adrzdv.mtocp.data.repository.UserDataStorage
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.snackbar.ErrorSnackbar
import com.adrzdv.mtocp.ui.screen.SplashScreen
import com.adrzdv.mtocp.util.DirectoryHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val userDataStorage = UserDataStorage(prefs)

        setContent {
            val showUpdateDialog = remember { mutableStateOf<UpdateConfig?>(null) }
            val isDownloading = remember { mutableStateOf(false) }
            val snackbarHostState = remember { SnackbarHostState() }

            Box(modifier = Modifier.fillMaxSize()) {
                SplashScreen(onTimeout = { })

                CustomSnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )

                showUpdateDialog.value?.let { config ->
                    AlertDialog(
                        onDismissRequest = { },
                        title = { Text(stringResource(R.string.update_available)) },
                        text = { Text(stringResource(R.string.new_version) + ": ${config.newVersionCode}\n\n${config.releaseNotes}") },
                        confirmButton = {
                            Button(
                                enabled = !isDownloading.value,
                                onClick = {
                                    isDownloading.value = true
                                    handleUpdate(
                                        config,
                                        userDataStorage
                                    ) { message ->
                                        snackbarHostState.showSnackbar(
                                            visuals = ErrorSnackbar(message)
                                        )
                                        delay(2000)
                                    }
                                }
                            ) {
                                Text(
                                    if (isDownloading.value) stringResource(R.string.loading)
                                    else stringResource(R.string.update)
                                )
                            }
                        },
                        dismissButton = {
                            TextButton(
                                enabled = !isDownloading.value,
                                onClick = { proceedNext(userDataStorage) }
                            ) {
                                Text(stringResource(R.string.later))
                            }
                        }
                    )
                }
            }

            LaunchedEffect(Unit) {
                try {
                    val response = RetrofitClient.updaterApi.getUpdateInfo()

                    if (response.isSuccessful) {
                        val config = response.body()

                        if (config != null &&
                            config.newVersionCode != BuildConfig.VERSION_NAME
                        ) {
                            showUpdateDialog.value = config
                        } else {
                            delay(1000)
                            proceedNext(userDataStorage)
                        }
                    } else {
                        proceedNext(userDataStorage)
                    }

                } catch (e: Exception) {
                    proceedNext(userDataStorage)
                }
            }
        }
    }

    private fun handleUpdate(
        config: UpdateConfig,
        storage: UserDataStorage,
        onError: suspend (String) -> Unit
    ) {
        lifecycleScope.launch {
            val directory = File(DirectoryHandler.UPDATER_DIRECTORY)

            if (!directory.exists()) directory.mkdirs()

            val apkFile = File(directory, "app.apk")

            val success = UpdateManager.downloadApk(config.apkUrl, apkFile)

            if (success) {
                UpdateManager.installApk(this@SplashActivity, apkFile)
            } else {
                onError(getString(R.string.download_error))
                proceedNext(storage)
            }
        }
    }

    private fun proceedNext(storage: UserDataStorage) {
        val token = storage.getToken()
        val nextActivity = if (!token.isNullOrEmpty()) {
            MainMenuActivity::class.java
        } else {
            RegisterActivity::class.java
        }
        startActivity(Intent(this, nextActivity))
        finish()
    }
}