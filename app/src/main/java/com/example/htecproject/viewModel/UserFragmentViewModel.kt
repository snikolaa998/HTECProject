package com.example.htecproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.htecproject.model.User
import com.example.htecproject.repository.Repository
import kotlinx.coroutines.launch

class UserFragmentViewModel(private val repository: Repository) : ViewModel() {
    val user: MutableLiveData<User> = MutableLiveData()
    fun getUser() {
        viewModelScope.launch {
            val response = repository.getUser()
            user.value = response
        }
    }
}

class UserViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserFragmentViewModel(repository) as T
    }
}