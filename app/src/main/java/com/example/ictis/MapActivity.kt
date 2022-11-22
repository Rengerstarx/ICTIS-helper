package com.example.ictis

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.ictis.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MapActivity:AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragmentM(Map())
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
    fun replaceFragmentM(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.BAZA,fragment)
        fragmentTransaction.commit()
    }
    fun replaceFragmentB(fragment: Fragment){
        val myIntent = Intent(this@MapActivity, MainActivity::class.java)
        myIntent.putExtra("key", android.R.attr.value) //Optional parameters
        this@MapActivity.startActivity(myIntent)
    }
    fun replaceFragmentP(fragment: Fragment){
        val myIntent = Intent(this@MapActivity, ProfileActivity::class.java)
        myIntent.putExtra("key", android.R.attr.value) //Optional parameters
        this@MapActivity.startActivity(myIntent)
    }

    fun Korpuses(view: android.view.View){
        if(view.id==findViewById<Button>(R.id.button).id){
            val myIntent = Intent(this@MapActivity, MapActivityD::class.java)
            myIntent.putExtra("key", android.R.attr.value) //Optional parameters
            this@MapActivity.startActivity(myIntent)
        }

    }


}