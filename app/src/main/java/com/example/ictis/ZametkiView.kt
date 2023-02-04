package com.example.ictis

import android.os.Bundle
import android.system.Os.remove
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.System.exit

class ZametkiView : Fragment() {

    private val dataModel:DataModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    var tx1: EditText?= null
    var tx2: EditText?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_zametki_view, container, false)
        tx1=view.findViewById(R.id.textBlock)
        tx2=view.findViewById(R.id.textView11)
        tx1?.isEnabled = false
        tx2?.isEnabled = false
        var l=""
        dataModel.message.observe(activity as LifecycleOwner) {
            l=it
        }
        auth = Firebase.auth
        if(l=="$"){
            tx1?.text= SpannableStringBuilder("")
            tx2?.text= SpannableStringBuilder("")
            tx1?.isEnabled = true
            tx2?.isEnabled = true
        }
        else{
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("s${l}").child("Название").get().addOnSuccessListener {
                tx1?.text= SpannableStringBuilder(it.value.toString())
            }
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("s${l}").child("Текст").get().addOnSuccessListener {
                tx2?.text= SpannableStringBuilder(it.value.toString())
            }
        }
        view.findViewById<ImageButton>(R.id.backBut2).setOnClickListener {
            if(tx1?.isEnabled == false){
                tx1?.isEnabled = true
                tx2?.isEnabled = true
            }else {
                tx1?.isEnabled = false
                tx2?.isEnabled = false
                if (l != "$") {
                    Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("s${l}").child("Название").setValue(tx1?.text.toString())
                    Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("s${l}").child("Текст").setValue(tx2?.text.toString())
                }else{
                    Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("CountZ2").get().addOnSuccessListener {
                        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("CountZ").setValue(it.value.toString().toInt()+1)
                        l=(it.value.toString().toInt()+1).toString()
                        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("s${l}").child("Название").setValue(tx1?.text.toString())
                        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("s${l}").child("Текст").setValue(tx2?.text.toString())
                    }
                    Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("CountZ").get().addOnSuccessListener {
                        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("CountZ2").setValue(it.value.toString().toInt()+1)
                    }
                    Log.e("321",l)
                }
            }
        }
        return view
    }

}