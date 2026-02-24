package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.ui.component.RevisionTypeDropdown
import com.adrzdv.mtocp.ui.component.newelements.NothingToShowPlug
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.model.ViolationViewModel

@Composable
fun ViolationCatalogScreen(
    viewModel: ViolationViewModel,
    revisionTypes: List<String>
) {
    var searchText by remember { mutableStateOf("") }
    var selectedRevision by remember { mutableStateOf(revisionTypes.first()) }

    val violations by viewModel.filteredViolations
        .asFlow()
        .collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.filterDataByString("")
        viewModel.filterDataByRevisionType(RevisionType.ALL)
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
                        viewModel.filterDataByString(searchText)
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
                viewModel.filterDataByString(it)
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
                    stringResource(R.string.search_text_hint),
                    style = CustomTypography.labelMedium
                )
            },
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(8.dp))

        RevisionTypeDropdown(
            revisionTypes = revisionTypes,
            selectedRevision = selectedRevision,
            isError = false,
            errorMessage = "",
            onRevisionSelected = {
                selectedRevision = it
                viewModel.filterDataByRevisionType(RevisionType.fromString(it))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (violations.isEmpty()) {
            NothingToShowPlug()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(violations) { violation ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = violation.code.toString(),
                            style = CustomTypography.bodyLarge,
                            modifier = Modifier.weight(1f),
                            color = Color.Black
                        )
                        Text(
                            text = violation.name,
                            style = CustomTypography.bodyLarge,
                            modifier = Modifier.weight(5f),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
