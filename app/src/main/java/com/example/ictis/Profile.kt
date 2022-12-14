package com.example.ictis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Profile : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        auth = Firebase.auth
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Имя").get().addOnSuccessListener {
            view.findViewById<TextView>(R.id.textView14).text=it.value.toString()
        }
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Фамиля").get().addOnSuccessListener {
            view.findViewById<TextView>(R.id.textView13).text=it.value.toString()
        }
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Отчество").get().addOnSuccessListener {
            view.findViewById<TextView>(R.id.textView15).text=it.value.toString()
        }
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Группа").get().addOnSuccessListener {
            view.findViewById<TextView>(R.id.textView12).text="Группа ${it.value.toString()}"
        }
        return view
    }

}