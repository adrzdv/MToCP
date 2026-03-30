package com.adrzdv.mtocp.ui.screen.share

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain
import com.adrzdv.mtocp.mapper.toDomain
import com.adrzdv.mtocp.ui.component.AppBar
import com.adrzdv.mtocp.ui.component.BackNavigationButton
import com.adrzdv.mtocp.ui.component.newelements.ClearIcon
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.cards.ViolationCard
import com.adrzdv.mtocp.ui.viewmodel.model.ViolationViewModel

@Composable
fun ViolationSelectionScreen(
    onItemClick: (ViolationDomain) -> Unit,
    onBackClick: () -> Unit,
    revisionType: RevisionType,
    violationViewModel: ViolationViewModel
) {
    val violationQuery by violationViewModel.query.collectAsState()
    val violationSuggestions by violationViewModel.suggestions.collectAsState()

    LaunchedEffect(Unit) {
        violationViewModel.setRevisionType(revisionType)
        violationViewModel.resetQuery()
    }

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.violation_catalog_string),
                navigationIcon = {
                    BackNavigationButton {
                        onBackClick()
                    }
                },
                actions = {}
            )
        }

    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                InputTextField(
                    value = violationQuery,
                    onValueChange = {
                        violationViewModel.onQueryChange(it)
                    },
                    isError = false,
                    errorText = "",
                    label = stringResource(R.string.start_input_text),
                    trailingIcon = {
                        if (violationQuery.isNotEmpty()) {
                            ClearIcon { violationViewModel.onQueryChange("") }
                        }
                    }
                )
            }
            items(violationSuggestions) { it ->
                ViolationCard(
                    it,
                    onItemClick = { onItemClick(it.toDomain()) }
                )
            }
        }
    }

}