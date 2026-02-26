package com.adrzdv.mtocp.ui.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.adrzdv.mtocp.R
import com.adrzdv.mtocp.data.api.RetrofitClient
import com.adrzdv.mtocp.data.repository.AuthRepositoryImpl
import com.adrzdv.mtocp.ui.component.ChangePasswordBottomSheet
import com.adrzdv.mtocp.ui.viewmodel.service.AssistedViewModelFactory
import com.adrzdv.mtocp.ui.viewmodel.model.ChangePasswordBottomSheetViewModel
import com.adrzdv.mtocp.ui.viewmodel.model.ServiceViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class ChangePasswordBottomSheetFragment(
    private val onDismiss: () -> Unit
) : BottomSheetDialogFragment() {
    private val authRepo = AuthRepositoryImpl(RetrofitClient.authApi, null)
    private val viewModel: ChangePasswordBottomSheetViewModel by viewModels {
        AssistedViewModelFactory {
            ChangePasswordBottomSheetViewModel(
                authRepository = authRepo,
                lengthError = getString(R.string.password_rule_4),
                digitError = getString(R.string.password_rule_1),
                upperCaseError = getString(R.string.password_rule_2),
                specDigitError = getString(R.string.password_rule_3),
                passwordNotMatchedError = getString(R.string.password_not_match)
            )
        }
    }
    private val serviceViewModel: ServiceViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.behavior.isDraggable = true

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { loading ->
                (dialog as? BottomSheetDialog)?.let {
                    it.behavior.isDraggable = !loading
                    it.setCancelable(!loading)
                    it.setCanceledOnTouchOutside(!loading)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ChangePasswordBottomSheet(
                    viewModel = viewModel,
                    serviceScreenVM = serviceViewModel,
                    onDismiss = { dismiss() }
                )
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss()
    }
}