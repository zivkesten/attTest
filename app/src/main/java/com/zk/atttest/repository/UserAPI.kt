package com.zk.atttest.repository

import com.zk.atttest.model.UsersResponse
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.http.GET

val apiModule = module {
    single {
        val retrofit: Retrofit = get()
        retrofit.create(UserAPI::class.java)
    }
}

interface UserAPI {
    @GET("api")
    suspend fun getUsers(): UsersResponse?
}
