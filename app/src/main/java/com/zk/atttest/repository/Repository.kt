package com.zk.atttest.repository

import com.zk.atttest.model.UsersResponse
import org.koin.dsl.module

val repositoryModule = module {

    single { Repository(get()) }
}

class Repository(private val userAPI: UserAPI)  {

   suspend fun getUsers(): UsersResponse? {
       return userAPI.getUsers()
   }
}
