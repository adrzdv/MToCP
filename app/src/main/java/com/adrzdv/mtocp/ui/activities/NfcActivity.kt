package com.adrzdv.mtocp.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.adrzdv.mtocp.nfc.NfcCommunicatorImpl
import com.adrzdv.mtocp.ui.screen.NfcScreen
import com.adrzdv.mtocp.ui.viewmodel.service.AssistedViewModelFactory
import com.adrzdv.mtocp.ui.viewmodel.model.NfcJsonReceiverViewModel

class NfcActivity : AppCompatActivity() {
    private val communicator = NfcCommunicatorImpl()
    private val nfcViewModel: NfcJsonReceiverViewModel by viewModels {
        val dataJson = intent.getStringExtra("rev_map_json") ?: "{}"
        AssistedViewModelFactory {
            NfcJsonReceiverViewModel(dataJson, communicator)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NfcScreen(nfcViewModel, this)
        }
        // НЕ вызываем startReader здесь — вызов будет в onResume
    }

    override fun onResume() {
        super.onResume()
        nfcViewModel.startReader(this) // Включаем режим приема после установки UI
    }

    override fun onPause() {
        super.onPause()
        nfcViewModel.stopReader(this)  // Отключаем прием при сворачивании
    }
}