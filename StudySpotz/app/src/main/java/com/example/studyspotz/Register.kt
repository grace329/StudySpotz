package com.example.studyspotz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

// YZABELLE NEEDS TO ADD COMMENTS
class Register : AppCompatActivity() {
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonReg: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun onRegisterClick() {
        progressBar.visibility = View.VISIBLE
        // Get email and password from the TextInputEditTexts
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        // Validate the email input
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
            return
        }
        // Validate the input
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            return
        }

        // Create user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this@Register, "Registration successful!", Toast.LENGTH_SHORT).show()
                    // Navigate to another activity or update the UI as needed
                } else {
                    // If sign in fails, display a message to the user
                    Toast.makeText(this@Register, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

        // Handle the button click here
        Toast.makeText(this, "Register button clicked", Toast.LENGTH_SHORT).show()
        // Add additional registration logic here
    }

    private fun onLoginClick() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.btn_register)
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.loginNow)

        textView.setOnClickListener {
            onLoginClick()
        }

        buttonReg.setOnClickListener {
           onRegisterClick()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}