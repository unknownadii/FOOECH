package com.example.fooech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_privacy_and_term.*




class PrivacyAndTermActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_and_term)

       val titleName= intent.getStringExtra("name")
        if (titleName=="term")
        {
            tv_privacy.visibility = View.GONE
            tv_term.visibility = View.VISIBLE
            val mystring = resources.getString(R.string.Term)
            tv_mixed.text= mystring

        }
        else if(titleName=="privacy")
        {
            tv_privacy.visibility = View.VISIBLE
            tv_term.visibility = View.GONE
            val mystring = resources.getString(R.string.Privacy)
            tv_mixed.text= mystring
        }


        backArrowProfile.setOnClickListener {
            onBackPressed()
        }
    }
}