package com.example.study

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.study.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {
    lateinit var database : DatabaseReference
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar);


        binding.schedule.setOnClickListener{
            showScheduleDialog()
        }



//        database = FirebaseDatabase.getInstance().getReference("Hello")
//
//        var id =  database.push().key!!
//
//        var model = Model(id,"Mominul Islam","24")
//
//        database.child(id).setValue(model)
//            .addOnSuccessListener {
//                Toast.makeText(applicationContext,"Saved",Toast.LENGTH_LONG).show()
//            }





    }


//    fun showLocalNotification(title: String, message:String) {
//        val channelId = "local_channel"
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, "Local Notifications", NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.notifications)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setAutoCancel(true)
//            .setVibrate(longArrayOf(1000,1000,1000,1000))
//            .setShowWhen(true)
//
//
//        notificationManager.notify(1, notificationBuilder.build())
//    }



    private fun showScheduleDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_schedule, null)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)


        val dialog = dialogBuilder.create()
        dialog.show()

        val btnOptionOne = dialogView.findViewById<Button>(R.id.ct)
        val btnOptionTwo = dialogView.findViewById<Button>(R.id.assignment)

        btnOptionOne.setOnClickListener {
//            val intent = Intent(this, ActivityOne::class.java)
//            startActivity(intent)
            dialog.dismiss()
        }

        btnOptionTwo.setOnClickListener {
//            val intent = Intent(this, ActivityTwo::class.java)
//            startActivity(intent)
            dialog.dismiss()
        }
    }








}
