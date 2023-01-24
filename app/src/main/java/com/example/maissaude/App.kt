package com.example.maissaude

import android.app.Application
import com.example.maissaude.model.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.getDatabase(this)
    }
}