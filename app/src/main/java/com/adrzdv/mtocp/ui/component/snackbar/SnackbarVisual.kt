package com.adrzdv.mtocp.ui.component.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

sealed interface AppSnackbar : SnackbarVisuals {
    override val message: String
}

data class ErrorSnackbar(
    override val message: String,
) : AppSnackbar {
    override val actionLabel: String? = null
    override val withDismissAction: Boolean = false
    override val duration: SnackbarDuration = SnackbarDuration.Short
}

data class InfoSnackbar(
    override val message: String
) : AppSnackbar {
    override val actionLabel: String? = null
    override val withDismissAction: Boolean = false
    override val duration: SnackbarDuration = SnackbarDuration.Short
}