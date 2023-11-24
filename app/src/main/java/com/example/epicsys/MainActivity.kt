package com.example.epicsys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.epicsys.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}