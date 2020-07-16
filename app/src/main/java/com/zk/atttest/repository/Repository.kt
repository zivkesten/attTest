package com.zk.atttest.repository

import com.google.gson.Gson
import com.zk.atttest.model.UsersResponse
import org.koin.dsl.module

val repositoryModule = module {

    single { Repository(get()) }
}

class Repository(private val userAPI: UserAPI) {
    private val networkError = "Error getting users"
    suspend fun getUsers(): UsersResponse? {
        var defaultResponse = UsersResponse(errorMessage = networkError)
        val response = userAPI.getUsers()

        response.body()?.let { defaultResponse = it }

        response.errorBody()?.let { body ->
            defaultResponse = Gson().fromJson(
                body.string(),
                UsersResponse::class.java
            )
        }
        return defaultResponse
    }
}
