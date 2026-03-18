package com.adrzdv.mtocp.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.adrzdv.mtocp.R

@Composable
fun SaveButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_save_32_white),
            contentDescription = stringResource(R.string.save_string)
        )
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(
            painter = painterResource(R.drawable.ic_back_32_white),
            contentDescription = stringResource(R.string.menu_string)
        )
    }
}