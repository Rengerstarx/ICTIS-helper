package com.example.ictis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import android.app.FragmentManager
import com.example.ictis.databinding.ActivityMainBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragmentP(Profile())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.InfoBlocks->replaceFragmentB(InfoBlock())
                R.id.Profile->replaceFragmentP(Profile())
                R.id.Map->replaceFragmentM(Map())
                else->{}
            }
            true
        }
        binding.imageView12.visibility=View.INVISIBLE
        binding.imageView13.visibility=View.INVISIBLE
        binding.imageView14.visibility=View.VISIBLE
    }

    fun replaceFragmentM(fragment: Fragment){
        val myIntent = Intent(this@ProfileActivity, MapActivity::class.java)
        myIntent.putExtra("key", android.R.attr.value) //Optional parameters
        this@ProfileActivity.startActivity(myIntent)
    }
    fun replaceFragmentB(fragment: Fragment){
        val myIntent = Intent(this@ProfileActivity, MainActivity::class.java)
        myIntent.putExtra("key", android.R.attr.value) //Optional parameters
        this@ProfileActivity.startActivity(myIntent)
    }
    fun replaceFragmentP(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.BAZA,fragment)
        fragmentTransaction.commit()
    }
}