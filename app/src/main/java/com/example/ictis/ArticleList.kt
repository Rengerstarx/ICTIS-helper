package com.example.ictis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ArticleList : Fragment(), ArticleAdapter.Listener {

    private val dataModel:DataModel by activityViewModels()
    val adapter=ArticleAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var l=""
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        dataModel.message.observe(activity as LifecycleOwner) {
            l=it
        }
        view.findViewById<TextView>(R.id.textBlock).text=l
        view.findViewById<RecyclerView>(R.id.rcView2).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.rcView2).adapter=adapter
        Firebase.database.getReference("Article").child("CountS").get().addOnSuccessListener {
            var t=it.value.toString().toInt()
            var h=1
            while(h<=t){
                var p=h
                Firebase.database.getReference("Article").child("Stati").child("s"+h.toString()).child("blok").get().addOnSuccessListener {
                    if(l==it.value.toString()){
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
        val fm = (getContext() as MainActivity).supportFragmentManager
        fm.beginTransaction().addToBackStack(null).replace(R.id.BAZA,ArticleView()).commit()
    }

}