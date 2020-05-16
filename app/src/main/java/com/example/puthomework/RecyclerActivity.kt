package com.example.puthomework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : AppCompatActivity() {
    private val users = mutableListOf<UserModel>()
    lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val intent = intent
        val name = intent.getStringExtra("name")
        val job = intent.getStringExtra("job")
        val created = intent.getStringExtra("createdAt")
        val id = intent.getStringExtra("id")

        users.add(UserModel(name!!, job!!, id!!, created!!))

        recyclerView.layoutManager = LinearLayoutManager(this@RecyclerActivity)
        adapter = RecyclerAdapter(users, this@RecyclerActivity)
        recyclerView.adapter = adapter
    }
}
