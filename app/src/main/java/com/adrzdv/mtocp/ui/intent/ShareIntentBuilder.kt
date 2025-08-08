package com.adrzdv.mtocp.ui.intent

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

object ShareIntentBuilder {
    fun shareZip(context: Context, file: File): Intent {
        val uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            file
        );

        return Intent(Intent.ACTION_SEND).apply {
            type = "application/zip"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
}