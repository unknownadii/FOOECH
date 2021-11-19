package com.example.fooech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.fooech.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
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
                R.id.navEditProfile -> Toast.makeText(
                    this, "edit selected", Toast.LENGTH_SHORT
                ).show()
                R.id.navTerm -> Toast.makeText(
                    this, "term and condtion selected", Toast.LENGTH_SHORT
                ).show()
                R.id.navPrivacy -> Toast.makeText(
                    this, "privacy selected", Toast.LENGTH_SHORT
                ).show()
                R.id.navLogout -> Toast.makeText(
                    this, "logout selected", Toast.LENGTH_SHORT
                ).show()
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
                R.id.add_button -> {
                    hideAppBar()
                    createFragment(addFragment)

                }
                R.id.explore_icon -> {
                    hideAppBar()
                    createFragment(exploreFragment)

                }
                R.id.profile_icon -> {
                    hideAppBar()
                    createFragment(profileFragment)

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

