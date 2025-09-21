package com.adrzdv.mtocp.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.adrzdv.mtocp.App
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.ui.screen.ServiceScreen
import com.adrzdv.mtocp.util.DirectoryHandler

class ServiceActivity : AppCompatActivity() {
    private lateinit var filePickerLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        filePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val fileUri: Uri? = result.data?.data
                fileUri?.let { uri ->
                    App.getImportManager().importFromJson(
                        this,
                        uri
                    ) { message ->
                        App.showToast(this, message)

                    }
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            ServiceScreen(
                onCleanRepositoryClick = { onResult ->
                    cleanDirs(onResult)
                },
                onLoadCatalog = ::loadDataFromFile,
                onBackClick = { finish() }
            )
        }
    }

    private fun loadDataFromFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/json", "text/json"))
            addCategory(Intent.CATEGORY_OPENABLE)
        }

        try {
            filePickerLauncher.launch(Intent.createChooser(intent, "Выберете файл:"))
        } catch (ex: ActivityNotFoundException) {
            App.showToast(this, MessageCodes.FILE_MANAGER_ERROR.messageTitle)
        }
    }

    private fun cleanDirs(onResult: (Boolean) -> Unit) {
        val success = DirectoryHandler.cleanDirectories()
        onResult(success)
    }
}