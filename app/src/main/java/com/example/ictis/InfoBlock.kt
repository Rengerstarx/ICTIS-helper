package com.example.ictis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class InfoBlock : Fragment(), BlockAdapter.Listener {

    val adapter=BlockAdapter(this)
    private val dataModel:DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_info_block, container, false)
        view.findViewById<RecyclerView>(R.id.rcView).layoutManager = GridLayoutManager(context,2)
        view.findViewById<RecyclerView>(R.id.rcView).adapter=adapter

        Firebase.database.getReference("Article").child("CountS").get().addOnSuccessListener {
            var t=it.value.toString().toInt()
            var h=1
            while(h<=t){
                Firebase.database.getReference("Article").child("Stati").child("s"+h.toString()).child("blok").get().addOnSuccessListener {
                    adapter.articCrate(Blocks(h+1,it.value.toString()))
                }
                h+=1
            }
        }
        return view

    }

    override fun onClick(blok: Blocks) {
        dataModel.message.value=blok.title
        val fm = (getContext() as MainActivity).supportFragmentManager
        fm.beginTransaction().addToBackStack(null).replace(R.id.BAZA,ArticleList()).commit()
    }


}