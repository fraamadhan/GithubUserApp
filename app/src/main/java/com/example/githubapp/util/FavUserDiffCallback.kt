package com.example.githubapp.util

import androidx.recyclerview.widget.DiffUtil
import com.example.githubapp.database.entity.FavoriteUser

class FavUserDiffCallback(private val oldFavUserList: List<FavoriteUser>, private val newFavUserList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavUserList.size
    override fun getNewListSize(): Int = newFavUserList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavUserList[oldItemPosition].username == newFavUserList[newItemPosition].username
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavUser = oldFavUserList[oldItemPosition]
        val newFavUser = newFavUserList[newItemPosition]
        return oldFavUser.username == newFavUser.username && oldFavUser.avatarUrl == newFavUser.avatarUrl
    }
}