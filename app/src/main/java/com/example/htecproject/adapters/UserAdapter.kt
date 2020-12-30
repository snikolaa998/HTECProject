package com.example.htecproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.htecproject.AppData
import com.example.htecproject.R
import com.example.htecproject.model.User

class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.user_title)
    val body: TextView = view.findViewById(R.id.user_body)
    val email: TextView = view.findViewById(R.id.user_email)
    val name: TextView = view.findViewById(R.id.user_name)
}

class UserAdapter(private val context: Context, private val user: User) : RecyclerView.Adapter<UserHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return UserHolder((view))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        with(holder) {
            title.text = AppData.selectedPost?.title
            body.text = AppData.selectedPost?.body
            email.text = user.email
            name.text = user.name
        }
    }
}