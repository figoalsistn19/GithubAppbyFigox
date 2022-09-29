package com.aran.githubappusersub3.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aran.githubappusersub3.data.response.Users
import com.aran.githubappusersub3.databinding.ItemUsersBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ListUsersAdapter : RecyclerView.Adapter<ListUsersAdapter.UsersViewHolder>() {

    private val listUsers = ArrayList<Users>()

    private var onItemClickedCallback: OnItemClickedCallback? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(itemData: ArrayList<Users>) {
        listUsers.clear()
        listUsers.addAll(itemData)
        notifyDataSetChanged()
    }

    interface OnItemClickedCallback {
        fun onItemClicked(data: Users)
    }

    inner class UsersViewHolder (private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: Users) {
            binding.root.setOnClickListener {
                onItemClickedCallback?.onItemClicked(users)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(users.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgItemPhoto)
                tvItemUsername.text = users.login
            }
        }
    }

    fun setOnItemClickCallback(onItemClickedCallback: OnItemClickedCallback) {
        this.onItemClickedCallback = onItemClickedCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val mView = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    override fun getItemCount(): Int = listUsers.size
}