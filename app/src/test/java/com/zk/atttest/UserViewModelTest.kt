package com.zk.atttest

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.zk.atttest.model.*
import com.zk.atttest.repository.Repository
import com.zk.atttest.viewModel.UserViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException

@RunWith(JUnit4::class)
class UserViewModelTest {

    @Before
    fun setUp() {

    }

    private lateinit var viewModel: UserViewModel

    @Test
    fun `onScreenLoad user data should be available`() {
        viewModel = UserViewModel()
        val mockItem = Item("1",
            Name("1", "1", "1"),
            Location(Street(0,"1"), "1", "1","1"),
            "1",
            "1")
        viewModel.event(Event.DataReceived(mockItem))
        Assert.assertEquals(mockItem, viewModel.obtainStateDetail.value)

    }
}