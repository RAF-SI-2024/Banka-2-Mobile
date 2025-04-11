package com.cyb.banka2_mobile.login.repository

import com.cyb.banka2_mobile.db.AppDatabase
import com.cyb.banka2_mobile.login.api.LoginApi
import com.cyb.banka2_mobile.login.api.model.LoginRequest
import com.cyb.banka2_mobile.login.api.model.mappers.toDbModel
import com.cyb.banka2_mobile.login.api.model.mappers.toUiModel
import com.cyb.banka2_mobile.login.model.UserUiModel
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginApi: LoginApi,
    private val database: AppDatabase
) {
    suspend fun doLogin(request: LoginRequest): Boolean {
        val response = loginApi.login(request)

        if (response.isSuccessful) {
            response.body()?.toDbModel()?.let { database.userDao().addUser(it) }
            return true
        }
        return false
    }

    suspend fun getUser(): UserUiModel {
        val response = database.userDao().getUser()

        if (response.isNotEmpty())
            return response[0].toUiModel()
        return UserUiModel(firstName = "", lastName = "", token = "")
    }

    suspend fun delete() {
        database.userDao().clearUsers()
    }
}