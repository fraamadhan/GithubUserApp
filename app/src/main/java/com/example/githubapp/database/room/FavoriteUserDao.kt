package com.example.githubapp.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubapp.database.entity.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("DELETE FROM favUser WHERE favorited = 0")
    suspend fun deleteAll()

    @Query("SELECT * FROM favUser WHERE favorited = 1")
    fun getAllFavUser(): LiveData<List<FavoriteUser>>

    @Update
    fun updateFavUser(favoriteUser: FavoriteUser)

    @Query("SELECT EXISTS(SELECT * FROM favUser WHERE username = :username AND favorited = 1)")
    fun isFavorited(username: String): LiveData<Boolean>

    @Query("SELECT EXISTS(SELECT * FROM favUser WHERE username = :username AND favorited = 1)")
    suspend fun checkFavorited(username: String): Boolean
}