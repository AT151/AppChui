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
    }
}
