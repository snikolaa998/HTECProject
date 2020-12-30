package com.example.htecproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.htecproject.model.Posts

@Dao
interface PostDao {
    @Insert
    suspend fun insertPost(post: Posts)

    @Query("DELETE FROM posts_table")
    suspend fun deletePosts()

    @Query("SELECT * FROM posts_table")
    suspend fun getAllPostFromDatabase(): List<Posts>

    @Query("DELETE FROM posts_table WHERE id = :id")
    suspend fun deletePost(id: Int)
}