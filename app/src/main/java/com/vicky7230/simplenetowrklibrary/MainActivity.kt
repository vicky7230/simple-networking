package com.vicky7230.simplenetowrklibrary

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.vicky7230.simple_networking.core.Http
import com.vicky7230.simple_networking.listener.JSONObjectListener
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Http.Request(Http.GET)
            .url("https://jsonplaceholder.typicode.com/posts/1")
            .makeRequest(object : JSONObjectListener {
                override fun onResponse(res: JSONObject?) {
                    Log.e(TAG, res.toString())
                }

                override fun onFailure(e: Exception?) {
                    e?.localizedMessage?.let {
                        Log.e(TAG, it)
                    }
                }
            })
    }
}