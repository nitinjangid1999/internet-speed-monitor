package com.example.internetspeedmonitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.internetspeedmonitor.model.repository.InternetSpeedRepository

class InternetViewModelProvider(private val repository: InternetSpeedRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InternetSpeedViewModel(repository) as T
    }
}