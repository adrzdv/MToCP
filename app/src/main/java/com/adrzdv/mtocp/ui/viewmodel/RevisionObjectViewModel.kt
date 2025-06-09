package com.adrzdv.mtocp.ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject

class RevisionObjectViewModel: ViewModel() {
    private val _coaches = mutableStateMapOf<String, RevisionObject>()
    val coaches: Map<String, RevisionObject> = _coaches

    fun addRevObject(revObject: RevisionObject) {
        _coaches.put(revObject.number, revObject)
    }

    fun removeRevObject(revObject: RevisionObject) {
        _coaches.remove(revObject.number)
    }

    fun cleanMap() {
        _coaches.clear()
    }
}