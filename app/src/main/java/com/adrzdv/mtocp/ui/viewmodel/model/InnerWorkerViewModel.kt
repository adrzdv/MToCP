package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.adrzdv.mtocp.domain.model.workers.InnerWorkerDomain

class InnerWorkerViewModel : ViewModel() {
    private val _workers = mutableStateListOf<InnerWorkerDomain>()
    val workers: List<InnerWorkerDomain> = _workers

    fun addWorker(worker: InnerWorkerDomain) {
        if (!_workers.contains(worker)) {
            _workers.add(worker)
        }
    }

    fun removeWorker(worker: InnerWorkerDomain) {
        _workers.remove(worker)
    }

    fun cleanWorkers() {
        _workers.clear()
    }
}