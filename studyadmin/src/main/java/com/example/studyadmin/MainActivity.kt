package com.example.studyadmin

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studyadmin.Internet.InternetCheck
import com.example.studyadmin.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)









        binding.ct.setOnClickListener{
            if(InternetCheck().isInternetAvailable(applicationContext)){
                startActivity(Intent(this,ClassTestActivity::class.java))
            }else{
                Toast.makeText(applicationContext,"Internet not available",Toast.LENGTH_LONG).show()
            }

        }


        binding.assignment.setOnClickListener{
            if(InternetCheck().isInternetAvailable(applicationContext)){
                startActivity(Intent(this,AssignmentActivity::class.java))
            }else{
                Toast.makeText(applicationContext,"Internet not available",Toast.LENGTH_LONG).show()
            }

        }


        binding.lr.setOnClickListener{
            if(InternetCheck().isInternetAvailable(applicationContext)){
                startActivity(Intent(this,LabReportActivity::class.java))
            }else{
                Toast.makeText(applicationContext,"Internet not available",Toast.LENGTH_LONG).show()
            }

        }





    }




}





//Baler Code
