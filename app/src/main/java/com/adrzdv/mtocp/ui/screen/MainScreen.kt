package com.adrzdv.mtocp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.component.ConfirmDialog
import com.adrzdv.mtocp.ui.theme.CustomTypography
import kotlin.text.Typography

@Composable
fun StartMenuScreen(
    onStartRevisionClick: () -> Unit,
    onOpenViolationCatalogClick: () -> Unit,
    onServiceMenuClick: () -> Unit,
    onExitClick: () -> Unit,
    onHelpClick: () -> Unit,
    appVersion: String
) {
    var showExitDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.main_menu_text),
                style = CustomTypography.displayLarge,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp)) // аналог горизонтального отступа до кнопок

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MenuButton(
                    text = stringResource(R.string.start_revision_text),
                    icon = painterResource(R.drawable.ic_start_revision_32_white),
                    onClick = onStartRevisionClick
                )
                MenuButton(
                    text = stringResource(R.string.violation_catalog_string),
                    icon = painterResource(R.drawable.ic_list_24_white),
                    onClick = onOpenViolationCatalogClick
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MenuButton(
                    text = stringResource(R.string.service_menu_string),
                    icon = painterResource(R.drawable.ic_tool_48_white),
                    onClick = onServiceMenuClick
                )
                MenuButton(
                    text = stringResource(R.string.exit_text),
                    icon = painterResource(R.drawable.ic_exit_24_white),
                    onClick = {
                        showExitDialog = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MenuButton(
                    text = stringResource(R.string.help),
                    icon = painterResource(R.drawable.ic_help_24_white),
                    onClick = onHelpClick
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        Text(
            text = appVersion,
            fontSize = 12.sp,
            color = Color.DarkGray,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        if (showExitDialog) {
            ConfirmDialog(
                title = "Выход",
                message = "Вы уверены, что хотите выйти?",
                onConfirm = {
                    showExitDialog = false
                    onExitClick()
                },
                onDismiss = { showExitDialog = false }
            )
        }
    }
}


@Composable
fun MenuButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .width(140.dp)
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults
                .buttonColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = false
                )
                .background(Color.Green, shape = RoundedCornerShape(12.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(bottom = 4.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = text,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = CustomTypography.bodyMedium,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    StartMenuScreen(
        onStartRevisionClick = {},
        onOpenViolationCatalogClick = {},
        onServiceMenuClick = {},
        onExitClick = {},
        onHelpClick = {},
        appVersion = "v1.0.0"
    )
}