package com.example.flixter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flixter.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_movies -> {
                switchFragment(MovieFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_persons -> {
                switchFragment(PersonFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        // Initially display the MovieFragment
        switchFragment(MovieFragment())
    }
}
