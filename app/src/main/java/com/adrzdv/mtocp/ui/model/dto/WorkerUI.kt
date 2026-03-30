package com.adrzdv.mtocp.ui.model.dto

/**
 * UI representation of a worker entity.
 *
 * This data class is used to display worker information within the user interface,
 * typically mapped from a domain or data transfer object (DTO).
 *
 * @property id The unique identifier of the worker.
 * @property name The display name of the worker.
 * @property position The display name of the worker's position.
 * @property depot The display name of the worker's depot.
 */
data class WorkerUI(
    val id: String,
    val name: String,
    val position: String,
    val depot: String
) {
}


