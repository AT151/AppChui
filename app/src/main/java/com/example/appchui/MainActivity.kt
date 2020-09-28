package com.example.appchui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.appchui.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import com.google.firebase.iid.FirebaseInstanceId


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val auth = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (auth != null) {
            createUI()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btn_logout.setOnClickListener{
            AuthUI.getInstance().signOut(this).addOnSuccessListener {
                startActivity(Intent(this, LoginActivity::class.java))
                Toast.makeText(this, "Successfully Signed Out", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (auth != null) {
            createUI()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    fun createUI(){
        auth?.let{
            txt_name.text = auth.displayName
            Glide.with(this)
                .load(auth.photoUrl)
                .fitCenter()
                .placeholder(R.drawable.profilepic)
                .into(userimage)
        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.action_buttons, menu)
        return true
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "descript"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("0", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.settings) {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)

    }
}
