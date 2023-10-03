package com.example.githubapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.githubapp.data.Result
import com.example.githubapp.data.retrofit.ApiService
import com.example.githubapp.database.entity.FavoriteUser
import com.example.githubapp.database.room.FavoriteUserDao
import com.example.githubapp.util.AppExecutors

class UsersRepository private constructor(
    private val apiService: ApiService,
    private val favUserDao : FavoriteUserDao,
    private val appExecutors : AppExecutors,
) {

    private val result = MediatorLiveData<Result<List<FavoriteUser>>>()
//    fun getFavUsers(usn : String) : LiveData<Result<List<FavoriteUser>>>{
//        result.value = Result.Loading
//        val client = apiService.getUser(usn)
//        client.enqueue(object : Callback<GithubResponse> {
//            override fun onResponse(
//                call: Call<GithubResponse>,
//                response: Response<GithubResponse>
//            ) {
//                if(response.isSuccessful){
//                    val favUsersResponse = response.body()?.items
//                    val favList = ArrayList<FavoriteUser>()
//                    appExecutors.diskIO.execute {
//                        favUsersResponse?.forEach {favoriteUser ->
//                            val isFavorited = favUserDao.isFavorited(favoriteUser.login)
//                            val favUsers = FavoriteUser(
//                                favoriteUser.login,
//                                favoriteUser.avatarUrl,
//                                isFavorited
//                            )
//                            favList.add(favUsers)
//                        }
//                        favUserDao.deleteAll()
//                        favUserDao.insert(favList)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
//                result.value = Result.Error(t.message.toString())
//            }
//        })
//        val localData = favUserDao.getAllFavUser()
//        result.addSource(localData){
//            newData : List<FavoriteUser> ->
//            result.value = Result.Success(newData)
//        }
//
//        return result
//    }

    fun getFavoritedUser() : LiveData<List<FavoriteUser>>{
        return favUserDao.getAllFavUser()
    }

    fun insertFavUser(favoriteUser: FavoriteUser){
        appExecutors.diskIO.execute{
            favUserDao.insert(favoriteUser)
        }
    }

    fun setFavoritedUser(favoriteUser: FavoriteUser, favoriteState : Boolean){
        appExecutors.diskIO.execute {
            favoriteUser.isFavorited = favoriteState
            favUserDao.updateFavUser(favoriteUser)
        }
    }

    fun isFavoritedUser(username : String) : LiveData<Boolean>{
        return favUserDao.isFavorited(username)
    }

    suspend fun checkFavorited(username: String) : Boolean{
        return favUserDao.checkFavorited(username)
    }

    suspend fun deleteAll(){
        favUserDao.deleteAll()
    }

    companion object{
        @Volatile
        private var instance: UsersRepository? = null
        fun getInstance(
            apiService: ApiService,
            favUserDao: FavoriteUserDao,
            appExecutors: AppExecutors
        ): UsersRepository =
            instance ?: synchronized(this){
                instance ?: UsersRepository(apiService, favUserDao, appExecutors)
            }.also { instance = it }
    }
}