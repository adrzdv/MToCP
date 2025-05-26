package com.adrzdv.mtocp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adrzdv.mtocp.ui.theme.CustomTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevisionTypeDropdown(
    revisionTypes: List<String>,
    selectedRevision: String,
    onRevisionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        OutlinedTextField(
            value = selectedRevision,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFCCCCCC),
                unfocusedBorderColor = Color(0xFFCCCCCC),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            revisionTypes.forEach { type ->
                DropdownMenuItem(
                    text = {
                        Text(
                            type,
                            style = CustomTypography.bodyLarge
                        )
                    },
                    onClick = {
                        onRevisionSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowSpinner() {
    RevisionTypeDropdown(
        listOf("test"),
        "ttt",
        onRevisionSelected = {}
    )
}