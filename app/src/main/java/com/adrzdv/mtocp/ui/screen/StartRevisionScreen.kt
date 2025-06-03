package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.OrdersTypes
import com.adrzdv.mtocp.ui.component.AutocompleteTextField
import com.adrzdv.mtocp.ui.component.RevisionTypeDropdown
import com.adrzdv.mtocp.ui.theme.CustomTypography
import com.adrzdv.mtocp.ui.viewmodel.AutocompleteViewModel
import com.adrzdv.mtocp.ui.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartRevisionScreen(
    orderViewModel: OrderViewModel,
    autocompleteViewModel: AutocompleteViewModel,
    orderTypes: List<String>,
    onBackClick: () -> Unit
) {
    val query by autocompleteViewModel.query.observeAsState("")
    val suggestions by autocompleteViewModel.filteredItems.observeAsState(emptyList())
    var selectedOrderType by remember { mutableStateOf("") }
    val isInputEnabled = selectedOrderType.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Spacer(modifier = Modifier.height(0.dp)) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_32),
                        contentDescription = stringResource(R.string.back_text)
                    )
                }
                Text(
                    text = stringResource(R.string.header_start_revision),
                    style = CustomTypography.displayLarge,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    //text = stringResource("R.string.inp"),
                    text = "TEXT",
                    style = CustomTypography.labelLarge
                )
                RevisionTypeDropdown(
                    revisionTypes = orderTypes,
                    selectedRevision = selectedOrderType,
                    onRevisionSelected = {
                        selectedOrderType = it
                        orderViewModel.setSelectedType(OrdersTypes.getFromString(it))
                        autocompleteViewModel.setOrderType(OrdersTypes.getFromString(it))
                    }
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                AutocompleteTextField(
                    query = query,
                    suggestions = suggestions,
                    onQueryChanged = { input ->
                        autocompleteViewModel.onQueryChanged(input)
                    },
                    onSuggestionSelected = { selected ->
                        orderViewModel.setObjectNumber(selected)
                    },
                    enabled = isInputEnabled
                )
            }
        }

    }
}