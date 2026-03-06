package com.adrzdv.mtocp.ui.validation

object Validator {
    private val WORKER_NAME_REGEX =
        Regex("""^[А-ЯЁ][а-яё]+(?:[-][А-ЯЁ][а-яё]+)?(?: (?:[А-ЯЁ][а-яё]+|[А-ЯЁ]\.|[А-ЯЁ]\.[А-ЯЁ]\.?)){0,2}$""")

    fun validateWorkerName(name: String): Boolean {
        return WORKER_NAME_REGEX.matches(name.trim())
    }
}