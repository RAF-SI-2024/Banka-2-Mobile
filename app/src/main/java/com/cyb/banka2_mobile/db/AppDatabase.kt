package com.cyb.banka2_mobile.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cyb.banka2_mobile.login.di.UserDao
import com.cyb.banka2_mobile.login.model.UserEntity
import com.cyb.banka2_mobile.splash.DemoEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}