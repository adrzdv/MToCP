package com.adrzdv.mtocp.ui.model.statedtoui

/**
 * UI representation of a worker entity.
 *
 * This data class is used to display worker information within the user interface,
 * typically mapped from a domain or data transfer object (DTO).
 *
 * @property id The unique identifier of the worker.
 * @property name The display name of the worker.
 * @property depot THe display name of the depot associated with the worker.
 */
data class WorkerUI(
    val id: Int,
    val name: String,
    val depot: String
)
