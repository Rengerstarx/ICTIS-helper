package com.example.ictis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class InfoBlock : Fragment(), BlockAdapter.Listener, ArticlePoiskAdapter.Listener {

    val adapter=BlockAdapter(this)
    val adapter2=ArticlePoiskAdapter(this)
    private val dataModel:DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info_block, container, false)
        view.findViewById<RecyclerView>(R.id.rcView).layoutManager = GridLayoutManager(context,2)
        view.findViewById<RecyclerView>(R.id.rcView).adapter=adapter
        view.findViewById<RecyclerView>(R.id.rcView3).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.rcView3).adapter=adapter2
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
        view.findViewById<ImageButton>(R.id.poiskButton).setOnClickListener{
            if (view.findViewById<RecyclerView>(R.id.rcView3).visibility == View.VISIBLE) {
                adapter2.deleter()
                view.findViewById<RecyclerView>(R.id.rcView3).visibility = View.GONE
                view.findViewById<RecyclerView>(R.id.rcView).visibility = View.VISIBLE
                view.findViewById<ConstraintLayout>(R.id.hello).visibility = View.VISIBLE
            } else {
                view.findViewById<RecyclerView>(R.id.rcView3).visibility = View.VISIBLE
                view.findViewById<RecyclerView>(R.id.rcView).visibility = View.GONE
                view.findViewById<ConstraintLayout>(R.id.hello).visibility = View.GONE
                Firebase.database.getReference("Article").child("CountS").get()
                    .addOnSuccessListener {
                        var t = it.value.toString().toInt()
                        var h = 0
                        var str = view.findViewById<EditText>(R.id.poisk).text.toString()
                        while (h <= t) {
                            var p = h
                            Firebase.database.getReference("Article").child("Stati")
                                .child("s" + h.toString()).child("name").get()
                                .addOnSuccessListener {
                                    if (it.value.toString().contains(str, ignoreCase = true)) {
                                        h = p
                                        adapter2.articFoundCrate(
                                            ArticleFound(p, it.value.toString(), p))
                                    }
                                }
                            h += 1
                        }
                    }
            }
        }
        return view

    }


    override fun onClickR(articleFound: ArticleFound) {
        dataModel.message.value=articleFound.number.toString()
        val fm = (getContext() as MainActivity).supportFragmentManager
        fm.beginTransaction().addToBackStack(null).replace(R.id.BAZA,ArticleView()).commit()
    }

    override fun onClick(blok: Blocks) {
        dataModel.message.value=blok.title
        val fm = (getContext() as MainActivity).supportFragmentManager
        fm.beginTransaction().addToBackStack(null).replace(R.id.BAZA,ArticleList()).commit()
    }


}