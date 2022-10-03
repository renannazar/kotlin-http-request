package com.example.simplehttprequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.simplehttprequest.networks.RequestHandler
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

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
                CoroutineScope(Dispatchers.IO).launch {
                    val request = RequestHandler.requestGET(urlData)
                    CoroutineScope(Dispatchers.Main).launch {
                        tvResult.text = request
                    }
                }
            }
        }
    }
}