package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.newelements.NothingToShowPlug
import com.adrzdv.mtocp.ui.component.newelements.cards.TrainInfoCard
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import com.adrzdv.mtocp.ui.viewmodel.model.TrainInfoViewModel

@Composable
fun TrainInfoScreen(
    viewModel: TrainInfoViewModel
) {
    var searchString by remember { mutableStateOf("") }
    val trains by viewModel.filteredTrains

    LaunchedEffect(Unit) {
        viewModel.loadTrains()
        viewModel.filterTrainListByString("")
    }

    Column(
        modifier = Modifier
            .background(AppColors.BACKGROUND_COLOR.color)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchString,
            maxLines = 1,
            trailingIcon = {
                if (searchString.isNotEmpty()) {
                    IconButton(onClick = {
                        searchString = ""
                        viewModel.filterTrainListByString(searchString)
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
                searchString = it
                viewModel.filterTrainListByString(it)
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
                    stringResource(R.string.search_train),
                    style = AppTypography.labelMedium
                )
            },
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_information_24_white),
                tint = AppColors.ERROR_COLOR.color,
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.depot_warning),
                textAlign = TextAlign.Center,
                color = AppColors.ERROR_COLOR.color,
                style = AppTypography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (trains.isEmpty()) {
            NothingToShowPlug()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(trains) { item ->
                    TrainInfoCard(item)
                }
            }
        }
    }
}