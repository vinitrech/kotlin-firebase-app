package com.vinitrech.kotlin_firebase_app.view.signUpForm

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.vinitrech.kotlin_firebase_app.R
import com.vinitrech.kotlin_firebase_app.databinding.ActivitySignUpFormBinding

class SignUpForm : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpFormBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener { view ->
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                val snackbar = Snackbar.make(view, "Fill all the fields!", Snackbar.LENGTH_SHORT)

                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { signup ->
                        if (signup.isSuccessful) {
                            val snackbar = Snackbar.make(
                                view, "User registered successfully!", Snackbar.LENGTH_SHORT
                            )
                            snackbar.setBackgroundTint(Color.BLUE)
                            snackbar.show()

                            binding.editEmail.setText("")
                            binding.editPassword.setText("")
                        }
                    }.addOnFailureListener { failure ->
                        val errorMessage = when (failure) {
                            is FirebaseAuthWeakPasswordException -> "The password must be at least 6 characters long!"
                            is FirebaseAuthInvalidCredentialsException -> "Invalid email!"
                            is FirebaseAuthUserCollisionException -> "User already exists!"
                            is FirebaseNetworkException -> "No internet connection!"
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
    }
}