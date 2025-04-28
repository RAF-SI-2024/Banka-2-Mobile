package com.cyb.banka2_mobile.networking.di

import com.cyb.banka2_mobile.networking.serialization.AppJson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(
        tokenProvider: TokenProvider,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                    .addHeader("CustomHeader", "CustomValue")

                tokenProvider.getToken()?.let { token ->
                    println("Jovan token: $token")
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())
            }
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3031/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}