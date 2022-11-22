package com.example.ictis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.ictis.databinding.ActivityMainBinding


class MapActivityD: AppCompatActivity() {
    private var laya: ConstraintLayout? = null
    private var flay: ConstraintLayout? = null
    private var flayer: ConstraintLayout? = null
    private var butka: Button? = null
    private var ArrayZero= arrayOf("Д-001","Д-002","Д-003","Д-004","Д-005","Д-006","Д-007","Д-008","Д-010","Д-011","Д-012","Д-013","Д-013/1","Д-013/2","Д-013/3","Д-013/4","Д-013/5","Д-013/6","Д-013/7","Д-014","Д-015","Д-016","Д-017","Д-017/1","Д-017/2","Д-018","Д-019","Д-020","Д-020/1")
    private var ArrayFirts= arrayOf("Д-101","Д-102","Д-103","Д-103/1","Д-104","Д-105","Д-106","Д-107","Д-108","Д-109","Д-110","Д-111","Д-112","Д-113","Д-113/1","Д-113/2","Д-117","Д-118","Д-119","Д-119/1","Д-125")
    private var ArraySecond= arrayOf("Д-201","Д-202","Д-203","Д-204","Д-204/1","Д-204/2","Д-205","Д-206","Д-207","Д-208","Д-208/1","Д-209","Д-210","Д-211","Д-211/1","Д-212","Д-212/1","Д-212/2","Д-212/3","Д-213","Д-214","Д-214/2","Д-215","Д-216","Д-216/1","Д-218","Д-219","Д-220","Д-221","Д-222","Д-223","Д-224","Д-225","Д-226","Д-226/1","Д-226/2")
    private var baton: Button?=null
    private var zerobut: Button?=null
    private var l=0
    private var lKab=0
    private var lKabMax=41
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.BAZA, KorpusDZero()).addToBackStack(null).commit()
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
        val myIntent = Intent(this@MapActivityD, MapActivity::class.java)
        myIntent.putExtra("key", android.R.attr.value) //Optional parameters
        this@MapActivityD.startActivity(myIntent)
    }
    fun replaceFragmentB(fragment: Fragment){
        val myIntent = Intent(this@MapActivityD, MainActivity::class.java)
        myIntent.putExtra("key", android.R.attr.value) //Optional parameters
        this@MapActivityD.startActivity(myIntent)
    }
    fun replaceFragmentP(fragment: Fragment){
        val myIntent = Intent(this@MapActivityD, ProfileActivity::class.java)
        myIntent.putExtra("key", android.R.attr.value) //Optional parameters
        this@MapActivityD.startActivity(myIntent)
    }

    override fun onBackPressed() {
        val myIntent = Intent(this@MapActivityD, MapActivity::class.java)
        myIntent.putExtra("key", android.R.attr.value) //Optional parameters
        this@MapActivityD.startActivity(myIntent)
    }

    fun NewFloar (view: android.view.View){
        laya=findViewById<ConstraintLayout>(R.id.lay)
        flay=findViewById<ConstraintLayout>(R.id.floa)
        if(view.id==findViewById<Button>(R.id.ButZero).id){
            l=0
            findViewById<ImageView>(R.id.D000).setImageResource(R.drawable.zerodkab)
            enable(view)
            zerobut=findViewById<Button>(R.id.ButZero)
            zerobut!!.setEnabled(false)
            lKab=29
        } else if(view.id==findViewById<Button>(R.id.ButFirst).id){
            l=1
            findViewById<ImageView>(R.id.D000).setImageResource(R.drawable.firstdkab)
            enable(view)
            zerobut=findViewById<Button>(R.id.ButFirst)
            zerobut!!.setEnabled(false)
            lKab=21
        }
        else if(view.id==findViewById<Button>(R.id.ButSecond).id){
            l=2
            findViewById<ImageView>(R.id.D000).setImageResource(R.drawable.seconddkab)
            enable(view)
            zerobut=findViewById<Button>(R.id.ButSecond)
            zerobut!!.setEnabled(false)
            lKab=36
        }
        flay?.visibility = View.INVISIBLE
        laya?.visibility = View.INVISIBLE
        flayer?.visibility = View.INVISIBLE
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
        if(l==0){
            lKab=29
        }
        KabinetsGeneration(view)
    }

    fun ShowFolders(view: android.view.View) {
        laya=findViewById<ConstraintLayout>(R.id.lay)
        flay=findViewById<ConstraintLayout>(R.id.floa)
        if(l==0){
            zerobut=findViewById<Button>(R.id.ButZero)
            zerobut!!.setEnabled(false)
        }
        laya?.visibility = View.INVISIBLE
        if (flay?.visibility == View.VISIBLE ){
            flay?.visibility = View.INVISIBLE
        } else{
            flay?.visibility = View.VISIBLE
        }
    }

    fun enable (view: android.view.View){
        zerobut=findViewById<Button>(R.id.ButZero)
        zerobut!!.setEnabled(true)
        zerobut=findViewById<Button>(R.id.ButFirst)
        zerobut!!.setEnabled(true)
        zerobut=findViewById<Button>(R.id.ButSecond)
        zerobut!!.setEnabled(true)
        zerobut=findViewById<Button>(R.id.ButThird)
        zerobut!!.setEnabled(true)
        zerobut=findViewById<Button>(R.id.ButFourth)
        zerobut!!.setEnabled(true)
        zerobut=findViewById<Button>(R.id.ButFifth)
        zerobut!!.setEnabled(true)
    }

    fun KabinetsGeneration(view: android.view.View){
        if(l==0){
            var k=1
            var str="D_"
            while(k<=lKab){
                val resId = resources.getIdentifier((str+k.toString()), "id", packageName)
                butka = findViewById(resId)
                butka?.visibility=View.VISIBLE
                k+=1
            }
            while(k<=lKabMax){
                val resId = resources.getIdentifier((str+k.toString()), "id", packageName)
                butka = findViewById(resId)
                butka?.visibility=View.INVISIBLE
                k+=1
            }
            k=0
            while (k<lKab){
                val resId = resources.getIdentifier((str+(k+1).toString()), "id", packageName)
                butka = findViewById(resId)
                butka?.text = ArrayZero[k].toString()
                k+=1
            }
        }
        if(l==1){
            var k=1
            var str="D_"
            while(k<=lKab){
                val resId = resources.getIdentifier((str+k.toString()), "id", packageName)
                butka = findViewById(resId)
                butka?.visibility=View.VISIBLE
                k+=1
            }
            while(k<=lKabMax){
                val resId = resources.getIdentifier((str+k.toString()), "id", packageName)
                butka = findViewById(resId)
                butka?.visibility=View.INVISIBLE
                k+=1
            }
            k=0
            while (k<lKab){
                val resId = resources.getIdentifier((str+(k+1).toString()), "id", packageName)
                butka = findViewById(resId)
                butka?.text = ArrayFirts[k].toString()
                k+=1
            }
        }
        if(l==2){
            var k=1
            var str="D_"
            while(k<=lKab){
                val resId = resources.getIdentifier((str+k.toString()), "id", packageName)
                butka = findViewById(resId)
                butka?.visibility=View.VISIBLE
                k+=1
            }
            while(k<=lKabMax){
                val resId = resources.getIdentifier((str+k.toString()), "id", packageName)
                butka = findViewById(resId)
                butka?.visibility=View.INVISIBLE
                k+=1
            }
            k=0
            while (k<lKab){
                val resId = resources.getIdentifier((str+(k+1).toString()), "id", packageName)
                butka = findViewById(resId)
                butka?.text = ArraySecond[k].toString()
                k+=1
            }
        }
    }

}