package com.example.githubapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.data.response.FollowersUserResponseItem
import com.example.githubapp.databinding.ItemUsersBinding
import com.example.githubapp.ui.activity.UsersDetailActivity

class FollowerAdapter : ListAdapter<FollowersUserResponseItem, FollowerAdapter.MyViewHolder>(
    DIFF_CALLBACK){
    class MyViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : FollowersUserResponseItem){
            binding.username.text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.userProfileImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val intentToDetail = Intent(holder.itemView.context, UsersDetailActivity::class.java)
            intentToDetail.putExtra(UsersDetailActivity.EXTRA_DATA, user.login)
            holder.itemView.context.startActivity(intentToDetail)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowersUserResponseItem>(){
            override fun areItemsTheSame(
                oldItem: FollowersUserResponseItem,
                newItem: FollowersUserResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FollowersUserResponseItem,
                newItem: FollowersUserResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}