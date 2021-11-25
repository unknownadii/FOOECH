package com.example.fooech

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.fooech.fragments.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView
import kotlinx.android.synthetic.main.activity_privacy_and_term.*
import kotlinx.android.synthetic.main.nav_header.*


class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //for the upper navigation bar
        setSupportActionBar(topAppBar)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout, R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawer_navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.navTerm -> {
                      val intent=  Intent(
                            this,PrivacyAndTermActivity::class.java)
                    intent.putExtra("name","term")
                    startActivity(intent)

                }
                R.id.navPrivacy -> {
                    val intent=  Intent(
                        this,PrivacyAndTermActivity::class.java)
                    intent.putExtra("name","privacy")
                    startActivity(intent)
                }
                R.id.navLogout -> {
                    Firebase.auth.signOut()
                    startActivity(Intent(this, RegisterActivity::class.java))
                    finish()
                    Toast.makeText(
                        this, "loging out", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            true
        }
        //middle recycler view
        val searchFragment = search_fragment()
        val homeFragment = home_fragment()
        val addFragment = AddRecipeFragment()
        val exploreFragment = ExploreRecipe()
        val profileFragment = Profile()
        createFragment(homeFragment)

        //for the bottom navigation bar
        bottomNavigationView.background = null
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_icon -> {
                    setAppBar()
                    createFragment(homeFragment)

                }
                R.id.search_icon -> {
                    hideAppBar()
                    createFragment(searchFragment)

                }

                R.id.explore_icon -> {
                    hideAppBar()
                    createFragment(exploreFragment)

                }

            }
            true
        }
    }

    //for upper menubar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun createFragment(item: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.rv_Fragment, item)
            commit()
        }
    }


    private fun hideAppBar() {
        supportActionBar?.hide()
    }

    private fun setAppBar() {
        supportActionBar?.show()
    }


}

