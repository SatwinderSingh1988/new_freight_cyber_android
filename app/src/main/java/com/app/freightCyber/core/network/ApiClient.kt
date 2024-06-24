package com.app.freightCyber.core.network

import android.content.Context
import com.app.freightCyber.app.AppConfigManager
import com.app.freightCyber.app.isProd
import com.app.freightCyber.data.source.local.prefrences.SharedPrefManager
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Invocation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val applicationContext: Context,
    private val gson: Gson,
    private val sharedPreferences: SharedPrefManager
) {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val cacheInterceptor = Interceptor { chain ->
        val response: Response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .onlyIfCached()
            .maxAge(1, TimeUnit.DAYS)
            .minFresh(4, TimeUnit.HOURS)
            .maxStale(8, TimeUnit.HOURS)
            .build()
        response.newBuilder()
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }

    private val headerInterceptor = Interceptor { chain ->
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()
        val requiresAuth = request.tag(Invocation::class.java)?.method()?.isAnnotationPresent(RequiresAuthorization::class.java) == true
        if (requiresAuth) {
            sharedPreferences.let {
                newRequestBuilder.header(HEADER_AUTHORIZATION, it.getToken())
            }
        }
        chain.proceed(newRequestBuilder.build())
    }

    fun getApiClient(): Retrofit = Retrofit.Builder()
        .client(createHttpClient())
        .baseUrl(AppConfigManager.config.baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private fun createHttpClient(): OkHttpClient {
        val dispatcher = Dispatcher().apply { maxRequests = MAX_REQUESTS }
        val clientBuilder = OkHttpClient.Builder().apply {
            cache(
                Cache(
                    applicationContext.cacheDir,
                    CACHE_SIZE_LIMIT
                )
            )
            readTimeout(REQUEST_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            writeTimeout(REQUEST_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            retryOnConnectionFailure(true)
            pingInterval(PING_INTERVAL, TimeUnit.SECONDS)
            addInterceptor(headerInterceptor)
            if (!AppConfigManager.environment.isProd) {
                addNetworkInterceptor(loggingInterceptor)
            }
            dispatcher(dispatcher)
        }
        return clientBuilder.build()
    }

    companion object {
        private const val CACHE_SIZE_LIMIT = (20 * 1024 * 1024).toLong() //20 mb
        private const val REQUEST_TIMEOUT_MS = 30_000L
        private const val MAX_REQUESTS = 1
        private const val PING_INTERVAL = 5L
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_X_TOKEN = "X-TOKEN"
        private const val HEADER_X_CHECK = "X-CHECK"
        private const val HEADER_CACHE_CONTROL = "Cache-Control"
    }


}