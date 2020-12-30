package com.example.htecproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.htecproject.fragments.PostsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showPostsFragment()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
            finish()
        }
    }

    private fun showPostsFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val postsFragment = PostsFragment()
        transaction.replace(R.id.fragment_holder, postsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}