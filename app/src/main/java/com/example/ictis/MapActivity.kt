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

    private var laya: ConstraintLayout? = null
    private var flay: ConstraintLayout? = null
    private var flayer: ConstraintLayout? = null
    private var image: ImageView? = null
    private var Scrol: ScrollView?=null
    private var baton: Button?=null
    private var zerobut: Button?=null
    private var l=0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        binding.bottomNavigationView.menu.findItem(R.id.Map).setEnabled(true)
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
            supportFragmentManager.beginTransaction().replace(R.id.BAZA,KorpusDZero()).addToBackStack(null).commit()
        }

    }

    fun showHide(view: android.view.View) {
        laya=findViewById<ConstraintLayout>(R.id.lay)
        flay=findViewById<ConstraintLayout>(R.id.floa)
        flay?.visibility = View.INVISIBLE
        laya?.visibility = if (laya?.visibility == View.VISIBLE){
            View.INVISIBLE
        } else{
            View.VISIBLE
        }
    }

    fun D_1_(view: android.view.View) {
        laya=findViewById<ConstraintLayout>(R.id.lay)
        val text = findViewById<TextView>(view.getId()).getText().toString()
        baton = findViewById(view.getId())
        if(l!=0){
            image=findViewById(l)
            image?.visibility =View.INVISIBLE
        }
        val resId = resources.getIdentifier(text, "id", packageName)
        image = findViewById(resId)
        if (laya?.visibility == View.VISIBLE ){
            laya?.visibility =View.INVISIBLE
        } else{
            laya?.visibility =View.VISIBLE
        }
        if (image?.visibility != View.VISIBLE){
            if(l==image?.getId()){
                l=0
            }
            else{
                image?.visibility =View.VISIBLE
                l=image!!.getId()
            }
        } else{
            image?.visibility =View.INVISIBLE
        }
    }

    fun ShowFolders(view: android.view.View) {
        laya=findViewById<ConstraintLayout>(R.id.lay)
        flay=findViewById<ConstraintLayout>(R.id.floa)
        laya?.visibility = View.INVISIBLE
        if (flay?.visibility == View.VISIBLE ){
            flay?.visibility = View.INVISIBLE
        } else{
            flay?.visibility = View.VISIBLE
        }
    }

    fun Floars(view: android.view.View){
        flay=findViewById<ConstraintLayout>(R.id.floa)
        zerobut=findViewById<Button>(R.id.ButZero)
        zerobut!!.setEnabled(false)
        flay?.visibility = View.INVISIBLE
        /*if(findViewById<TextView>(view.getId()).getText().toString()=="1"){
            val myIntent = Intent(this@MainActivity, KorpusDFloarFirst::class.java)
            myIntent.putExtra("key", android.R.attr.value) //Optional parameters
            this@MainActivity.startActivity(myIntent)
        }*/
    }

    fun MaxSize(view: android.view.View){
        laya=findViewById<ConstraintLayout>(R.id.lay)
        flay=findViewById<ConstraintLayout>(R.id.floa)
        flayer=findViewById<ConstraintLayout>(R.id.MaxView)
        val k: Button?=findViewById(R.id.Cabinets)
        val kk: Button?=findViewById(R.id.Floars)
        if (flayer?.visibility == View.VISIBLE){
            k!!.setEnabled(true)
            kk!!.setEnabled(true)
            flayer?.visibility = View.INVISIBLE
        } else{
            k!!.setEnabled(false)
            kk!!.setEnabled(false)
            flayer?.visibility = View.VISIBLE
            laya?.visibility = View.INVISIBLE
            flay?.visibility = View.INVISIBLE
        }
    }
}