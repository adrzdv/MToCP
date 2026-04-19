package com.adrzdv.mtocp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.adrzdv.mtocp.data.api.RetrofitClient
import com.adrzdv.mtocp.data.db.AppDatabase
import com.adrzdv.mtocp.data.importmodel.AdditionalParamImport
import com.adrzdv.mtocp.data.importmodel.TrainImport
import com.adrzdv.mtocp.data.repository.AuthRepository
import com.adrzdv.mtocp.data.repository.AuthRepositoryImpl
import com.adrzdv.mtocp.data.repository.CompanyRepositoryImpl
import com.adrzdv.mtocp.data.repository.DepotRepositoryImpl
import com.adrzdv.mtocp.data.repository.KriCoachRepoImpl
import com.adrzdv.mtocp.data.repository.TrainRepositoryImpl
import com.adrzdv.mtocp.data.repository.UserDataStorage
import com.adrzdv.mtocp.data.repository.ViolationRepositoryImpl
import com.adrzdv.mtocp.data.repository.old.TempParamRepositoryImpl
import com.adrzdv.mtocp.domain.repository.CompanyRepository
import com.adrzdv.mtocp.domain.repository.DepotRepository
import com.adrzdv.mtocp.domain.repository.KriCoachRepo
import com.adrzdv.mtocp.domain.repository.TrainRepository
import com.adrzdv.mtocp.domain.repository.ViolationRepository
import com.adrzdv.mtocp.domain.repository.old.TempParamRepository
import com.adrzdv.mtocp.domain.usecase.CreateDinnerCarUseCase
import com.adrzdv.mtocp.domain.usecase.CreatePassengerCoachUseCase
import com.adrzdv.mtocp.domain.usecase.GetCompanyByNameUseCase
import com.adrzdv.mtocp.domain.usecase.GetDepotByNameUseCase
import com.adrzdv.mtocp.domain.usecase.GetTrainByNumberUseCase
import com.adrzdv.mtocp.domain.usecase.GetTrainSchemeUseCase
import com.adrzdv.mtocp.service.stringprovider.StringProvider
import com.adrzdv.mtocp.service.stringprovider.StringProviderImpl
import com.adrzdv.mtocp.util.importmanager.ImportHandlerRegistry
import com.adrzdv.mtocp.util.importmanager.ImportManager
import com.adrzdv.mtocp.util.importmanager.handlers.AdditionalParamHandler
import com.adrzdv.mtocp.util.importmanager.handlers.TrainImportHandler
import java.util.concurrent.ExecutorService
import java.util.function.Consumer

/**
 * A manual dependency injection container that manages the lifecycle and initialization
 * of application-wide components.
 *
 * This class serves as a central registry for repositories, data storage, network services,
 * and utility managers. It facilitates the decoupling of object creation from business logic.
 *
 * @property context The application context used for initializing system-level services.
 * @property database The [AppDatabase] instance providing DAOs for repository construction.
 * @property prefs The [SharedPreferences] instance used for persistent user data storage.
 * @property executor The [ExecutorService] used for background task processing in managers.
 *
 * @property violationRepo Repository for managing violation-related data.
 * @property depotRepo Repository for managing depot-related data.
 * @property companyRepo Repository for managing company-related data.
 * @property trainRepo Repository for managing train and rolling stock data.
 * @property tempParamRepo Repository for managing temporary parameters.
 * @property kriCoachRepo Repository for managing KRI coach data.
 * @property userDataStorage Manager for handling user-specific preferences and session data.
 * @property authRepo Repository for handling authentication and API access.
 * @property registry The [ImportHandlerRegistry] containing handlers for different data import types.
 * @property importManager Orchestrator for performing asynchronous data import operations.
 * @property stringProvider Utility for providing localized strings outside of Android components.
 */
class AppDependencies(
    context: Context,
    database: AppDatabase,
    prefs: SharedPreferences,
    executor: ExecutorService
) {
    val violationRepo: ViolationRepository = ViolationRepositoryImpl(database.violationDao())
    val depotRepo: DepotRepository = DepotRepositoryImpl(database.depotDao())
    val companyRepo: CompanyRepository = CompanyRepositoryImpl(database.companyDao())
    val trainRepo: TrainRepository =
        TrainRepositoryImpl(database.trainDao(), database.depotDao())

    val tempParamRepo: TempParamRepository = TempParamRepositoryImpl(database.tempParamsDao())
    val kriCoachRepo: KriCoachRepo = KriCoachRepoImpl(database.kriCoachDao())
    val userDataStorage = UserDataStorage(prefs)
    val authRepo: AuthRepository =
        AuthRepositoryImpl(RetrofitClient.authApi, userDataStorage)
    val registry: ImportHandlerRegistry = ImportHandlerRegistry().apply {
//        register(
//            ViolationImport::class.java,
//            ViolationImportHandler(
//                violationRepo,
//                Consumer { msg: String? -> Log.d("IMPORT", msg!!) })
//        )
//        register(
//            DepotImport::class.java,
//            DepotImportHandler(
//                depotRepo,
//                Consumer { msg: String? -> Log.d("IMPORT", msg!!) })
//        )
//        register(
//            CompanyImport::class.java,
//            CompanyImportHandler(
//                companyRepo,
//                Consumer { msg: String? -> Log.d("IMPORT", msg!!) })
//        )
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
    val stringProvider: StringProvider = StringProviderImpl(context)
    val getDepotByNameUseCase = GetDepotByNameUseCase(depotRepo)
    val getTrainByNumberUseCase = GetTrainByNumberUseCase(trainRepo)
    val createPassengerCoachUseCase = CreatePassengerCoachUseCase(getDepotByNameUseCase)
    val getTrainSchemeUseCase = GetTrainSchemeUseCase()
    val getCompanyByNameUseCase = GetCompanyByNameUseCase(companyRepo)
    val createDinnerCarUseCase = CreateDinnerCarUseCase(getDepotByNameUseCase, getCompanyByNameUseCase)


    //val violationRepo: ViolationRepository = ViolationRepositoryImpl(database.violationDao())
    //val companyRepo: CompanyRepository = CompanyRepositoryImpl(database.companyDao())
    //val depotRepo: DepotRepository = DepotRepositoryImpl(database.depotDao())
}