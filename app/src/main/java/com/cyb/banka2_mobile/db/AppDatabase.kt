package com.cyb.banka2_mobile.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cyb.banka2_mobile.splash.DemoEntity

@Database(
    entities = [
        DemoEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {

}