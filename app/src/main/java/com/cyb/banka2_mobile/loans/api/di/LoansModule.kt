package com.cyb.banka2_mobile.loans.api.di

import com.cyb.banka2_mobile.loans.api.LoansApi
import com.cyb.banka2_mobile.login.api.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoansModule {
    @Provides
    @Singleton
    fun provideLoansApi(retrofit: Retrofit): LoansApi {
        return retrofit.create(LoansApi::class.java)
    }
}