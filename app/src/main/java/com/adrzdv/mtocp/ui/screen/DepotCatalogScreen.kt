package com.adrzdv.mtocp.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.DepotViewModel

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

        Spacer(modifier = Modifier.height(8.dp))

        if (depots.isEmpty()) {
            NothingToShowPlug()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(depots) { depot ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = depot.name,
                            style = AppTypography.bodyLarge,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = depot.shortName,
                                style = AppTypography.bodyMedium,
                                color = Color.Gray
                            )

                            Text(
                                text = depot.phoneNumber,
                                style = AppTypography.bodyMedium,
                                color = Color.Black,
                                modifier = Modifier.clickable {
                                    val clip = ClipData.newPlainText("phone", depot.phoneNumber)
                                    clipboard.setPrimaryClip(clip)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${depot.branchName}, ${depot.branchShortName}",
                            style = AppTypography.bodyMedium,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}