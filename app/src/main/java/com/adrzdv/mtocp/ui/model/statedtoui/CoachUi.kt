package com.adrzdv.mtocp.ui.model.statedtoui

/**
 * Data class representing a UI representation of a coach.
 *
 * @property number The number of the coach.
 * @property route The route associated with the coach.
 * @property type The type of the coach.
 * @property depot The depot associated with the coach.
 */
data class CoachUi(
    val number: String = "",
    val route: String = "",
    val type: String = "",
    val depot: String = ""
)
