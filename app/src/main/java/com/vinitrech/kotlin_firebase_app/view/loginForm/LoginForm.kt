package com.vinitrech.kotlin_firebase_app.view.loginForm

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.vinitrech.kotlin_firebase_app.R
import com.vinitrech.kotlin_firebase_app.databinding.ActivityLoginFormBinding
import com.vinitrech.kotlin_firebase_app.view.mainScreen.MainScreen
import com.vinitrech.kotlin_firebase_app.view.signUpForm.SignUpForm

class LoginForm : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFormBinding
    private val auth = FirebaseAuth.getInstance()

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
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { auth ->
                    if (auth.isSuccessful) {
                        goToMainScreen()
                    }
                }.addOnFailureListener { failure ->
                    val errorMessage = when (failure) {
                        is FirebaseAuthInvalidCredentialsException -> "Invalid credentials!"
                        else -> "Internal error. Please try again later."
                    }

                    val snackbar = Snackbar.make(
                        view, errorMessage, Snackbar.LENGTH_SHORT
                    )
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }

        binding.dontHaveAccount.setOnClickListener {
            val intent = Intent(this, SignUpForm::class.java)
            startActivity(intent)
        }
    }

    private fun goToMainScreen() {
        val intent = Intent(this, MainScreen::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val userAuth = FirebaseAuth.getInstance().currentUser

        if(userAuth != null){
            goToMainScreen()
        }
    }
}