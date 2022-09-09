package com.example.internetspeedmonitor.utils

class Utils {
    companion object {
        private const val MB: Float = 1000000F
        private const val KB: Float = 1000F

        fun formatSpeed(speed: Long): String {
            return if (speed >= MB) {
                String.format("%.1f Mbps", (speed.toFloat() / MB))
            } else {
                String.format("%.1f Kbps", (speed.toFloat() / KB))
            }

        }
    }
}