package com.example.ictis

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Izbranoy : Fragment(), IzbranoyAdapter.Listener{

    private val dataModel:DataModel by activityViewModels()
    val adapter=IzbranoyAdapter(this)
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_izbranoy, container, false)
        auth = Firebase.auth
        view.findViewById<RecyclerView>(R.id.rcView2).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.rcView2).adapter=adapter
        Firebase.database.getReference("Article").child("CountS").get().addOnSuccessListener {
            var t=it.value.toString().toInt()
            Log.e("sender", t.toString())
            var h=1
            while(h<=t){
                var p=h
                Log.e("sender", p.toString())
                Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Избраное").child("${p}").get().addOnSuccessListener {
                    if(it.value.toString()=="1"){
                        Firebase.database.getReference("Article").child("Stati").child("s"+p.toString()).child("name").get().addOnSuccessListener {
                            adapter.articCrate(Article(p,it.value.toString(),p))
                        }
                    }
                }
                h+=1
            }
        }
        return view
    }

    override fun onClick(artic: Article) {
        adapter.deleter()
        dataModel.message.value=artic.number.toString()
        val fm = (getContext() as ProfileActivity).supportFragmentManager
        fm.beginTransaction().addToBackStack(null).replace(R.id.BAZA,ArticleView()).commit()
    }

    override fun onSvayp(artic: Article) {
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString())
            .child("Избраное").child("${artic.number}").setValue(0)
        adapter.deler(artic._id)

    }
}