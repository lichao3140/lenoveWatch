package cn.ycgo.base.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import cn.ycgo.base.common.storage.CommonSPEngine
import com.ycgo.base.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object LxcRetrofitFactory{

    private var authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${CommonSPEngine.getToken()}")
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val loggingInterceptor =  HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private var client = if (BuildConfig.DEBUG) {
        OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }


    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

}