package com.example.githubapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.data.response.ItemsItem
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.ui.UserAdapter
import com.example.githubapp.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        val adapter = UserAdapter()
        binding.rvUsers.adapter = adapter


        mainViewModel.listUser.observe(this){
            listUser ->
            adapter.submitList(listUser)
        }
        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{textView, actionId, event->
                    mainViewModel.getUser(searchView.text.toString())
                    searchView.hide()
                    false
                }
            searchBar.inflateMenu(R.menu.menu_form)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId){
                    R.id.fav_list_menu -> {
                        val intentToFavList = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                        startActivity(intentToFavList)
                        true
                    }
                    else-> {
                        false
                    }
                }
            }
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intentToDetail = Intent(this@MainActivity, UsersDetailActivity::class.java)
                intentToDetail.putExtra(UsersDetailActivity.EXTRA_DATA, data.login)
                intentToDetail.putExtra(UsersDetailActivity.EXTRA_PICTURE, data.avatarUrl)
                startActivity(intentToDetail)
            }
        })
    }


    private fun showLoading(isLoading : Boolean){
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }
        else{
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}