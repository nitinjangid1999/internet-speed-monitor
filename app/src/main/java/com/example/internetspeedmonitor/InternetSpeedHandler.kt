package com.example.internetspeedmonitor

import android.net.TrafficStats
import kotlinx.coroutines.delay

class InternetSpeedHandler {

    suspend fun getInternetSpeed(): Long {
        val previousBytes = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
        delay(1000)
        val currentBytes = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
        return currentBytes - previousBytes
    }
}

