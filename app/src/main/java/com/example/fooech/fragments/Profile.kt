package com.example.fooech.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fooech.ItemsViewModel
import com.example.fooech.R
import com.example.fooech.RecyclerView.UploadAdapter
import kotlinx.android.synthetic.main.fragment_profile.*

class Profile : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getting context
        val context = activity

        // this creates a vertical layout Manager
        rv_uploads.layoutManager = GridLayoutManager(context,2)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel( R.drawable.foodimage.toString(),"Item " + i,"anbc","wert"))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = UploadAdapter(data)

        // Setting the Adapter with the recyclerview
        rv_uploads.adapter = adapter

    }
}