package com.example.ictis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ictis.databinding.ActivityMainBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private val dataModel: DataModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        dataModel.message.observe(this,{})
        setContentView(binding.root)
        replaceFragmentB(InfoBlock())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.InfoBlocks->replaceFragmentB(InfoBlock())
                R.id.Profile->replaceFragmentP(Profile())
                R.id.Map->replaceFragmentM(Map())
                else->{
                }
            }
            true
        }
    }

   fun replaceFragmentB(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.BAZA,fragment)
        fragmentTransaction.commit()


    }
    fun replaceFragmentM(fragment: Fragment){
        val myIntent = Intent(this@MainActivity, MapActivity::class.java)
        myIntent.putExtra("key", android.R.attr.value) //Optional parameters
        this@MainActivity.startActivity(myIntent)
    }
    fun replaceFragmentP(fragment: Fragment){
        val myIntent = Intent(this@MainActivity, ProfileActivity::class.java)
        myIntent.putExtra("key", android.R.attr.value) //Optional parameters
        this@MainActivity.startActivity(myIntent)
    }

   /* fun Poisk(view:View){
        view.findViewById<RecyclerView>(R.id.rcView3).layoutManager = LinearLayoutManager()
        view.findViewById<RecyclerView>(R.id.rcView3).adapter=adapter2
        if (findViewById<RecyclerView>(R.id.rcView2).visibility == View.VISIBLE) {
            adapter2.deleter()
            findViewById<RecyclerView>(R.id.rcView2).visibility = View.GONE
        } else {
            findViewById<RecyclerView>(R.id.rcView2).visibility = View.VISIBLE
            Firebase.database.getReference("Article").child("CountS").get()
                .addOnSuccessListener {
                    var t = it.value.toString().toInt()
                    var h = 0
                    var str = findViewById<EditText>(R.id.poisk).text.toString()
                    while (h <= t) {
                        var p = h
                        Firebase.database.getReference("Article").child("Stati")
                            .child("s" + h.toString()).child("name").get()
                            .addOnSuccessListener {
                                if (it.value.toString().contains(str, ignoreCase = true)) {
                                    h = p
                                    //adapter2.articFoundCrate(ArticleFound(p,it.value.toString(),p))
                                }
                            }
                        h += 1
                    }
                }
        }
    }*/

    fun back(view: View){
        replaceFragmentB(InfoBlock())
    }

}