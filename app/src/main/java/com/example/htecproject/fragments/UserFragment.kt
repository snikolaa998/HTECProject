package com.example.htecproject.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.htecproject.AppData
import com.example.htecproject.R
import com.example.htecproject.adapters.UserAdapter
import com.example.htecproject.repository.Repository
import com.example.htecproject.viewModel.UserFragmentViewModel
import com.example.htecproject.viewModel.UserViewModelFactory

class UserFragment : Fragment() {

    private lateinit var userViewModel: UserFragmentViewModel
    private lateinit var userRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("UserFragment", AppData.selectedPost?.body.toString())
        userRecyclerView = view.findViewById(R.id.user_recycler)
        val repository = Repository(requireActivity().application)
        val userViewModelFactory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserFragmentViewModel::class.java)
        userViewModel.getUser()
        userViewModel.user.observe(this, Observer {
            val adapter = UserAdapter(requireContext(), it)
            userRecyclerView.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }
}