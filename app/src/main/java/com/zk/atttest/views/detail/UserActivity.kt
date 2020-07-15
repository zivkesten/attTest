package com.zk.atttest.views.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zk.atttest.R
import com.zk.atttest.viewModel.UserViewModel
import com.zk.atttest.model.Event
import com.zk.atttest.model.Item
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserActivity : AppCompatActivity() {

    private val viewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val intent: Item? = intent.getParcelableExtra(this.getString(R.string.extra_item))
        viewModel.event(Event.DataReceived(intent))
        supportFragmentManager
            .beginTransaction()
            .add(R.id.user_container, UserFragment.newInstance())
            .commit()
    }
}