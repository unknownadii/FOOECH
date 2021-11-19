package com.example.fooech.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fooech.PreStoreRecipe.RecipeActivity
import com.example.fooech.R
import kotlinx.android.synthetic.main.fragment_explore_recipe.*

class ExploreRecipe : Fragment(R.layout.fragment_explore_recipe) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cv_indian.setOnClickListener {
            val country="Indian"
            val intent=Intent(requireContext(),RecipeActivity::class.java)
            intent.putExtra("countryName",country)
            startActivity(intent)

        }
        cv_american.setOnClickListener {
            val country="American"
            val intent=Intent(requireContext(),RecipeActivity::class.java)
            intent.putExtra("countryName",country)
            startActivity(intent)
        }
        cv_british.setOnClickListener {
            val country="British"
            val intent=Intent(requireContext(),RecipeActivity::class.java)
            intent.putExtra("countryName",country)
            startActivity(intent)
        }
        cv_chinese.setOnClickListener {
            val country="Chinese"
            val intent=Intent(requireContext(),RecipeActivity::class.java)
            intent.putExtra("countryName",country)
            startActivity(intent)
        }
        cv_itlian.setOnClickListener {
            val country="Italian"
            val intent=Intent(requireContext(),RecipeActivity::class.java)
            intent.putExtra("countryName",country)
            startActivity(intent)
        }
        cv_mexican.setOnClickListener {
            val country="Mexican"
            val intent=Intent(requireContext(),RecipeActivity::class.java)
            intent.putExtra("countryName",country)
            startActivity(intent)
        }
        cv_canadian.setOnClickListener {
            val country="Canadian"
            val intent=Intent(requireContext(),RecipeActivity::class.java)
            intent.putExtra("countryName",country)
            startActivity(intent)
        }
    }

}