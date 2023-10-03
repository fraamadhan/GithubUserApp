package com.example.githubapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.database.entity.FavoriteUser
import com.example.githubapp.databinding.ItemUsersBinding
import com.example.githubapp.ui.activity.UsersDetailActivity
import com.example.githubapp.util.FavUserDiffCallback

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>(){

    private val listFavUser = ArrayList<FavoriteUser>()
    fun setListFavoriteUsers(listFavUser: List<FavoriteUser>) {
        val diffCallback = FavUserDiffCallback(this.listFavUser, listFavUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavUser.clear()
        this.listFavUser.addAll(listFavUser)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavUser.size

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.bind(listFavUser[position])
        holder.itemView.setOnClickListener {
            val intentToDetail = Intent(holder.itemView.context, UsersDetailActivity::class.java)
            intentToDetail.putExtra(UsersDetailActivity.EXTRA_DATA, listFavUser[position].username)
            holder.itemView.context.startActivity(intentToDetail)
        }
    }

    class FavoriteUserViewHolder(private val binding : ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser){
            with(binding){
                username.text = favoriteUser.username
                Glide.with(itemView.context)
                    .load(favoriteUser.avatarUrl)
                    .circleCrop()
                    .into(binding.userProfileImage)
            }
        }
    }
}