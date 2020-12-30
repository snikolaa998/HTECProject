package com.example.htecproject

import android.app.Application
import com.example.htecproject.model.Posts

class AppData : Application() {
    companion object {
        var selectedPost: Posts? = null
    }
}