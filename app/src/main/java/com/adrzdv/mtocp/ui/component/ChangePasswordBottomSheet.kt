package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.ChangePasswordBottomSheetViewModel
import com.adrzdv.mtocp.ui.viewmodel.ServiceViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordBottomSheetComp(
    viewModel: ChangePasswordBottomSheetViewModel,
    serviceViewModel: ServiceViewModel,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = { if (!isLoading) onDismiss() },
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.change_password),
                style = AppTypography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            var oldPassword by remember { mutableStateOf("") }
            var newPassword by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }

            OutlinedTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                label = { Text("Old password") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New password") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm password") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        viewModel.changePassword()
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isLoading) "Loading..." else "Save")
            }

            Spacer(Modifier.height(8.dp))
            TextButton(
                onClick = { if (!isLoading) onDismiss() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Cancel")
            }
        }
    }
}