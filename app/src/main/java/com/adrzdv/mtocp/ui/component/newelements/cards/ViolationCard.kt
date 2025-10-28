package com.adrzdv.mtocp.ui.component.newelements.cards

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.ui.model.ViolationDto
import com.adrzdv.mtocp.ui.theme.AppColors
import com.adrzdv.mtocp.ui.theme.AppTypography

@Composable
fun ViolationCard(
    violation: ViolationDto,
    onChangeValueClick: () -> Unit,
    onResolveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onMakePhotoClick: () -> Unit,
    onMakeVideoClick: () -> Unit,
    onAddTagClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var isResolved by remember { mutableStateOf(violation.isResolved) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        expanded = true
                    }
                )
            }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.OFF_WHITE.color,
                contentColor = AppColors.MAIN_GREEN.color
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    style = AppTypography.bodyMedium,
                    text = violation.code.toString()
                )
                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                        .width(8.dp)
                )
                Text(
                    style = AppTypography.bodyMedium,
                    text = violation.name,
                    maxLines = 3,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis
                )
                DropdownMenu(
                    expanded = expanded,
                    containerColor = AppColors.OFF_WHITE.color,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_edit_value_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.change_value))
                            }
                        },
                        onClick = {
                            expanded = false
                            onChangeValueClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_resolved_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.resolved))
                            }
                        },
                        onClick = {
                            expanded = false
                            isResolved = !isResolved
                            onResolveClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add_tag_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.add_tag))
                            }
                        },
                        onClick = {
                            expanded = false
                            onAddTagClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add_photo_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.add_photo))
                            }
                        },
                        onClick = {
                            expanded = false
                            onMakePhotoClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_add_video_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.add_video))
                            }
                        },
                        onClick = {
                            expanded = false
                            onMakeVideoClick()
                        },
                        enabled = false
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_delete_24_white),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.delete))
                            }
                        },
                        onClick = {
                            expanded = false
                            onDeleteClick()
                        }
                    )
                }
            }
        }
        Badge(
            containerColor = if (isResolved) AppColors.DARK_GREEN.color else AppColors.MATERIAL_RED.color,
            contentColor = AppColors.OFF_WHITE.color,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-8).dp, y = 8.dp)
        ) {
            Text(if (isResolved) stringResource(R.string.resolved) else stringResource(R.string.unresolved))
        }
    }
}