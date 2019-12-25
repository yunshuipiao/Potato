package com.swensun.potato

import androidx.lifecycle.*

object SharedViewModelFactory : ViewModelProvider.Factory, LifecycleObserver {

    private val viewModelMap = hashMapOf<String, ViewModel>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val canonicalName = modelClass.canonicalName ?: ""
        val viewModel = viewModelMap[canonicalName]
        if (viewModel == null) {
            val newViewModel =  modelClass.newInstance()
            viewModelMap[canonicalName] = newViewModel as ViewModel
            return newViewModel
        } else {
            return viewModel as T
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun clearViewModel() {
        viewModelMap.clear()
    }
}