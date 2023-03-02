package com.example.ictis

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class ProfileRedach : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_redach, container, false)
        auth = Firebase.auth
        val tx1=view.findViewById<EditText>(R.id.editTextSurname)
        val tx2=view.findViewById<EditText>(R.id.editTextName)
        val tx3=view.findViewById<EditText>(R.id.editTextOtchestvo)
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Имя").get().addOnSuccessListener {
            view.findViewById<EditText>(R.id.editTextSurname).text= SpannableStringBuilder(it.value.toString())
        }
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Фамиля").get().addOnSuccessListener {
            view.findViewById<EditText>(R.id.editTextName).text=SpannableStringBuilder(it.value.toString())
        }
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Отчество").get().addOnSuccessListener {
            view.findViewById<EditText>(R.id.editTextOtchestvo).text=SpannableStringBuilder(it.value.toString())
        }
        view.findViewById<TextView>(R.id.textView16).setOnClickListener {
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Имя").setValue(tx1.text.toString())
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Фамиля").setValue(tx2.text.toString())
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Отчество").setValue(tx3.text.toString())
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Курс").setValue(view.findViewById<Spinner>(R.id.spinner2).selectedItem.toString())
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Группа").setValue(view.findViewById<Spinner>(R.id.spinner).selectedItem.toString())
        }
        return view
    }

}