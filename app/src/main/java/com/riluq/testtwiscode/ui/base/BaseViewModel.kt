package com.riluq.testtwiscode.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riluq.testtwiscode.data.AppRepository

abstract class BaseViewModel(val repository: AppRepository): ViewModel() {
    private val _hasBackPressed = MutableLiveData(false)
    val hasBackPressed: LiveData<Boolean>
        get() = _hasBackPressed

    fun backPressed() {
        _hasBackPressed.value = true
    }
    fun backPressedComplete() {
        _hasBackPressed.value = false
    }

    fun setDeviceToken(token: String?) = repository.setDeviceToken(token)
}