package com.adrzdv.mtocp.data.model.auth

import android.content.Context
import android.provider.Settings;

class DeviceIdProviderImpl(
    private val context: Context
) : DeviceIdProvider {

    override fun get(): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
}