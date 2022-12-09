package com.example.ictis

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ictis.MainActivity
import com.example.ictis.R
import com.example.ictis.SignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        //---------переход между активностями-------
        val logintext: TextView = findViewById(R.id.loginrnowbutton)
        logintext.setOnClickListener {
            val intent =  Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        //-------------------------------------------
        val registerButton: TextView = findViewById(R.id.regButton)
        registerButton.setOnClickListener{
            performSignUp()
        }
        //---------ввод пароля и почты---------------
        performSignUp()
        //-------------------------------------------
    }

    private fun performSignUp() {
        val email = findViewById<EditText>(R.id.editTextTextPersonNameLogin)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        if(email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Заполните все поля ", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //успешный ввод, переход к main activity
                    Toast.makeText(baseContext, "Authentication Success.",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }

    }
}