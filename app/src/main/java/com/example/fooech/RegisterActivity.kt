package com.example.fooech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btn_login.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
        btn_register.setOnClickListener {
            cv_register.visibility = View.GONE
            ll_register.visibility = View.GONE
            cv_login.visibility = View.VISIBLE
            ll_login.visibility = View.VISIBLE
            Toast.makeText(this, "Register Sucessfully", Toast.LENGTH_SHORT).show()
        }
        tv_register.setOnClickListener {
            cv_login.visibility = View.GONE
            ll_login.visibility = View.GONE
            cv_register.visibility = View.VISIBLE
            ll_register.visibility = View.VISIBLE

        }
        tv_login.setOnClickListener {
            cv_register.visibility = View.GONE
            ll_register.visibility = View.GONE
            cv_login.visibility = View.VISIBLE
            ll_login.visibility = View.VISIBLE
        }
    }
}