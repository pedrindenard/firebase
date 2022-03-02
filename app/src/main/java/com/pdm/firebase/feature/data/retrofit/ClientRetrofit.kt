package com.pdm.firebase.feature.data.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pdm.firebase.BuildConfig
import com.pdm.firebase.util.setLanguage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ClientRetrofit {

    companion object {

        fun create(): Api {

            val clientHttp = OkHttpClient.Builder()
                .connectTimeout(timeout = 60, TimeUnit.SECONDS)
                .readTimeout(timeout = 60, TimeUnit.SECONDS)
                .addInterceptor(ServiceInterceptor())
                .build()

            val clientRetrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_API_URL)
                .client(clientHttp)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return clientRetrofit.create(Api::class.java)
        }
    }

    class ServiceInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            chain.request().apply {
                return chain.proceed(
                    this.newBuilder().url(
                        this.url.newBuilder()
                            .addQueryParameter(name = "api_key", value = BuildConfig.TMDB_API_KEY)
                            .addQueryParameter(name = "language", value = this.setLanguage())
                            .build()
                    ).build()
                )
            }
        }
    }
}