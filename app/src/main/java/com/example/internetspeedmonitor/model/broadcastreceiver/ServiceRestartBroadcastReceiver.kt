package com.example.internetspeedmonitor.model.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.internetspeedmonitor.model.service.InternetSpeedMonitorService

class ServiceRestartBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        context?.let {
            val intent = Intent(it, InternetSpeedMonitorService::class.java)
            it.startForegroundService(intent)
        }
    }
}