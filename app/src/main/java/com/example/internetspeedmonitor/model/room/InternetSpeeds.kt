package com.example.internetspeedmonitor.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
class InternetSpeeds(
    @ColumnInfo(name = "max_speed")
    var maxSpeed: Long = 0,
    @ColumnInfo(name = "min_speed")
    var minSpeed: Long = 0,
    @ColumnInfo(name = "avg_speed")
    var meanSpeed: Long = 0
)