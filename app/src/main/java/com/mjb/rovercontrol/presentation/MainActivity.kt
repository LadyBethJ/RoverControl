package com.mjb.rovercontrol.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.mjb.rovercontrol.R
import com.mjb.rovercontrol.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.title = destination.label
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}