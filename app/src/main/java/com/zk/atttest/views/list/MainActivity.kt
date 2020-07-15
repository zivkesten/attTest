package com.zk.atttest.views.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.zk.atttest.R
import com.zk.atttest.model.ViewEffect
import com.zk.atttest.viewModel.MainViewModel
import com.zk.atttest.views.detail.UserActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val model by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, UserListFragment.newInstance(), UserListFragment::class.qualifiedName)
            .commit()

        observeViewEffect()
    }

    private fun observeViewEffect() {
        model.obtainViewEffects.observe(this, Observer {
            trigger(it)
        })
    }

    private fun trigger(effect: ViewEffect) {
        when(effect) {
            is ViewEffect.TransitionToScreen -> {
                val intent = Intent(this, UserActivity::class.java)
                intent.putExtra(getString(R.string.extra_item), effect.item)
                startActivity(intent)
            }
        }
    }
}
