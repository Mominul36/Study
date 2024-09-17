package com.example.studyadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.studyadmin.databinding.ActivityClassTestBinding
import com.example.studyadmin.databinding.ActivityLabReportBinding

class LabReportActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityLabReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLabReportBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.lr_nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}