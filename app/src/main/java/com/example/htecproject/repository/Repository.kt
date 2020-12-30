package com.example.htecproject.repository

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.htecproject.AppData
import com.example.htecproject.api.RetrofitInstance
import com.example.htecproject.database.PostDao
import com.example.htecproject.database.PostDb
import com.example.htecproject.model.Posts
import com.example.htecproject.model.User

class Repository(application: Application) {
    private var postDao: PostDao?
    init {
        val db = PostDb.getDatabase(application)
        postDao = db?.postDao()
    }

    suspend fun getPosts() : List<Posts> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun getUser() : User {
        return RetrofitInstance.api.getUser(AppData.selectedPost?.id.toString())
    }

    @Suppress
    @WorkerThread
    suspend fun insert(post: Posts) {
        postDao?.insertPost(post)
    }

    @Suppress
    @WorkerThread
    suspend fun delete() {
        postDao?.deletePosts()
    }

    @Suppress
    @WorkerThread
    suspend fun deletePost(id: Int) {
        postDao?.deletePost(id)
    }

    @Suppress
    @WorkerThread
    suspend fun getPostFromDatabase(): List<Posts> {
        return postDao?.getAllPostFromDatabase()!!
    }
}