package com.adrzdv.mtocp.service.stringprovider

import androidx.annotation.StringRes

interface StringProvider {
    fun getString(@StringRes id: Int): String
}