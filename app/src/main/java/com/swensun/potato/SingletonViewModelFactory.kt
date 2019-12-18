package com.swensun.potato

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object SharedViewModelFactory : ViewModelProvider.Factory {

    val viewModelMap = hashMapOf<String, ViewModel>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val canonicalName = modelClass.canonicalName ?: ""
        if (modelClass == UserViewModel::class.java) {
            return if (viewModelMap.containsKey(canonicalName)) {
                viewModelMap[canonicalName] as T
            } else {
                val viewModel = modelClass.newInstance()
                viewModelMap[canonicalName] = viewModel
                viewModel as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    fun clearViewModel() {
        viewModelMap.clear()
    }
}