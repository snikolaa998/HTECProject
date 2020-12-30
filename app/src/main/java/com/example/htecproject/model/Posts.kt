package com.example.htecproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_table")
data class Posts(@PrimaryKey(autoGenerate = true) var post_id: Int = 0, val userId: Int, val id: Int, val title: String, val body: String)