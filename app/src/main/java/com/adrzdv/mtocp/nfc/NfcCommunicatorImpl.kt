package com.adrzdv.mtocp.nfc

import android.app.Activity

class NfcCommunicatorImpl : NfcCommunicator {
    private val reader = NfcReader()

    override fun setJsonToSend(json: String) {
        JsonCardService.jsonToSend = json
    }

    override fun enableReader(activity: Activity, onJsonReceived: (String) -> Unit) {
        reader.enableReaderMode(activity, onJsonReceived)
    }

    override fun disableReader(activity: Activity) {
        reader.disableReaderMode(activity)
    }
}