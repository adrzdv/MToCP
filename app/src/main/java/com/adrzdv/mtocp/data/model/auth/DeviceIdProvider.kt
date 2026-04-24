package com.adrzdv.mtocp.data.model.auth

interface DeviceIdProvider {
    fun get(): String
}