package com.example.fitnessapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Calc::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun calDao(): CalcDao

    companion object {
        private var INSTANCE: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            return if (INSTANCE == null) {

                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "fitness_tracker"
                    ).build()
                }
                INSTANCE as AppDatabase
            } else {
                INSTANCE as AppDatabase
            }
        }
    }

}