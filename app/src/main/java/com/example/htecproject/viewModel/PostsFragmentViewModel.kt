package com.example.htecproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.htecproject.model.Posts
import com.example.htecproject.repository.Repository
import kotlinx.coroutines.launch

class PostsFragmentViewModel(private val repository: Repository) : ViewModel() {

    val posts: MutableLiveData<List<Posts>> = MutableLiveData()
    val databasePost: MutableLiveData<List<Posts>> = MutableLiveData()

    fun getPostsFromDatabase() {
        viewModelScope.launch {
            databasePost.value = repository.getPostFromDatabase()
        }
    }

    fun getAllPosts() {
        viewModelScope.launch {
            val response = repository.getPosts()
            posts.value = response
        }
    }
    fun insert(post: Posts) {
        viewModelScope.launch {
            repository.insert(post)
        }
    }

    fun delete() {
        viewModelScope.launch {
            repository.delete()
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            repository.deletePost(id)
            getPostsFromDatabase()
        }
    }
}

class PostsViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostsFragmentViewModel(repository) as T
    }
}