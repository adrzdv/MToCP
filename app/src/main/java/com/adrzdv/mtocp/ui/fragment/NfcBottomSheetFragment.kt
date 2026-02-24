package com.adrzdv.mtocp.ui.fragment

import android.media.RingtoneManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.adrzdv.mtocp.nfc.NfcCommunicatorImpl
import com.adrzdv.mtocp.ui.component.NFCBottomSheetContent
import com.adrzdv.mtocp.ui.viewmodel.service.AssistedViewModelFactory
import com.adrzdv.mtocp.ui.viewmodel.model.NfcJsonReceiverViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.delay

class NfcBottomSheetFragment : BottomSheetDialogFragment() {

    var onJsonReceived: ((String) -> Unit)? = null

    private val communicator = NfcCommunicatorImpl()
    private val nfcViewModel: NfcJsonReceiverViewModel by viewModels {
        val dataJson = arguments?.getString("rev_map_json") ?: "{}"
        AssistedViewModelFactory {
            NfcJsonReceiverViewModel(
                dataJson,
                communicator
            )
        }
    }

    private var animateDismiss = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                var isVisible by remember { mutableStateOf(true) }
                LaunchedEffect(animateDismiss) {
                    if (animateDismiss) {
                        isVisible = false
                        delay(300)
                        dismissAllowingStateLoss()
                    }
                }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
                ) {
                    NFCBottomSheetContent(nfcViewModel, requireActivity())
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nfcViewModel.receivedJson.observe(viewLifecycleOwner) { json ->
            if (json.isNotEmpty()) {
                onJsonReceived?.invoke(json)
                nfcViewModel.stopReader(requireActivity())
                nfcViewModel.stopTransfer(requireActivity())
                playSuccessSound()
                dismiss()
            }
        }
        nfcViewModel.transferCompleted.observe(viewLifecycleOwner) {
            playSuccessSound()
            animateDismiss = true
        }
    }

    override fun onResume() {
        super.onResume()
        nfcViewModel.startReader(requireActivity())
    }

    override fun onPause() {
        super.onPause()
        nfcViewModel.stopReader(requireActivity())
    }

    private fun playSuccessSound() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(requireContext(), notification)
        r.play()
    }

    companion object {
        fun newInstance(json: String) = NfcBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putString("rev_map_json", json)
            }
        }
    }
}