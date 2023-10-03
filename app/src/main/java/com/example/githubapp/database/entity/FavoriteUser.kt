package com.example.githubapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favUser")
data class FavoriteUser(
    @PrimaryKey
    @ColumnInfo(name="username")
    var username : String = "",

    @ColumnInfo(name="avatarUrl")
    var avatarUrl : String? = null,

    @ColumnInfo(name="favorited")
    var isFavorited : Boolean = false,
)
