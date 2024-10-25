package com.example.studyspotz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity2 : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var textView: TextView
    private var user: FirebaseUser? = null


    private fun onClick() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        Log.d("MainActivity", "onCreate called")

        auth = FirebaseAuth.getInstance()
        button = findViewById(R.id.logout)
        textView=findViewById(R.id.user_details)
        user = auth.currentUser


        if (user == null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        } else {
            textView.text = user!!.email
        }

        button.setOnClickListener {
           onClick()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}