package com.example.internetspeedmonitor.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface InternetSpeedDAO {
    @Insert
    fun insert(internetSpeed: InternetSpeed)

    @Query("select MIN(internet_speed.speed) as min_speed, MAX(internet_speed.speed) as max_speed, AVG(internet_speed.speed) as avg_speed from internet_speed")
    fun getInternetSpeeds(): LiveData<InternetSpeeds>
}