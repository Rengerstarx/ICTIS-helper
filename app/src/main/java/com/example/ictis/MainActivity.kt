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


open class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding=ActivityMainBinding.inflate(layoutInflater)
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

    fun InfoOpen(view: android.view.View){
        if(view.id==findViewById<Button>(R.id.War).id){
            supportFragmentManager.beginTransaction().replace(R.id.BAZA, War()).addToBackStack(null).commit()
        }
        else if(view.id==findViewById<Button>(R.id.Spravki).id){
            supportFragmentManager.beginTransaction().replace(R.id.BAZA, Spravki()).addToBackStack(null).commit()
        }
        else if(view.id==findViewById<Button>(R.id.FellowShip).id){
            supportFragmentManager.beginTransaction().replace(R.id.BAZA, Fellowship()).addToBackStack(null).commit()
        }
        else if(view.id==findViewById<Button>(R.id.Dominotry).id){
            supportFragmentManager.beginTransaction().replace(R.id.BAZA, Dominotry()).addToBackStack(null).commit()
        }
    }

    fun DinamicInfoBloks(view: android.view.View){
        supportFragmentManager.beginTransaction().replace(R.id.BAZA, Article()).addToBackStack(null).commit()

    }
}