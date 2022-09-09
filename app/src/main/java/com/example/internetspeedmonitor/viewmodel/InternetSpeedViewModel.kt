package com.example.internetspeedmonitor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.internetspeedmonitor.model.repository.InternetSpeedRepository
import com.example.internetspeedmonitor.model.room.InternetSpeeds

class InternetSpeedViewModel(private val repository: InternetSpeedRepository) : ViewModel() {
    var internetSpeedsLiveData: LiveData<InternetSpeeds> = MutableLiveData()

    fun getInternetSpeeds() {
        internetSpeedsLiveData = repository.getInternetSpeeds()
    }
}