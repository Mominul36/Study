package com.example.studyadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.studyadmin.databinding.ActivityClassTestBinding

class ClassTestActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityClassTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityClassTestBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.ct_nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}