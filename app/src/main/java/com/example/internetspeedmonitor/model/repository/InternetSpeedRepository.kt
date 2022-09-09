package com.example.internetspeedmonitor.model.repository

import androidx.lifecycle.LiveData
import com.example.internetspeedmonitor.model.room.InternetSpeed
import com.example.internetspeedmonitor.model.room.InternetSpeedDAO
import com.example.internetspeedmonitor.model.room.InternetSpeeds

class InternetSpeedRepository(private val internetSpeedDAO: InternetSpeedDAO) {
    fun saveInternetSpeed(internetSpeed: InternetSpeed) {
        internetSpeedDAO.insert(internetSpeed)
    }

    fun getInternetSpeeds(): LiveData<InternetSpeeds> {
        return internetSpeedDAO.getInternetSpeeds()
    }
}