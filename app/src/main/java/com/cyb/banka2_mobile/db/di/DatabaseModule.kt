package com.cyb.banka2_mobile.db.di

import com.cyb.banka2_mobile.db.AppDatabase
import com.cyb.banka2_mobile.db.AppDatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(builder: AppDatabaseBuilder): AppDatabase = builder.build()
}