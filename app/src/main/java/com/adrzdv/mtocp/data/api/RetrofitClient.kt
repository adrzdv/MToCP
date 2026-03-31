package com.adrzdv.mtocp.data.api

import com.adrzdv.mtocp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = BuildConfig.BASE_URL
    private const val UPDATE_URL = BuildConfig.UPDATE_URL

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }

    val storage: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(UPDATE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val docRequestApi: DocRequestApi by lazy {
        retrofit.create(DocRequestApi::class.java)
    }

    val updaterApi: UpdateApi by lazy {
        storage.create(UpdateApi::class.java)
    }
}