//package com.example.githubapp.data.repository
//
//import android.app.Application
//import androidx.lifecycle.LiveData
//import com.example.githubapp.database.entity.FavoriteUser
//import com.example.githubapp.database.room.FavoriteUserDao
//import com.example.githubapp.database.room.FavoriteUserDatabase
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//class UserRepository(application: Application) {
//    private val mFavoriteUserDao : FavoriteUserDao
//
//    init {
//        val db = FavoriteUserDatabase.getInstance(application)
//        mFavoriteUserDao = db.favoriteUserDao()
//    }
//
//    fun getAllFavUsers():LiveData<List<FavoriteUser>>{
//        return mFavoriteUserDao.getAllFavUser()
//    }
//
//    suspend fun insert(favoriteUser: FavoriteUser){
//        withContext(Dispatchers.IO){
//            mFavoriteUserDao.insert(favoriteUser)
//        }
//    }
//
//    suspend fun delete(favoriteUser: FavoriteUser){
//        withContext(Dispatchers.IO){
//            mFavoriteUserDao.delete(favoriteUser)
//        }
//    }
//}