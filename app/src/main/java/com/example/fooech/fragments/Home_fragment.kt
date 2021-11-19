package com.example.fooech.fragments

import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.fooech.ApiWork.HomeSingleton
import com.example.fooech.RecyclerView.HomeAdapter
import com.example.fooech.ItemsViewModel
import com.example.fooech.R
import kotlinx.android.synthetic.main.fragment_home_fragment.*
import kotlinx.android.synthetic.main.list_home_rv.*
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class home_fragment : Fragment(R.layout.fragment_home_fragment) {
    val apiKey = "2e53e98f75a34cd8af3f98f05f1f4bce"
   // val date = "2021-10-18"

    lateinit var adapter: HomeAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getting context

        // this creates a vertical layout Manager
        home_recyclerview.layoutManager = LinearLayoutManager(context)
        // This loop will create 20 Views containing
        // the image with the count of view

        // This will pass the ArrayList to our Adapter

        getApi()
        adapter = HomeAdapter()
        // Setting the Adapter with the recyclerview
        home_recyclerview.adapter = adapter

    }

          fun getApi() {

        // Instantiate the RequestQueue.
              val dateNow = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                  Calendar.getInstance().time
              } else {
                  TODO("VERSION.SDK_INT < N")
              }
              // val queue = Volley.newRequestQueue(requireContext())
        val url =
            "https://newsapi.org/v2/everything?qInTitle=%22recipe%22&from=" + dateNow + "&language=en&apiKey=" + apiKey
// Request a string response from the provided URL.
        val getRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                var newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<ItemsViewModel>()
                //progress bar
                for (i in 0 until newsJsonArray.length()) {
                    var indexStringResponse = newsJsonArray.getJSONObject(i)
                    var stringTitle = indexStringResponse.getString("title")
                    var imageUrl = indexStringResponse.getString("urlToImage")

                    var descriptionContent = indexStringResponse.getString("content")
                    var viewDetailUrl = indexStringResponse.getString("url")

                    newsArray.add(
                        ItemsViewModel(
                            imageUrl,
                            stringTitle,
                            descriptionContent,
                            viewDetailUrl
                        )
                    )
                    //Toast.makeText(requireContext(), "Entered", Toast.LENGTH_LONG).show()
                }
                adapter.update(newsArray)
            },
            Response.ErrorListener {
                Toast.makeText(requireContext(), "error occured", Toast.LENGTH_SHORT).show()
            }
        )
        //for dealing with exception E/Volley: [4436] NetworkUtility.shouldRetryException: Unexpected response code 403
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        HomeSingleton.getInstance(requireContext()).addToRequestQueue(getRequest)

    }


}
