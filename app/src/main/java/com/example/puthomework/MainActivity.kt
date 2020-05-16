package com.example.puthomework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val users = mutableListOf<UserModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPutUser.setOnClickListener{
            addUser()
        }
    }


    private fun addUser() {
        val name = etName.text.toString()
        val job = etJob.text.toString()
        val parameters = mutableMapOf<String, String>()
        parameters["name"] = name
        parameters["job"] = job
        HttpRequest.postRequest(HttpRequest.USERS, parameters, object : CustomCallback {
            override fun onSuccess(body: String) {
                val json = JSONObject(body)
                val id = json.getString("id")
                val createdAt = json.getString("createdAt")

                users.add(UserModel(name, job, id, createdAt))

                //
                val intent = Intent(this@MainActivity, RecyclerActivity::class.java)
                //
                intent.putExtra("name", name)
                intent.putExtra("job", job)
                intent.putExtra("id", id)
                intent.putExtra("createdAt", createdAt)
                startActivity(intent)
                //
                Toast.makeText(this@MainActivity, "$name, $id, $createdAt", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
