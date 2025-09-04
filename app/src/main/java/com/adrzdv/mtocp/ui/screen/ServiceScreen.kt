package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.buttons.MenuButton
import com.adrzdv.mtocp.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceScreen(
    onCleanRepositoryClick: () -> Unit,
    onLoadCatalog: () -> Unit,
    onBackClick: () -> Unit
) {

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
                .padding(16.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_32),
                        contentDescription = stringResource(R.string.back_text)
                    )
                }
                Text(
                    text = stringResource(R.string.header_service),
                    style = AppTypography.headlineLarge,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MenuButton(
                    text = stringResource(R.string.clean_dir_string),
                    icon = painterResource(R.drawable.ic_clear_data_24_white),
                    onClick = onCleanRepositoryClick
                )
                MenuButton(
                    text = stringResource(R.string.load_catalog_string),
                    icon = painterResource(R.drawable.ic_catalog_24_white),
                    onClick = onLoadCatalog
                )
            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun PreviewServiceScreen() {
    ServiceScreen(
        onBackClick = {},
        onLoadCatalog = {},
        onCleanRepositoryClick = {}
    )
}