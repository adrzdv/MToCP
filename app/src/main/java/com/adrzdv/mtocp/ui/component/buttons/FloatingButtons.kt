package com.adrzdv.mtocp.ui.component.buttons

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun FloatingSaveButton(
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        contentColor = Color.White,
        containerColor = AppColors.MAIN_GREEN.color,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_save_32_white),
                contentDescription = stringResource(R.string.save_string)
            )
        },
        text = {
            Text(
                text = stringResource(R.string.save_string),
                style = AppTypography.labelLarge
            )
        }
    )
}