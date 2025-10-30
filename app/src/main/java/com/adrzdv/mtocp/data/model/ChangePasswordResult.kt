package com.adrzdv.mtocp.data.model

sealed class ChangePasswordResult {
    data object Success : ChangePasswordResult()
    data class ServerError(val code: Int, val message: String?) : ChangePasswordResult()
    data class NetworkError(val message: String?) : ChangePasswordResult()
}