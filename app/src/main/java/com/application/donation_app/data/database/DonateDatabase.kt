package com.application.donation_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Donate::class],
    version = 1
)
abstract class DonateDatabase : RoomDatabase() {

    abstract fun donatesDao(): DonateDao

    companion object {
        @Volatile
        private var INSTANCE: DonateDatabase? = null

        fun getInstance(context: Context): DonateDatabase {
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DonateDatabase::class.java,
                    "donate_db"
                ).build()
                INSTANCE = instance
                instance
            }
            return INSTANCE as DonateDatabase
        }
    }
}