package com.example.ictis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
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
    var numer=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_zametki, container, false)
        auth = Firebase.auth
        view.findViewById<RecyclerView>(R.id.rcView2).layoutManager = GridLayoutManager(context,2)
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
                        var z=it.value.toString()
                        if(g!="_0%$#@@!_") {
                            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("s${p}").child("Активность").get().addOnSuccessListener {
                                if(it.value.toString().toInt()==1)
                                    adapter.articCrate(ZamOne(p, g, z))
                            }
                        }
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
        view.findViewById<Button>(R.id.button6).setOnClickListener {
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Заметки").child("s${numer}").child("Активность").setValue(0)
            adapter.deler(numer)
            view.findViewById<CardView>(R.id.reshenie).visibility=View.GONE
            view.findViewById<ImageView>(R.id.imageView22).visibility=View.GONE
            view?.findViewById<RecyclerView>(R.id.rcView2)?.isEnabled=true
        }
        view.findViewById<Button>(R.id.button7).setOnClickListener {
            view.findViewById<CardView>(R.id.reshenie).visibility=View.GONE
            view.findViewById<ImageView>(R.id.imageView22).visibility=View.GONE
            view?.findViewById<RecyclerView>(R.id.rcView2)?.isEnabled=true
        }
        return view
    }

    override fun onClick(zam: ZamOne) {
        adapter.deleter()
        dataModel.message.value=zam._id.toString()
        val fm = (getContext() as ProfileActivity).supportFragmentManager
        fm.beginTransaction().addToBackStack(null).replace(R.id.BAZA,ZametkiView()).commit()
    }

    override fun onLong(zam: ZamOne) {
        numer=zam._id
        view?.findViewById<CardView>(R.id.reshenie)?.visibility=View.VISIBLE
        view?.findViewById<ImageView>(R.id.imageView22)?.visibility=View.VISIBLE
        view?.findViewById<RecyclerView>(R.id.rcView2)?.isEnabled=false
    }

}