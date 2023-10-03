package com.example.githubapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.repository.UsersRepository
import com.example.githubapp.data.response.GithubUserDetailResponse
import com.example.githubapp.data.retrofit.ApiConfig
import com.example.githubapp.database.entity.FavoriteUser
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel (private val usersRepository: UsersRepository) : ViewModel() {

    private val _user = MutableLiveData<GithubUserDetailResponse>()
    val user : LiveData<GithubUserDetailResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    fun getFavoritedUser(username : String) : LiveData<Boolean>{
        return usersRepository.isFavoritedUser(username)
    }

    suspend fun checkFavorited(username: String) : Boolean{
        return viewModelScope.async {
            usersRepository.checkFavorited(username)
        }.await()
    }

//    fun getStateFavorit(username : String) : Boolean{
//        viewModelScope.launch {
//            val state = usersRepository.statusFavorite(username)
//        }
//        return state
//    }


//    fun saveUser(favoriteUser: FavoriteUser){
//        usersRepository.setFavoritedUser(favoriteUser, true)
//    }

    fun insertFavUser(favoriteUser: FavoriteUser){
        usersRepository.insertFavUser(favoriteUser)
    }

    fun deleteAll(){
        viewModelScope.launch {
            usersRepository.deleteAll()
        }
    }

    fun deleteUser(favoriteUser: FavoriteUser){
        usersRepository.setFavoritedUser(favoriteUser, false)
    }

    fun getUserName() : String{
        return user.value!!.login
    }

    fun getAvatarUrl() : String{
        return user.value!!.avatarUrl
    }

    fun getDetailUser(usn : String = ""){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(usn)
        client.enqueue(object : Callback<GithubUserDetailResponse> {
            override fun onResponse(
                call: Call<GithubUserDetailResponse>,
                response: Response<GithubUserDetailResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _user.value = response.body()
                }
                else{
                    Log.e(DETAILTAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubUserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DETAILTAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    companion object{
        private const val DETAILTAG = "DetailUserViewModel"
    }

}