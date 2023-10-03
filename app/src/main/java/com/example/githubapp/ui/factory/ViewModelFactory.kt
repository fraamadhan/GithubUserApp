package com.example.githubapp.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.data.repository.UsersRepository
import com.example.githubapp.di.Injection
import com.example.githubapp.ui.viewmodel.DetailUserViewModel
import com.example.githubapp.ui.viewmodel.FavoriteUserViewModel

class ViewModelFactory private constructor(private val usersRepository: UsersRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(usersRepository) as T
        }
        if(modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)){
            return FavoriteUserViewModel(usersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}