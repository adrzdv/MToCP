package com.adrzdv.mtocp.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.adrzdv.mtocp.domain.model.revisionobject.basic.RevisionObject

class RevisionObjectViewModel<T: RevisionObject>: ViewModel() {
    private val _revObjects = mutableStateListOf<T>()
    val revObjects: List<T> = _revObjects

    fun addRevObject(item: T) {
        _revObjects.add(item)
    }

    fun removeRevObject(item: T) {
        _revObjects.remove(item)
    }

    fun cleanMap() {
        _revObjects.clear()
    }
}