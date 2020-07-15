package com.zk.atttest.mainLIst

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zk.atttest.R
import com.zk.atttest.databinding.UserListItemBinding
import com.zk.atttest.model.Item
import com.zk.atttest.model.name

class UsersRecyclerViewAdapter(private var values: List<Item> = ArrayList(),
                               private val listener: OnItemClickListener)
    : RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>() {

    fun update(articles: List<Item>) {
        if (values.isEmpty()) {
            values = articles
            notifyDataSetChanged()
            return
        }
        val diffResult = DiffUtil.calculateDiff(UsersListDiffUtil(values, articles))
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(values[position])

    override fun getItemCount(): Int {
        return values.size
    }

    inner class ViewHolder(private val binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            with(binding) {
                primaryText.text = item.name()
                Picasso.get()
                    .load(item.picture?.medium)
                    .placeholder(R.drawable.progress_animation)
                    .into(mediaImage)
                binding.root.setOnClickListener {
                    listener.onItemClick(item)
                }
            }
        }
    }
}
