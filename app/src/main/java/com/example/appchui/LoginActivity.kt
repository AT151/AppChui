package com.example.appchui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        createLoginUI()
    }

    fun createLoginUI(){
        val providers = arrayListOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            var response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                Log.d("USERDATA", user?.displayName)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                if(response == null){
                    finish()
                }
                if (response?.getError()?.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    //Show No Internet Notification
                    return
                }
                if(response?.getError()?.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, response?.error?.errorCode.toString(), Toast.LENGTH_LONG)
                        .show()
                    Log.d("ERRORCODE", response?.error?.errorCode.toString())
                    return
                }
            }
        }
    }
}