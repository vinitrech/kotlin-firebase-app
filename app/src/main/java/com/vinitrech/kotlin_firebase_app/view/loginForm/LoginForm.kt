package com.vinitrech.kotlin_firebase_app.view.loginForm

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.vinitrech.kotlin_firebase_app.R
import com.vinitrech.kotlin_firebase_app.databinding.ActivityLoginFormBinding
import com.vinitrech.kotlin_firebase_app.view.signUpForm.SignUpForm

class LoginForm : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener { view ->
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                val snackbar = Snackbar.make(
                    view, "Fill all the fields to login!", Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{

            }
        }

        binding.dontHaveAccount.setOnClickListener {
            val intent = Intent(this, SignUpForm::class.java)
            startActivity(intent)
        }
    }
}