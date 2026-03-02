package com.adrzdv.mtocp.ui.component.newelements

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import com.adrzdv.mtocp.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    state: DatePickerState
) {
    DatePicker(
        state = state,
        colors = DatePickerColors(
            containerColor = AppColors.SURFACE_COLOR.color,
            titleContentColor = AppColors.MAIN_COLOR.color,
            headlineContentColor = AppColors.MAIN_COLOR.color,
            weekdayContentColor = AppColors.MAIN_COLOR.color,
            subheadContentColor = AppColors.MAIN_COLOR.color,
            navigationContentColor = AppColors.MAIN_COLOR.color,
            dividerColor = AppColors.SURFACE_COLOR.color,
            yearContentColor = AppColors.MAIN_COLOR.color,
            disabledYearContentColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f),
            currentYearContentColor = AppColors.MAIN_COLOR.color,
            selectedYearContentColor = AppColors.SURFACE_COLOR.color,
            disabledSelectedYearContentColor = AppColors.SURFACE_COLOR.color.copy(alpha = 0.38f),
            selectedYearContainerColor = AppColors.MAIN_COLOR.color,
            disabledSelectedYearContainerColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f),
            dayContentColor = AppColors.MAIN_COLOR.color,
            disabledDayContentColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f),
            selectedDayContentColor = AppColors.SURFACE_COLOR.color,
            disabledSelectedDayContentColor = AppColors.SURFACE_COLOR.color.copy(alpha = 0.38f),
            selectedDayContainerColor = AppColors.MAIN_COLOR.color,
            disabledSelectedDayContainerColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f),
            todayContentColor = AppColors.MAIN_COLOR.color,
            todayDateBorderColor = AppColors.MAIN_COLOR.color,
            dayInSelectionRangeContainerColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.15f),
            dayInSelectionRangeContentColor = AppColors.MAIN_COLOR.color,
            dateTextFieldColors = TextFieldDefaults.colors(
                focusedTextColor = AppColors.MAIN_COLOR.color,
                unfocusedTextColor = AppColors.MAIN_COLOR.color,
                disabledTextColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f),
                errorTextColor = AppColors.ERROR_COLOR.color,
                focusedContainerColor = AppColors.SURFACE_COLOR.color,
                unfocusedContainerColor = AppColors.SURFACE_COLOR.color,
                disabledContainerColor = AppColors.SURFACE_COLOR.color,
                errorContainerColor = AppColors.SURFACE_COLOR.color,
                cursorColor = AppColors.MAIN_COLOR.color,
                errorCursorColor = AppColors.ERROR_COLOR.color,
                focusedIndicatorColor = AppColors.MAIN_COLOR.color,
                unfocusedIndicatorColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.6f),
                disabledIndicatorColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f),
                errorIndicatorColor = AppColors.ERROR_COLOR.color,
                focusedLabelColor = AppColors.MAIN_COLOR.color,
                unfocusedLabelColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.7f),
                disabledLabelColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f),
                errorLabelColor = AppColors.ERROR_COLOR.color,
                focusedPlaceholderColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.6f),
                unfocusedPlaceholderColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.6f),
                disabledPlaceholderColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f),
                errorPlaceholderColor = AppColors.ERROR_COLOR.color,
                focusedSupportingTextColor = AppColors.MAIN_COLOR.color,
                unfocusedSupportingTextColor = AppColors.MAIN_COLOR.color,
                disabledSupportingTextColor = AppColors.MAIN_COLOR.color.copy(alpha = 0.38f),
                errorSupportingTextColor = AppColors.ERROR_COLOR.color
            )
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimePicker(
    state: TimePickerState
) {
    TimeInput(
        state = state,
        colors = TimePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
            clockDialColor = AppColors.SURFACE_COLOR.color,
            clockDialSelectedContentColor = AppColors.SURFACE_COLOR.color,
            clockDialUnselectedContentColor = AppColors.MAIN_COLOR.color,
            selectorColor = AppColors.MAIN_COLOR.color,
            periodSelectorBorderColor = AppColors.MAIN_COLOR.color,
            periodSelectorSelectedContainerColor = AppColors.MAIN_COLOR.color,
            periodSelectorUnselectedContainerColor = AppColors.SURFACE_COLOR.color,
            periodSelectorSelectedContentColor = AppColors.SURFACE_COLOR.color,
            periodSelectorUnselectedContentColor = AppColors.MAIN_COLOR.color,
            timeSelectorSelectedContainerColor = AppColors.MAIN_COLOR.color,
            timeSelectorUnselectedContainerColor = AppColors.SURFACE_COLOR.color,
            timeSelectorSelectedContentColor = AppColors.SURFACE_COLOR.color,
            timeSelectorUnselectedContentColor = AppColors.MAIN_COLOR.color
        )
    )
}