package com.example.ictis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SecondRegister : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_register)
        auth = Firebase.auth
    }

    fun Continue(view: View){
        var s1=findViewById<EditText>(R.id.edLogin).text.toString()
        var s2=findViewById<EditText>(R.id.edPas).text.toString()
        var s3=findViewById<EditText>(R.id.edFathername).text.toString()
        if(s1!="" && s2!="" && s3!=""){
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Имя").setValue(s1)
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Фамиля").setValue(s2)
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Отчество").setValue(s3)
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Курс").setValue(findViewById<Spinner>(R.id.spinner2).selectedItem.toString())
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Группа").setValue(findViewById<Spinner>(R.id.spinner).selectedItem.toString())
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            val toast = Toast.makeText(applicationContext, "Не все поля заполненны", Toast.LENGTH_SHORT)
            toast.show()
        }

    }
}