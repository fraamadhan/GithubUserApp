package com.example.githubapp.di

import android.content.Context
import com.example.githubapp.data.repository.UsersRepository
import com.example.githubapp.data.retrofit.ApiConfig
import com.example.githubapp.database.room.FavoriteUserDatabase
import com.example.githubapp.util.AppExecutors

object Injection {
    fun provideRepository(context : Context) : UsersRepository{
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return UsersRepository.getInstance(apiService, dao, appExecutors)
    }
}