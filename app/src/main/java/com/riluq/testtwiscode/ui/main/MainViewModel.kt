package com.riluq.testtwiscode.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.riluq.testtwiscode.data.AppRepository
import com.riluq.testtwiscode.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers

class MainViewModel(repository: AppRepository): BaseViewModel(repository) {

    private val _totalProductsSelected = MutableLiveData(0)
    val totalProductsSelected: LiveData<Int>
        get() = _totalProductsSelected

    fun search() = liveData(Dispatchers.IO) {
        emitSource(repository.onSearch())
    }

    fun setTotalProductsSelected(total: Int) {
        _totalProductsSelected.value = total
    }

    fun addTotalProductsSelected() {
        _totalProductsSelected.value = _totalProductsSelected.value?.plus(1)
    }

    fun minusTotalProductsSelected() {
        _totalProductsSelected.value = _totalProductsSelected.value?.minus(1)
    }
}