package com.example.internetspeedmonitor.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "internet_speed")
class InternetSpeed(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var speed: Long
)