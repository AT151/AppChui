package com.example.appchui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.appchui.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val auth = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (auth == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            txt_name.text = auth.displayName
        }
    }
}
