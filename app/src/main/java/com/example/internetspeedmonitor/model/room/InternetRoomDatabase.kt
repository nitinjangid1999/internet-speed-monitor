package com.example.internetspeedmonitor.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [InternetSpeed::class], version = 1)
abstract class InternetRoomDatabase : RoomDatabase() {
    abstract fun getInternetSpeedDAO(): InternetSpeedDAO

    companion object {
        private var INSTANCE: InternetRoomDatabase? = null
        fun getRoomDatabase(context: Context): InternetRoomDatabase {
            var tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            tempInstance =
                Room.databaseBuilder(context, InternetRoomDatabase::class.java, "internet_speed")
                    .build()
            INSTANCE = tempInstance
            return tempInstance
        }
    }
}