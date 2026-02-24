package com.adrzdv.mtocp

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.room.Room
import com.adrzdv.mtocp.data.db.AppDatabase
import java.util.concurrent.Executors

class App : Application() {

    lateinit var appDependencies: AppDependencies
        private set

    override fun onCreate() {
        super.onCreate()

        clearDatabase()

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mtocpdb"
        )
            .createFromAsset("db/mtocpdb.db")
            .build()

        val executor = Executors.newSingleThreadExecutor()
        appDependencies = AppDependencies(
            this,
            database,
            getSharedPreferences("prefs", Context.MODE_PRIVATE),
            executor
        )
    }

    private fun clearDatabase() {
        val prefs = getSharedPreferences("app", MODE_PRIVATE)

        val currVersion = packageManager.getPackageInfo(packageName, 0).versionName
        val savedVersion = prefs.getString("version", "")

        if (currVersion != null) {
            if (!currVersion.equals(savedVersion)) {
                this.deleteDatabase("mtocp")
                prefs.edit { putString("version", currVersion) }
            }
        }
    }

}