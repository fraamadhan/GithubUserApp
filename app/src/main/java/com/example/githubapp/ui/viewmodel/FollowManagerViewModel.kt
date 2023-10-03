package com.example.githubapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.data.response.FollowersUserResponseItem
import com.example.githubapp.data.response.FollowingUserResponseItem
import com.example.githubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowManagerViewModel : ViewModel() {


    private val _listFollowing = MutableLiveData<List<FollowingUserResponseItem>>()
    val listFollowing : LiveData<List<FollowingUserResponseItem>> = _listFollowing

    private val _listFollowers = MutableLiveData<List<FollowersUserResponseItem>>()
    val listFollowers : LiveData<List<FollowersUserResponseItem>> = _listFollowers
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object{
        const val FOLLSTAG = "FollowManagerViewModel"
    }

    fun getFollowing(usn : String?){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(usn)
        client.enqueue(object : Callback<List<FollowingUserResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowingUserResponseItem>>,
                response: Response<List<FollowingUserResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listFollowing.value = response.body()
                }
                else{
                    Log.e(FOLLSTAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FOLLSTAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    fun getFollowers(usn : String?){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(usn)
        client.enqueue(object: Callback<List<FollowersUserResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowersUserResponseItem>>,
                response: Response<List<FollowersUserResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listFollowers.value = response.body()
                }
                else{
                    Log.e(FOLLSTAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersUserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FOLLSTAG, "onFailure : ${t.message.toString()}")
            }

        })

    }
}