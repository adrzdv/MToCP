package com.adrzdv.mtocp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.adrzdv.mtocp.data.api.RetrofitClient
import com.adrzdv.mtocp.data.db.AppDatabase
import com.adrzdv.mtocp.data.importmodel.AdditionalParamImport
import com.adrzdv.mtocp.data.importmodel.CompanyImport
import com.adrzdv.mtocp.data.importmodel.DepotImport
import com.adrzdv.mtocp.data.importmodel.TrainImport
import com.adrzdv.mtocp.data.importmodel.ViolationImport
import com.adrzdv.mtocp.data.repository.AuthRepository
import com.adrzdv.mtocp.data.repository.AuthRepositoryImpl
import com.adrzdv.mtocp.data.repository.CompanyRepositoryImpl
import com.adrzdv.mtocp.data.repository.DepotRepositoryImpl
import com.adrzdv.mtocp.data.repository.KriCoachRepoImpl
import com.adrzdv.mtocp.data.repository.TempParamRepositoryImpl
import com.adrzdv.mtocp.data.repository.TrainRepositoryImpl
import com.adrzdv.mtocp.data.repository.UserDataStorage
import com.adrzdv.mtocp.data.repository.ViolationRepositoryImpl
import com.adrzdv.mtocp.domain.repository.CompanyRepository
import com.adrzdv.mtocp.domain.repository.DepotRepository
import com.adrzdv.mtocp.domain.repository.KriCoachRepo
import com.adrzdv.mtocp.domain.repository.TempParamRepository
import com.adrzdv.mtocp.domain.repository.TrainRepository
import com.adrzdv.mtocp.domain.repository.ViolationRepository
import com.adrzdv.mtocp.util.importmanager.ImportHandlerRegistry
import com.adrzdv.mtocp.util.importmanager.ImportManager
import com.adrzdv.mtocp.util.importmanager.handlers.AdditionalParamHandler
import com.adrzdv.mtocp.util.importmanager.handlers.CompanyImportHandler
import com.adrzdv.mtocp.util.importmanager.handlers.DepotImportHandler
import com.adrzdv.mtocp.util.importmanager.handlers.TrainImportHandler
import com.adrzdv.mtocp.util.importmanager.handlers.ViolationImportHandler
import java.util.concurrent.ExecutorService
import java.util.function.Consumer

class AppDependencies(
    context: Context,
    database: AppDatabase,
    prefs: SharedPreferences,
    executor: ExecutorService
) {
    val violationRepo: ViolationRepository = ViolationRepositoryImpl(database.violationDao())
    val depotRepo: DepotRepository = DepotRepositoryImpl(database.depotDao())
    val companyRepo: CompanyRepository = CompanyRepositoryImpl(database.companyDao())
    val trainRepo: TrainRepository = TrainRepositoryImpl(database.trainDao(), database.depotDao())
    val tempParamRepo: TempParamRepository = TempParamRepositoryImpl(database.tempParamsDao())
    val kriCoachRepo: KriCoachRepo = KriCoachRepoImpl(database.kriCoachDao())
    val userDataStorage = UserDataStorage(prefs)
    val authRepo: AuthRepository =
        AuthRepositoryImpl(RetrofitClient.authApi, userDataStorage)
    val registry: ImportHandlerRegistry = ImportHandlerRegistry().apply {
        register(
            ViolationImport::class.java,
            ViolationImportHandler(
                violationRepo,
                Consumer { msg: String? -> Log.d("IMPORT", msg!!) })
        )
        register(
            DepotImport::class.java,
            DepotImportHandler(
                depotRepo,
                Consumer { msg: String? -> Log.d("IMPORT", msg!!) })
        )
        register(
            CompanyImport::class.java,
            CompanyImportHandler(
                companyRepo,
                Consumer { msg: String? -> Log.d("IMPORT", msg!!) })
        )
        register(
            TrainImport::class.java,
            TrainImportHandler(
                trainRepo,
                Consumer { msg: String? -> Log.d("IMPORT", msg!!) })
        )
        register(
            AdditionalParamImport::class.java,
            AdditionalParamHandler(
                tempParamRepo,
                Consumer { msg: String? -> Log.d("IMPORT", msg!!) })
        )
    }
    val importManager: ImportManager = ImportManager(registry, executor)
}