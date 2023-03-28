package com.example.randomjokegenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var jokeType = ""
    var jokeSetup = ""
    var jokePunchline = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.jokeButton)
        val textView1 = findViewById<TextView>(R.id.textView)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        getNextImage(button, textView1, textView2, textView3)

    }

    private fun getJoke() {
        val client = AsyncHttpClient()
        client["https://official-joke-api.appspot.com/jokes/random", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Joke", "response successful$json")
                Log.d("JokeURL", "joke URL set")
                jokeType = json.jsonObject.getString("type")
                jokeSetup = json.jsonObject.getString("setup")
                jokePunchline = json.jsonObject.getString("punchline")
            }


            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Error", errorResponse)
            }
        }]

    }

    private fun getNextImage(button: Button, textView: TextView, textView2: TextView, textView3: TextView) {
        button.setOnClickListener {
            getJoke()
            textView.text = jokeType
            textView2.text = jokeSetup
            textView3.text = jokePunchline
        }
    }
}