package com.example.htecproject.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.htecproject.AppData
import com.example.htecproject.R
import com.example.htecproject.fragments.UserFragment
import com.example.htecproject.interfaces.OnItemClickListener
import com.example.htecproject.model.Posts

class PostHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.post_title)
    val body: TextView = view.findViewById(R.id.post_body)
    val itemConstraint: ConstraintLayout = view.findViewById(R.id.postConstraintLayout)
    val imageViewDelete: ImageView = view.findViewById(R.id.iv_delete)
}

class PostAdapter(private val context: Context, private val postsList: List<Posts>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<PostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false)
        return PostHolder((view))
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = postsList[position]
        with(holder) {
            title.text = post.title
            body.text = post.body
            imageViewDelete.setOnClickListener {
                onItemClickListener.onItemClickListener(post)
            }
            itemConstraint.setOnClickListener {
                AppData.selectedPost = post
                val fragmentContext = context as FragmentActivity
                val transaction = fragmentContext.supportFragmentManager.beginTransaction()
                val userFragment = UserFragment()
                transaction.replace(R.id.fragment_holder, userFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}