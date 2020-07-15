package com.zk.atttest.views.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zk.atttest.R
import com.zk.atttest.databinding.FragmentUserListBinding
import com.zk.atttest.mainLIst.UsersRecyclerViewAdapter
import com.zk.atttest.mainLIst.OnItemClickListener
import com.zk.atttest.mainLIst.VerticalSpaceItemDecoration
import com.zk.atttest.model.Event
import com.zk.atttest.model.Item
import com.zk.atttest.model.ListViewState
import com.zk.atttest.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class UserListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

	companion object {
		fun newInstance(): UserListFragment {
			return UserListFragment()
		}
	}

	private lateinit var binding: FragmentUserListBinding

	private val usersAdapter: UsersRecyclerViewAdapter = UsersRecyclerViewAdapter(listener = this)

	// Lazy Inject ViewModel
	private val viewModel by sharedViewModel<MainViewModel>()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentUserListBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupBinding()
		observeViewState()
		if (savedInstanceState == null) {
			viewModel.event(Event.ScreenLoad)
		}
	}

	private fun setupBinding() {
		binding.swiperefresh.setOnRefreshListener(this)
		binding.list.apply {
			layoutManager = GridLayoutManager(context, 2)
			addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.list_item_decoration)))
			adapter = usersAdapter
		}
	}

	private fun observeViewState() {
		viewModel.obtainState.observe(viewLifecycleOwner, Observer {
			render(it)
		})
	}

	private fun render(state: ListViewState) {
		state.adapterList.let { usersAdapter.update(it) }
		swiperefresh.isRefreshing = false
	}

	override fun onRefresh() {
		viewModel.event(Event.SwipeToRefreshEvent)
	}

	override fun onItemClick(item: Item) {
		viewModel.event(Event.ListItemClicked(item))
	}
}
