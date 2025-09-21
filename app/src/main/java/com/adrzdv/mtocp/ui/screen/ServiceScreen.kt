package com.adrzdv.mtocp.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.MessageCodes
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.buttons.MenuButton
import com.adrzdv.mtocp.ui.component.newelements.Divider
import com.adrzdv.mtocp.ui.component.newelements.SettingsRow
import com.adrzdv.mtocp.ui.component.newelements.SwitchRow
import com.adrzdv.mtocp.ui.component.snackbar.CustomSnackbarHost
import com.adrzdv.mtocp.ui.component.snackbar.ErrorSnackbar
import com.adrzdv.mtocp.ui.component.snackbar.InfoSnackbar
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceScreen(
    onCleanRepositoryClick: ((Boolean) -> Unit) -> Unit,
    onLoadCatalog: () -> Unit,
    onBackClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var appVersion =
        context.packageManager.getPackageInfo(context.packageName, 0)?.versionName ?: "unknown"
    val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val username = prefs.getString("username", "null")

    Scaffold(
        containerColor = AppColors.BACKGROUND_COLOR.color,
        topBar = {
            TopAppBar(
                title = {
                    Spacer(modifier = Modifier.height(0.dp))
                    Text(
                        text = stringResource(R.string.settings),
                        style = AppTypography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back_32_white),
                            contentDescription = stringResource(R.string.menu_string)
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = AppColors.MAIN_COLOR.color,
                    scrolledContainerColor = AppColors.MAIN_COLOR.color,
                    navigationIconContentColor = AppColors.SURFACE_COLOR.color,
                    titleContentColor = AppColors.SURFACE_COLOR.color,
                    actionIconContentColor = AppColors.SURFACE_COLOR.color,
                    subtitleContentColor = AppColors.SURFACE_COLOR.color
                )
            )
        },
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Text(
                    text = stringResource(R.string.settings_personal),
                    style = AppTypography.titleSmall,
                    color = AppColors.SURFACE_COLOR.color,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                SettingsRow(
                    icon = painterResource(R.drawable.ic_outline_person_shield_24),
                    title = stringResource(R.string.username),
                    subtitle = username,
                    onClick = {}
                )
            }
            item {
                SettingsRow(
                    icon = painterResource(R.drawable.ic_outline_password_24),
                    title = stringResource(R.string.password),
                    subtitle = stringResource(R.string.change_password),
                    isClickable = true,
                    onClick = {}
                )
            }
            item {
                Divider()
            }
            item {
                Text(
                    text = stringResource(R.string.settings_main),
                    style = AppTypography.titleSmall,
                    color = AppColors.SURFACE_COLOR.color,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                SettingsRow(
                    icon = painterResource(R.drawable.ic_clear_data_24_white),
                    title = stringResource(R.string.clean_dir_string),
                    subtitle = stringResource(R.string.clear_dirs_sub),
                    isClickable = true,
                    onClick = {
                        onCleanRepositoryClick { success ->
                            coroutineScope.launch {
                                if (success) {
                                    snackbarHostState.showSnackbar(
                                        visuals = InfoSnackbar(MessageCodes.DIRECTORY_SUCCESS.messageTitle)
                                    )
                                } else {
                                    snackbarHostState.showSnackbar(
                                        visuals = ErrorSnackbar(MessageCodes.DIRECTORY_FAIL.messageTitle)
                                    )
                                }
                            }
                        }
                    }
                )
            }
            item {
                SettingsRow(
                    icon = painterResource(R.drawable.ic_catalog_24_white),
                    title = stringResource(R.string.load_catalog_string),
                    subtitle = stringResource(R.string.update_catalog_sub),
                    isClickable = true,
                    onClick = { onLoadCatalog() }
                )
            }
            item {
                Divider()
            }
            item {
                Text(
                    text = stringResource(R.string.settings_share),
                    style = AppTypography.titleSmall,
                    color = AppColors.SURFACE_COLOR.color,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                SwitchRow(
                    icon = painterResource(R.drawable.ic_outline_notifications_24),
                    title = stringResource(R.string.notifications),
                    subtitle = stringResource(R.string.notifications_sub),
                    initialState = true,
                    onStateChange = {}
                )
            }
            item {
                SettingsRow(
                    icon = painterResource(R.drawable.ic_information_24_white),
                    title = stringResource(R.string.about_app),
                    subtitle = appVersion as String?,
                    onClick = {}
                )
            }
            item {
                Divider()
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