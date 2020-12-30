package com.example.htecproject.fragments

import android.app.Service
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.htecproject.R
import com.example.htecproject.adapters.PostAdapter
import com.example.htecproject.interfaces.OnItemClickListener
import com.example.htecproject.model.Posts
import com.example.htecproject.repository.Repository
import com.example.htecproject.util.Constants
import com.example.htecproject.viewModel.PostsFragmentViewModel
import com.example.htecproject.viewModel.PostsViewModelFactory

class PostsFragment : Fragment(), OnItemClickListener {


    private lateinit var postsViewModel: PostsFragmentViewModel
    private lateinit var postsRecyclerView: RecyclerView
    private lateinit var mPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsRecyclerView = view.findViewById(R.id.post_recycler)
        val refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        mPreferences = activity?.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)!!

        if (mPreferences.getBoolean("firstrun", true)) {
            if (networkAvailable()) {
                mPreferences.edit().putBoolean("firstrun", false).apply()
                getData()
            } else {
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
            }
        } else {
            getDataFromDatabase()
        }
        refreshLayout.setOnRefreshListener {
            refreshData(refreshLayout)
        }
    }

    override fun onItemClickListener(post: Posts) {
        val id = post.id
        postsViewModel.deletePost(id)
    }

    private fun getData() {
        val repository = Repository(requireActivity().application)
        val postsViewModelFactory = PostsViewModelFactory(repository)
        postsViewModel = ViewModelProvider(this, postsViewModelFactory).get(PostsFragmentViewModel::class.java)
        postsViewModel.getAllPosts()
        postsViewModel.posts.observe(this, Observer {
            postsViewModel.delete()
            for (data in it) {
                postsViewModel.insert(data)
            }
            postsViewModel.getPostsFromDatabase()
            postsViewModel.databasePost.observe(this, Observer {databasePost ->
                val adapter = PostAdapter(requireContext(), databasePost, this)
                postsRecyclerView.apply {
                    this.adapter = adapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            })
        })
    }

    private fun getDataFromDatabase() {
        val repository = Repository(requireActivity().application)
        val postsViewModelFactory = PostsViewModelFactory(repository)
        postsViewModel = ViewModelProvider(this, postsViewModelFactory).get(PostsFragmentViewModel::class.java)
        postsViewModel.getPostsFromDatabase()
        postsViewModel.databasePost.observe(this, Observer {
            val adapter = PostAdapter(requireContext(), it, this)
            postsRecyclerView.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    private fun refreshData(layout: SwipeRefreshLayout) {
        if (networkAvailable()) {
            getData()
        } else {
            Toast.makeText(requireContext(), "No internet connection!", Toast.LENGTH_SHORT).show()
        }
        layout.isRefreshing = false
        }
    }