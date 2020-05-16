package com.example.puthomework

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_layout.view.*

class RecyclerAdapter(private val users: MutableList<UserModel>, private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun getItemCount() = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.name.text = user.name
        holder.id.text = user.id
        holder.createdAt.text = user.createdAt
        holder.job.text = user.job
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.tvName
        val id: TextView = itemView.tvId
        val createdAt: TextView = itemView.tvCreatedAt
        val job: TextView = itemView.tvJob
    }
}