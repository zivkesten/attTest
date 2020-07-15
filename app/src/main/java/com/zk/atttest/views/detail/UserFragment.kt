package com.zk.atttest.views.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.zk.atttest.databinding.UserFragmentBinding
import com.zk.atttest.viewModel.UserViewModel
import com.zk.atttest.model.DetailViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private val viewModel by sharedViewModel<UserViewModel>()

    private lateinit var binding: UserFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
    }

    private fun observeViewState() {
        viewModel.obtainStateDetail.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })
    }

    private fun renderState(stateDetail: DetailViewState) {
        with(binding) {
            title.text = stateDetail.title
            email.text = stateDetail.email
            phone.text = stateDetail.phone
            address.text = stateDetail.address
            Picasso.get().load(stateDetail.backDrop).into(backdrop)
        }
    }
}