package com.adrzdv.mtocp.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.newelements.NothingToShowPlug
import com.adrzdv.mtocp.ui.model.DepotDto
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.DepotViewModel

@Composable
fun DepotCatalogScreen(
    viewModel: DepotViewModel
) {
    var searchText by remember { mutableStateOf("") }
    val depots by viewModel.filteredDepots
        .asFlow()
        .collectAsState(initial = emptyList())
    val context = LocalContext.current
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    LaunchedEffect(Unit) {
        viewModel.resetDinnerFilter()
        viewModel.filterByString("")
    }

    Column(
        modifier = Modifier
            .background(AppColors.BACKGROUND_COLOR.color)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchText,
            maxLines = 1,
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = {
                        searchText = ""
                        viewModel.filterByString(searchText)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.clear_text),
                            tint = Color.Gray
                        )
                    }
                }
            },
            onValueChange = {
                searchText = it
                viewModel.filterByString(it)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.MAIN_COLOR.color,
                unfocusedBorderColor = Color(0xFFCCCCCC),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            label = {
                Text(
                    stringResource(R.string.search_depot_hint),
                    style = AppTypography.labelMedium
                )
            },
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (depots.isEmpty()) {
            NothingToShowPlug()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(depots) { depot ->
                    DepotInfoCard(
                        depot = depot,
                        onPhoneClick = {
                            if (depot.phoneNumber.isNotBlank() && depot.phoneNumber != "0") {
                                val clip = ClipData.newPlainText("phone", depot.phoneNumber)
                                clipboard.setPrimaryClip(clip)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DepotInfoCard(
    depot: DepotDto,
    onPhoneClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.SURFACE_COLOR.color,
            contentColor = AppColors.MAIN_COLOR.color
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Text(
                text = depot.name,
                style = AppTypography.bodyLarge
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = depot.shortName,
                style = AppTypography.bodyMedium,
                color = AppColors.MAIN_COLOR.color.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = depot.branchShortName,
                style = AppTypography.bodyMedium
            )

            if (depot.phoneNumber.isNotBlank() && depot.phoneNumber != "0") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = depot.phoneNumber,
                    style = AppTypography.bodyMedium,
                    modifier = Modifier.clickable(onClick = onPhoneClick)
                )
            }
        }
    }
}
