package com.example.simplehttprequest

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.simplehttprequest.networks.RequestHandler
import com.google.android.material.button.MaterialButton
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
                    }
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _job.cancel()
    }
}