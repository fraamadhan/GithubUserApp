package com.example.githubapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.githubapp.data.repository.UsersRepository

class FavoriteUserViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    fun getFavoritedUser() = usersRepository.getFavoritedUser()
}
