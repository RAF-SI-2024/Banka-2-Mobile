package com.cyb.banka2_mobile.exchange.api.di

import com.cyb.banka2_mobile.exchange.api.ExchangeApi
import com.cyb.banka2_mobile.login.api.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExchangeModule {
    @Provides
    @Singleton
    fun provideExchangeApi(retrofit: Retrofit): ExchangeApi {
        return retrofit.create(ExchangeApi::class.java)
    }
}