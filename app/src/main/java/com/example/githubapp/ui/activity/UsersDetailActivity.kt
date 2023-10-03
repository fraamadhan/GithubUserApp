package com.example.githubapp.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.data.response.GithubUserDetailResponse
import com.example.githubapp.database.entity.FavoriteUser
import com.example.githubapp.databinding.ActivityUsersDetailBinding
import com.example.githubapp.ui.SectionPagerAdapter
import com.example.githubapp.ui.factory.ViewModelFactory
import com.example.githubapp.ui.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class UsersDetailActivity : AppCompatActivity(){

    private var favoriteUser : FavoriteUser = FavoriteUser()

    private lateinit var binding : ActivityUsersDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory : ViewModelFactory = ViewModelFactory.getInstance(this)
        val detailUserViewModel : DetailUserViewModel by viewModels{
            factory
        }

        val username = intent.getStringExtra(EXTRA_DATA)

        detailUserViewModel.isLoading.observe(this){
            showLoading(it)
        }
        detailUserViewModel.user.observe(this){
                user ->
            setDataUser(user)
        }


        if(detailUserViewModel.getDetailUser(username!!) != null){
            detailUserViewModel.getFavoritedUser(username!!).observe(this){
                    isFavorite->
                if(isFavorite){
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                }
                else{
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                }
            }
        }

        binding.apply {
            fabFavorite.setOnClickListener {
                favoriteUser?.username = detailUserViewModel.getUserName()
                favoriteUser?.avatarUrl = detailUserViewModel.getAvatarUrl()

                lifecycleScope.launch {
                    val isFavorited = detailUserViewModel.checkFavorited(username)
                    if(isFavorited){
                        Log.d("INI IF : ", isFavorited.toString())
                        binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                        detailUserViewModel.deleteUser(favoriteUser)
                        detailUserViewModel.deleteAll()
                    }
                    else{
                        Log.d("INI ELSE : ", isFavorited.toString())
                        binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                        favoriteUser?.isFavorited = true
                        detailUserViewModel.insertFavUser(favoriteUser)
                    }
                }

            }
        }

        detailUserViewModel.getDetailUser(username!!)

        val sectionPagerAdapter = SectionPagerAdapter(this, username)
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs : TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){
                tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getTotalRow(row : Int) : Int{
        return row
    }

    private fun setDataUser(user : GithubUserDetailResponse){
        Glide.with(this)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.userDetailsProfileimg)

        binding.userFullNameDetail.text = user.name
        binding.usernameDetail.text = user.login
        binding.followers.text = String.format(getString(R.string.total_followers), user.followers)
        binding.following.text = String.format(getString(R.string.total_followings), user.following)
    }

    private fun showLoading(isLoading : Boolean){
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }
        else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }


    companion object{
        const val EXTRA_DATA = "extra_data"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.followings,
        )
        const val EXTRA_PICTURE = "extra_pictures"
    }
}