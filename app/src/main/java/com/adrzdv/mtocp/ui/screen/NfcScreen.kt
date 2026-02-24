package com.adrzdv.mtocp.ui.screen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.buttons.CustomOutlinedButton
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.NfcJsonReceiverViewModel

@Composable
fun NfcScreen(
    nfcJsonReceiverViewModel: NfcJsonReceiverViewModel,
    activity: Activity
) {
    val isTransferring by nfcJsonReceiverViewModel.isTransferring.observeAsState(false)
    val receivedJson by nfcJsonReceiverViewModel.receivedJson.observeAsState("")

    LaunchedEffect(receivedJson) {
        Log.d("NfcScreen", "Received JSON changed: $receivedJson")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = stringResource(R.string.nfc_help_info),
            style = AppTypography.bodyMedium
        )

        if (isTransferring) {
            CircularProgressIndicator(modifier = Modifier.size(120.dp))
            Text(
                text = stringResource(R.string.nfc_sender),
                style = AppTypography.bodyMedium
            )
        } else {
            Text(
                text = stringResource(R.string.nfc_receiver),
                style = AppTypography.bodyMedium
            )
        }

        if (receivedJson.isNotEmpty()) {
            Text(
                text = stringResource(R.string.nfc_file_received),
                style = AppTypography.bodyMedium
            )
        }

        CustomOutlinedButton(
            onClick = {
                nfcJsonReceiverViewModel.startTransfer(activity)
            },
            text = stringResource(R.string.send)
        )
    }
}