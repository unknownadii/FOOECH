package com.example.fooech.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.fooech.ApiWork.HomeSingleton
import com.example.fooech.DataModels.SerachDataModel
import com.example.fooech.ItemsViewModel
import com.example.fooech.R
import com.example.fooech.RecyclerView.HomeAdapter
import com.example.fooech.RecyclerView.SearchRecipeAdapter
import kotlinx.android.synthetic.main.fragment_home_fragment.*
import kotlinx.android.synthetic.main.fragment_search.*

class search_fragment : Fragment(R.layout.fragment_search) {

    lateinit var adapter: SearchRecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_searchRecipe.layoutManager = GridLayoutManager(requireContext(), 2)
        // This loop will create 20 Views containing
        // the image with the count of view
        adapter = SearchRecipeAdapter()
        searchClick.setOnClickListener {
            if (et_searchRecipe.text?.isEmpty() == true) {
                Toast.makeText(context, "Enter Something To Search", Toast.LENGTH_SHORT).show()
            } else {
                searchNothing.visibility = View.GONE
                getApi(et_searchRecipe.text.toString())
                et_searchRecipe.visibility = View.VISIBLE
            }
        }
        rv_searchRecipe.adapter = adapter
    }

    private fun getApi(name: String) {

        val url = "https://www.themealdb.com/api/json/v1/1/search.php?s=$name"
        // Request a string response from the provided URL.
        val getSearchRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                try {
                    var newsJsonArray = it.getJSONArray("meals")
                    val newsArray = ArrayList<SerachDataModel>()
                    //progress bar
                    for (i in 0 until newsJsonArray.length()) {
                        var indexStringResponse = newsJsonArray.getJSONObject(i)
                        var stringTitle = indexStringResponse.getString("strMeal")
                        var imageUrl = indexStringResponse.getString("strMealThumb")
                        newsArray.add(SerachDataModel(stringTitle, imageUrl))
                        //Toast.makeText(requireContext(), "Entered", Toast.LENGTH_LONG).show()
                    }
                    adapter.update(newsArray)


                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Not Avilable", Toast.LENGTH_SHORT).show()
                }
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
        HomeSingleton.getInstance(requireContext()).addToRequestQueue(getSearchRequest)

    }
}
