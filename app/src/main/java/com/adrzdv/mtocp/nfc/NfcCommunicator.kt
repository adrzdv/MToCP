package com.adrzdv.mtocp.nfc

import android.app.Activity

interface NfcCommunicator {
    fun setJsonToSend(json: String)
    fun enableReader(activity: Activity, onJsonReceived: (String) -> Unit)
    fun disableReader(activity: Activity)
}