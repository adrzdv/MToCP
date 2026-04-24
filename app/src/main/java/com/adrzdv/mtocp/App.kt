package com.adrzdv.mtocp

import android.app.Application
import androidx.core.content.edit
import androidx.room.Room
import com.adrzdv.mtocp.data.api.RetrofitHolder
import com.adrzdv.mtocp.data.db.AppDatabase
import com.adrzdv.mtocp.data.db.MIGRATION_1_2
import com.adrzdv.mtocp.data.model.auth.DeviceIdProviderImpl
import com.adrzdv.mtocp.data.repository.UserDataStorage
import com.adrzdv.mtocp.data.repository.refcache.CacheRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class App : Application() {

    companion object {
        private const val PREFS_NAME = "prefs"
        private const val DB_NAME = "mtocpdb"
        private const val APP_VERSION_KEY = "version"
        private const val DB_ASSET_VERSION_KEY = "db_asset_version"
        private const val DB_ASSET_VERSION = 2
    }

    lateinit var appDependencies: AppDependencies
        private set
    lateinit var cacheRepository: CacheRepository
        private set

    override fun onCreate() {

        super.onCreate()

        clearDatabase()

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DB_NAME
        ).createFromAsset("db/mtocpdb.db")
            .addMigrations(MIGRATION_1_2)
            .build()

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val userDataStorage = UserDataStorage(prefs)
        val deviceIdProvider = DeviceIdProviderImpl(applicationContext)
        val executor = Executors.newSingleThreadExecutor()

        val retrofitHolder = RetrofitHolder(
            deviceIdProvider = deviceIdProvider,
            userDataStorage = userDataStorage
        )

        appDependencies = AppDependencies(
            this,
            database = database,
            prefs,
            executor,
            retrofitHolder
        )

        cacheRepository = CacheRepository(appDependencies)
        CoroutineScope(Dispatchers.IO).launch {
            cacheRepository.loadCache()
        }

    }

    private fun clearDatabase() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        val currVersion = packageManager.getPackageInfo(packageName, 0).versionName
        val savedVersion = prefs.getString(APP_VERSION_KEY, "")
        val savedDbAssetVersion = prefs.getInt(DB_ASSET_VERSION_KEY, -1)
        val shouldRecreateDb = currVersion != savedVersion || savedDbAssetVersion != DB_ASSET_VERSION

        if (shouldRecreateDb) {
            deleteDatabase(DB_NAME)

            prefs.edit {
                putString(APP_VERSION_KEY, currVersion ?: "")
                putInt(DB_ASSET_VERSION_KEY, DB_ASSET_VERSION)
            }
        }
    }

}
