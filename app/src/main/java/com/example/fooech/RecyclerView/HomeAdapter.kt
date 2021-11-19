package com.example.fooech.RecyclerView

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooech.ItemsViewModel
import com.example.fooech.R


class HomeAdapter() :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private var items: ArrayList<ItemsViewModel> = ArrayList()
    lateinit var context: Context

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_home_rv, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = items[position]

        // sets the image to the imageview from our itemHolder class
        val imageUrl = ItemsViewModel.urlToImage
        Glide.with(holder.imageHome.context)
            .load(imageUrl)
            .into(holder.imageHome)

        // sets the text to the textview from our itemHolder class
        holder.headingHome.text = ItemsViewModel.title
        holder.DescriptionHome.text = ItemsViewModel.content
        val goToUrl = ItemsViewModel.url
        holder.viewDetailHome.setOnClickListener {
            getUrlFromIntent(it, goToUrl)
        }

    }

    fun update(updatedItems: ArrayList<ItemsViewModel>) {
        items.clear()
        items.addAll(updatedItems)

        notifyDataSetChanged()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageHome: ImageView = itemView.findViewById(R.id.imageHome)
        val headingHome: TextView = itemView.findViewById(R.id.headingHome)
        val DescriptionHome: TextView = itemView.findViewById(R.id.DescriptionHome)
        val viewDetailHome: Button = itemView.findViewById(R.id.viewDetailHome)
    }

    fun getUrlFromIntent(it: View, urltoOpen: String) {
        val url = urltoOpen
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(it.context, intent, null)
    }

}