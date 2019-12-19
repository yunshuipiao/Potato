package com.swensun.potato

import androidx.lifecycle.*

object SharedViewModelFactory : ViewModelProvider.Factory, LifecycleObserver {

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

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun clearViewModel() {
        viewModelMap.clear()
    }
}