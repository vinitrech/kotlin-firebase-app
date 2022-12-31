package com.vinitrech.kotlin_firebase_app.view.mainScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vinitrech.kotlin_firebase_app.R
import com.vinitrech.kotlin_firebase_app.databinding.ActivityMainScreenBinding
import com.vinitrech.kotlin_firebase_app.view.loginForm.LoginForm

class MainScreen : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val goBackToLoginScreen = Intent(this, LoginForm::class.java)
            startActivity(goBackToLoginScreen)
            finish()
        }

        binding.saveDataButton.setOnClickListener {
            val usersMap = hashMapOf(
                "name" to "Vinicius",
                "surname" to "Tonello",
                "username" to "vinitrech@gmail.com",
                "password" to 123456,
                "age" to 24
            )
            db.collection("Users").document("Vinicius").set(usersMap).addOnCompleteListener {
                Log.d("db", "Data saved successfully!")
            }.addOnFailureListener {
                Log.d("db", "Error saving data!")
            }
        }

        binding.updateDataButton.setOnClickListener {
            db.collection("Users").document("Vinicius").update("name", "Vinicius 2", "surname", "Tonello 2")
                .addOnCompleteListener {
                    Log.d("db_update", "Data saved successfully!")
                }.addOnFailureListener {
                Log.d("db_update", "Error saving data!")
            }
        }

        binding.readDataButton.setOnClickListener {
            db.collection("Users").document("Vinicius").addSnapshotListener { document, error ->
                if (document != null) {
                    binding.readName.text = document.getString("name")
                    binding.readSurname.text = document.getString("surname")
                    binding.readAge.text = document.getLong("age").toString()
                }
            }
        }

        binding.deleteDataButton.setOnClickListener {
            db.collection("Users").document("Vinicius").delete()
        }
    }
}