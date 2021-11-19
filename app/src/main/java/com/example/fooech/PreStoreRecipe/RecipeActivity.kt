package com.example.fooech.PreStoreRecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.fooech.ApiWork.HomeSingleton
import com.example.fooech.DataModels.CountryRecipeDataModel
import com.example.fooech.R
import com.example.fooech.RecyclerView.CountryRecipeAdapter
import com.example.fooech.popData
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.country_recipe.*
import kotlinx.android.synthetic.main.fragment_search.*

class RecipeActivity : AppCompatActivity() {

    lateinit var countryadapter: CountryRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)


        setSupportActionBar(toolbar_Recipe_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //set back button
        val intent = intent
        val selectedCountry = intent.getStringExtra("countryName")
        supportActionBar?.title = "$selectedCountry Recipe"
        // Setting an title in the action bar.
        toolbar_Recipe_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        rv_countryRecipe.layoutManager = GridLayoutManager(this, 2)
        countryadapter = CountryRecipeAdapter()

        if (selectedCountry != null) {
            getCountryApi(selectedCountry)
        } else {
            Toast.makeText(this, "country is missing!!", Toast.LENGTH_SHORT).show()
        }
        rv_countryRecipe.adapter = countryadapter
    }

    private fun getCountryApi(cName: String) {
        val url = "https://www.themealdb.com/api/json/v1/1/filter.php?a=$cName"
        // Request a string response from the provided URL.
        val getSearchRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                var newsJsonArray = it.getJSONArray("meals")
                val CountrynewsArray = ArrayList<CountryRecipeDataModel>()
                //progress bar
                for (i in 0 until newsJsonArray.length()) {
                    var indexStringResponse = newsJsonArray.getJSONObject(i)
                    var stringTitle = indexStringResponse.getString("strMeal")
                    var dishId = indexStringResponse.getString("idMeal")
                    var imageUrl = indexStringResponse.getString("strMealThumb")
                    CountrynewsArray.add(CountryRecipeDataModel(stringTitle, imageUrl, dishId))

                }
                countryadapter.update(CountrynewsArray)
            },
            Response.ErrorListener {
                Toast.makeText(this, "error occured", Toast.LENGTH_SHORT).show()
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
        HomeSingleton.getInstance(this).addToRequestQueue(getSearchRequest)

    }
}
