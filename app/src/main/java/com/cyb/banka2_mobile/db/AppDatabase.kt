package com.cyb.banka2_mobile.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cyb.banka2_mobile.login.di.UserDao
import com.cyb.banka2_mobile.login.model.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}