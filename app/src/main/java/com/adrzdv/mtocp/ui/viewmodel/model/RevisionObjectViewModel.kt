package com.adrzdv.mtocp.ui.viewmodel.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject

class RevisionObjectViewModel<T : RevisionObject> : ViewModel() {
    private val _revObjects = mutableStateListOf<T>()
    val revObjects: List<T> = _revObjects

    fun addRevObject(item: T) : Boolean {
        if (!_revObjects.contains(item)) {
            _revObjects.add(item)
            return true
        }
        return false
    }

    fun removeRevObject(item: T) {
        _revObjects.remove(item)
    }

    fun cleanMap() {
        _revObjects.clear()
    }
}