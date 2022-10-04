package com.example.simplehttprequest

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.simplehttprequest.networks.RequestHandler
import com.example.simplehttprequest.networks.UserResponse
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val _job = SupervisorJob()
    private val _coroutinesScope = CoroutineScope(_job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMainSubmit: MaterialButton = findViewById(R.id.btn_main_submit)
        val etMainUrl: EditText = findViewById(R.id.et_main_url)
        val tvResult: TextView = findViewById(R.id.tv_main_result)

        btnMainSubmit.setOnClickListener {
            val urlData = etMainUrl.text.toString().trim()
            if (urlData.isEmpty()) {
                etMainUrl.error = "Form is required"
                return@setOnClickListener
            } else {
                _coroutinesScope.launch(Dispatchers.IO) {
                    val request = RequestHandler.requestGET(urlData)

                    withContext(Dispatchers.Main) {
                        tvResult.text = request

                        val gson = Gson()
                        val userList = gson.fromJson(request, UserResponse::class.java)
                        Log.d("Converted json", userList.toString())
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _job.cancel()
    }
}