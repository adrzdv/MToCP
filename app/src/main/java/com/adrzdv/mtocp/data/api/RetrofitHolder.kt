package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.BuildConfig
import com.adrzdv.mtocp.data.api.interceptor.AuthInterceptor
import com.adrzdv.mtocp.data.api.interceptor.DeviceInterceptor
import com.adrzdv.mtocp.data.api.interceptor.TokenAuthenticator
import com.adrzdv.mtocp.data.model.auth.DeviceIdProvider
import com.adrzdv.mtocp.data.repository.UserDataStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHolder(
    private val deviceIdProvider: DeviceIdProvider,
    private val userDataStorage: UserDataStorage
) {
    companion object {
        private const val NETWORK_TIMEOUT_SECONDS = 120L
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val deviceInterceptor = DeviceInterceptor(deviceIdProvider)
    private val authInterceptor = AuthInterceptor(userDataStorage)

    private val authClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(deviceInterceptor)
            .build()
    }

    val authApi: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    private val mainClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(deviceInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(TokenAuthenticator(userDataStorage) {
                authApi
            })
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(mainClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val storageRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.UPDATE_URL)
            .client(mainClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val docRequestApi: DocRequestApi by lazy {
        retrofit.create(DocRequestApi::class.java)
    }

    val updaterApi: UpdateApi by lazy {
        storageRetrofit.create(UpdateApi::class.java)
    }

    val dictionaryApi: DictionaryApi by lazy {
        retrofit.create(DictionaryApi::class.java)
    }
}
