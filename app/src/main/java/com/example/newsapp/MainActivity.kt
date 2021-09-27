package com.example.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), OnNewsClick {

    private lateinit var mAdaptor: NewsAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    //to set recycleview as linear layout
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mAdaptor = NewsAdaptor(this)
        recyclerView.adapter=mAdaptor
    }



    //we made api calls
    fun fetchNews(){
        //use volley to call api
        val queue = Volley.newRequestQueue(this)
        val url = "https://newsapi.org/v2/everything?q=tesla&from=2021-08-24&sortBy=publishedAt&apiKey=85d4b730bf414d71bdc0e3331df763a2" //api url
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            {
              val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news= News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                    )
                    newsArray.add(news)
                    //update time every day done
                }
                mAdaptor.updateData(newsArray)
            },
            {

            }
        )
        queue.add(jsonObjectRequest)
    }

    override fun onClicked(news: News) {

            // we will need to access chrome custom tabs implementation add...
            val builder= CustomTabsIntent.Builder()
            val customTabsIntent= builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(news.url))

    }


}