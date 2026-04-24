package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.domain.model.enums.RevisionType
import com.adrzdv.mtocp.ui.component.newelements.ClearIcon
import com.adrzdv.mtocp.ui.component.newelements.DropdownField
import com.adrzdv.mtocp.ui.component.newelements.InputTextField
import com.adrzdv.mtocp.ui.component.newelements.NothingToShowPlug
import com.adrzdv.mtocp.ui.component.newelements.cards.ViolationCard
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.viewmodel.model.ViolationViewModel

@Composable
fun ViolationCatalogScreen(
    viewModel: ViolationViewModel
) {
    val query by viewModel.query.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val revisionType by viewModel.revisionType.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.resetQuery()
    }

    Column(
        modifier = Modifier
            .background(AppColors.BACKGROUND_COLOR.color)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        InputTextField(
            value = query,
            onValueChange = {
                viewModel.onQueryChange(it)
            },
            isError = false,
            errorText = "",
            label = stringResource(R.string.search_violation),
            trailingIcon = {
                if (query.isNotEmpty()) {
                    ClearIcon(
                        onClick = {
                            viewModel.resetQuery()
                        }

                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownField(
            source = RevisionType.getListOfTypes(),
            selected = revisionType?.revisionTypeTitle ?: RevisionType.ALL.revisionTypeTitle,
            isError = false,
            label = stringResource(R.string.choose_revision_type),
            onOptionSelected = {
                viewModel.setRevisionType(RevisionType.fromString(it))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (suggestions.isEmpty()) {
            NothingToShowPlug()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(suggestions) { violation ->
                    ViolationCard(
                        violation = violation
                    )
                }
            }
        }
    }
}
