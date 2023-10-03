package com.example.githubapp.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.ActivityFavoriteUserBinding
import com.example.githubapp.ui.FavoriteUserAdapter
import com.example.githubapp.ui.factory.ViewModelFactory
import com.example.githubapp.ui.viewmodel.FavoriteUserViewModel

class FavoriteUserActivity : AppCompatActivity() {

    private var binding : ActivityFavoriteUserBinding? = null
    private lateinit var adapter : FavoriteUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.toolbar?.title = "Favorite User"

        val factory : ViewModelFactory = ViewModelFactory.getInstance(this)
        val favoriteUserViewModel : FavoriteUserViewModel by viewModels{
            factory
        }

        favoriteUserViewModel.getFavoritedUser().observe(this@FavoriteUserActivity){
            listFavoriteUser ->
            if(listFavoriteUser != null){
                adapter.setListFavoriteUsers(listFavoriteUser)
            }
        }

        adapter = FavoriteUserAdapter()

        binding?.apply {
            rvFavoriteUser.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvFavoriteUser.setHasFixedSize(true)
            rvFavoriteUser.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}