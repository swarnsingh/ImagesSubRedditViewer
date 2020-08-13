package com.swarn.redditviewer.ui.activity

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.swarn.redditviewer.R
import com.swarn.redditviewer.ui.fragment.RedditFragment

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Reddit"

        setSupportActionBar(toolbar)

        navigateFragment(RedditFragment(), true)
    }

    fun navigateFragment(fragment: Fragment, flag: Boolean) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (flag) {
            fragmentTransaction.add(
                R.id.frame_container,
                fragment,
                fragment::class.java.canonicalName
            )
        } else {
            fragmentTransaction.replace(
                R.id.frame_container,
                fragment,
                fragment::class.java.canonicalName
            )
            fragmentTransaction.addToBackStack(fragment::class.java.canonicalName)
        }

        fragmentTransaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun setBackIcon() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    fun setHome() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }
}