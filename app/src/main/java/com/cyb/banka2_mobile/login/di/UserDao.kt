package com.cyb.banka2_mobile.login.di

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cyb.banka2_mobile.login.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userEntity: UserEntity)

    @Query("SELECT * FROM UserEntity")
    suspend fun getUser(): List<UserEntity>
}