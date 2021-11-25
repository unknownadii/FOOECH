package com.example.fooech

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var dialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth=Firebase.auth

        btn_login.setOnClickListener {
            showDialog()
            logInUser()

        }
        btn_register.setOnClickListener {
            showDialog()
            registerUser()

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

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Toast.makeText(this, "Welcome Back ", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun registerUser() {
        val email = et_register_email.text.toString().trim { it <= ' ' }
        val name = et_register_username.text.toString().trim { it <= ' ' }
        val password = et_register_password.text.toString()
        if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        hideDialog()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                        Toast.makeText(this, "Register Succesfully", Toast.LENGTH_SHORT).show()
                    } else {
                        hideDialog()
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this, "Registration failed." + task.exception,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }

    }

    private fun logInUser() {
        val email = et_login_email.text.toString().trim { it <= ' ' }
        val password = et_login_password.text.toString()
        if (!email.isEmpty() && !password.isEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        hideDialog()
                        Toast.makeText(this, "logging in", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        hideDialog()
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "User Does not Exits", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }



    private fun showDialog()
    {
        dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCancelable(false)
        dialog.show()
    }
    private fun hideDialog()
    {
        dialog.dismiss()
    }
}