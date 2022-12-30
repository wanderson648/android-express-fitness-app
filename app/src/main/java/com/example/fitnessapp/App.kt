package com.example.fitnessapp

import android.app.Application
import com.example.fitnessapp.model.AppDatabase

class App: Application() {

    lateinit var db : AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }

}