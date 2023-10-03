package com.example.githubapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.FragmentFollowDetailBinding
import com.example.githubapp.ui.FollowerAdapter
import com.example.githubapp.ui.FollowingAdapter
import com.example.githubapp.ui.viewmodel.FollowManagerViewModel

class FollowDetailFragment : Fragment() {

    private lateinit var binding : FragmentFollowDetailBinding
    private val followDetailManagerViewModel : FollowManagerViewModel by viewModels()

    companion object{
        const val ARG_POSITION ="section number"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position : Int? = arguments?.getInt(ARG_POSITION, 0)
        var  username : String? = arguments?.getString(ARG_USERNAME)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowDetailFragment.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowDetailFragment.addItemDecoration(itemDecoration)

        val adapterFollowing = FollowingAdapter()
        val adapterFollower = FollowerAdapter()

        when(position){
            1 -> {
                followDetailManagerViewModel.getFollowers(username)

            }
            2 -> {
                followDetailManagerViewModel.getFollowing(username)

            }

        }
        followDetailManagerViewModel.listFollowers.observe(requireActivity()){
            binding.rvFollowDetailFragment.adapter = adapterFollower
            adapterFollower.submitList(it)
        }
        followDetailManagerViewModel.listFollowing.observe(requireActivity()){
            binding.rvFollowDetailFragment.adapter = adapterFollowing
            adapterFollowing.submitList(it)
        }

        followDetailManagerViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }

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