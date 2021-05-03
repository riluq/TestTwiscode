package com.riluq.testtwiscode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riluq.testtwiscode.data.AppRepository
import com.riluq.testtwiscode.ui.cart.CartViewModel
import com.riluq.testtwiscode.ui.main.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: AppRepository):
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository) as T
            modelClass.isAssignableFrom(CartViewModel::class.java) -> CartViewModel(repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}