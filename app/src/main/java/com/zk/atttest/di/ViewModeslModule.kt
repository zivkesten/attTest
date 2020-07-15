package com.zk.atttest.di

import com.zk.atttest.viewModel.UserViewModel
import com.zk.atttest.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ViewModeslModule {
	companion object{
		val modules = module {
			viewModel { MainViewModel(get()) }
			viewModel { UserViewModel() }
		}
	}
}
