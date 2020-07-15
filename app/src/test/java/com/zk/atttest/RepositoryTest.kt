package com.zk.atttest

import kotlinx.coroutines.runBlocking
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.zk.atttest.model.*
import com.zk.atttest.repository.Repository
import com.zk.atttest.repository.UserAPI
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException

@RunWith(JUnit4::class)
class RepositoryTest {

    private lateinit var userAPI: UserAPI
    private lateinit var repository: Repository

    private val users = UsersResponse(emptyList(), Info())

    @Before
    fun setUp() {
        userAPI = mock()
        val mockException: HttpException = mock()
        whenever(mockException.code()).thenReturn(401)
        runBlocking {
            whenever(userAPI.getUsers()).thenReturn(users)
        }
        repository = Repository(userAPI)
    }

    @Test
    fun `test getNews then articles is returned`() {
        runBlocking {
            assertEquals(users, repository.getUsers())
        }
    }
}