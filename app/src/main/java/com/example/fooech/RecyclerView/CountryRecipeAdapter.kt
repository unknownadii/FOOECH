package com.example.fooech.RecyclerView

import android.app.Dialog
import android.content.ClipData
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.session.MediaSession
import android.net.Uri
import android.os.Handler
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.example.fooech.ApiWork.HomeSingleton
import com.example.fooech.DataModels.CountryRecipeDataModel
import com.example.fooech.PreStoreRecipe.RecipeActivity
import com.example.fooech.R
import com.example.fooech.popData
import java.util.*
import kotlin.collections.ArrayList


class CountryRecipeAdapter : RecyclerView.Adapter<CountryRecipeAdapter.ViewHolder>() {
    private var countryRecipeitems: ArrayList<CountryRecipeDataModel> = ArrayList()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_recipe, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val countryItemsViewModel = countryRecipeitems[position]
        // sets the image to the imageview from our itemHolder class
        val countryImageUrl = countryItemsViewModel.countryMealImage
        Glide.with(holder.CountryimageSearch.context)
            .load(countryImageUrl)
            .into(holder.CountryimageSearch)
        // sets the text to the textview from our itemHolder class
        holder.CountryrecipeName.text = countryItemsViewModel.countryMealName
        var dishId = countryItemsViewModel.countryMealId
        //var viewtesting:View=holder.CountryrecipeCardView
        holder.CountryrecipeCardView.setOnClickListener {
            getIdApi(dishId, it)
            // Use
            val dialog = Dialog(it.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.loading_circle)
            dialog.setCancelable(false);
            dialog.show();
            val handler = Handler()
            handler.postDelayed({
                // do something after 1000ms
                showDialog(it)
                dialog.hide()
            }, 1000)

        }

    }

    fun update(updatedItems: ArrayList<CountryRecipeDataModel>) {
        countryRecipeitems.clear()
        countryRecipeitems.addAll(updatedItems)
        notifyDataSetChanged()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return countryRecipeitems.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val CountryimageSearch: ImageView = itemView.findViewById(R.id.iv_country)
        val CountryrecipeName: TextView = itemView.findViewById(R.id.tv_country)
        val CountryrecipeCardView: CardView = itemView.findViewById(R.id.cv_countryRecipe)
    }


    private fun showDialog(view: View) {
        val dialog = Dialog(view.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_popup)
        val backArrow = dialog.findViewById<ImageView>(R.id.backArrowPopup)

        val dishname = dialog.findViewById<TextView>(R.id.popupDishName)
        val dishimage = dialog.findViewById<ImageView>(R.id.popupDishImage)
        val dishdescription = dialog.findViewById<TextView>(R.id.popUpDishDescription)
        val youtubeDirect = dialog.findViewById<ImageView>(R.id.youtubeBrowser)

        dishname.text = popData.pupUpDishName
        dishdescription.text = popData.popUpDishDescription

//getting image of popup
        Glide.with(view.context)
            .load(popData.popUpDishImageUrl)
            .into(dishimage)

        youtubeDirect.setOnClickListener {
            Toast.makeText(view.context, "opening youtube", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(popData.popUpDishYoutube)
            ContextCompat.startActivity(it.context, intent, null)
        }
        backArrow.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show();
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.window!!.setGravity(Gravity.BOTTOM);
    }


    private fun getIdApi(dishId: String, view: View) {
        val url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$dishId"
        // Request a string response from the provided URL.
        val getSearchRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                var newsJsonArray = it.getJSONArray("meals")
                var indexStringResponse = newsJsonArray.getJSONObject(0)
                popData.pupUpDishName = indexStringResponse.getString("strMeal")
                popData.popUpDishImageUrl = indexStringResponse.getString("strMealThumb")
                popData.popUpDishDescription = indexStringResponse.getString("strInstructions")
                popData.popUpDishYoutube = indexStringResponse.getString("strYoutube")
            },
            Response.ErrorListener {
                Toast.makeText(view.context, "error occured", Toast.LENGTH_SHORT).show()
            }
        )
        //for dealing with exception E/Volley: [4436] NetworkUtility.shouldRetryException: Unexpected response code 403
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        HomeSingleton.getInstance(view.context).addToRequestQueue(getSearchRequest)
    }

}