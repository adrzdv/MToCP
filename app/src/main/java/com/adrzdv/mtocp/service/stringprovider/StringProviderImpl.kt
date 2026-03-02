package com.adrzdv.mtocp.service.stringprovider

import android.content.Context

class StringProviderImpl(
    private val context: Context
) : StringProvider {
    override fun getString(id: Int): String = context.getString(id)
}