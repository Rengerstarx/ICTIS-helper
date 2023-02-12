package com.example.ictis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Zametki : Fragment(), ZametkiAdapter.Listener {

    private val dataModel:DataModel by activityViewModels()
    val adapter=ZametkiAdapter(this)
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_zametki, container, false)
        auth = Firebase.auth
        view.findViewById<RecyclerView>(R.id.rcView2).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.rcView2).adapter=adapter
        adapter.deleter()
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("CountZ").get().addOnSuccessListener {
            var t=it.value.toString().toInt()
            var h=1
            while(h<=t) {
                var p = h
                Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("s${p}").child("Название").get().addOnSuccessListener {
                    var g=it.value.toString()
                    Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("s${p}").child("Текст").get().addOnSuccessListener {
                        if(g!="_0%$#@@!_")
                            adapter.articCrate(ZamOne(p,g,it.value.toString()))
                    }
                }
                h+=1
            }
        }
        view.findViewById<ImageButton>(R.id.backBut2).setOnClickListener {
            dataModel.message.value="$"
            val fm = (getContext() as ProfileActivity).supportFragmentManager
            fm.beginTransaction().addToBackStack(null).replace(R.id.BAZA,ZametkiView()).commit()
        }
        return view
    }

    override fun onClick(zam: ZamOne) {
        adapter.deleter()
        dataModel.message.value=zam._id.toString()
        val fm = (getContext() as ProfileActivity).supportFragmentManager
        fm.beginTransaction().addToBackStack(null).replace(R.id.BAZA,ZametkiView()).commit()
    }

}