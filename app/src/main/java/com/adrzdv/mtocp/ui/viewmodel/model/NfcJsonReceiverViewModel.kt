package com.adrzdv.mtocp.ui.viewmodel.model

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adrzdv.mtocp.nfc.NfcCommunicator

class NfcJsonReceiverViewModel(
    private val json: String,
    private val communicator: NfcCommunicator
) : ViewModel() {

    private val _receivedJson = MutableLiveData<String>()
    val receivedJson: LiveData<String> = _receivedJson

    private val _isTransferring = MutableLiveData(false)
    val isTransferring: LiveData<Boolean> = _isTransferring

    private val _transferCompleted = MutableLiveData<Unit>()
    val transferCompleted: LiveData<Unit> = _transferCompleted

    fun startReader(activity: Activity) {
        Log.d("NfcVM", "startReader called")
        communicator.enableReader(activity) { receivedJson ->
            Log.d("NfcVM", "Received JSON: $receivedJson")
            _receivedJson.postValue(receivedJson)
            _isTransferring.postValue(false)
        }
    }

    fun stopReader(activity: Activity) {
        Log.d("NfcVM", "stopReader called")
        communicator.disableReader(activity)
        _isTransferring.postValue(false)
    }

    fun startTransfer(activity: Activity) {
        Log.d("NfcVM", "startTransfer called")
        stopReader(activity)
        _isTransferring.postValue(true)
        prepareJsonToSend()

        communicator.setTransferCompleted {
            _isTransferring.postValue(false)
            _transferCompleted.postValue(Unit)
        }
    }

    fun stopTransfer(activity: Activity) {
        _isTransferring.postValue(false)
        startReader(activity)
    }

    private fun prepareJsonToSend() {
        communicator.setJsonToSend(json)
    }
}