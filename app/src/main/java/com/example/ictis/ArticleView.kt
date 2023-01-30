package com.example.ictis

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class ArticleView : Fragment(), TextToSpeech.OnInitListener {

    private val dataModel:DataModel by activityViewModels()
    private var stat=""
    private var tts: TextToSpeech? = null
    private var locale: Locale = Locale("RU")
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tts = TextToSpeech(activity, this)
        auth = Firebase.auth
        val view = inflater.inflate(R.layout.fragment_article_view, container, false)
        var l=""
        dataModel.message.observe(activity as LifecycleOwner) {
            l=it
        }
        var K=l
        Firebase.database.getReference("Article").child("Stati").child("s"+l).child("name").get().addOnSuccessListener {
            view.findViewById<TextView>(R.id.TextStat).text=it.value.toString()
        }
        Firebase.database.getReference("Article").child("Stati").child("s"+l).child("CountBlock").get().addOnSuccessListener {
            var t=it.value.toString().toInt()
            var h=1
            while(h<=t){
                var p=h
                Firebase.database.getReference("Article").child("Stati").child("s"+l).child("Textes").child("tx"+h.toString()).get().addOnSuccessListener {
                    h=p
                    val resId = resources.getIdentifier(("textView"+h.toString()), "id", requireActivity().packageName)
                    view.findViewById<TextView>(resId).text=it.value.toString()
                    stat+=it.value.toString()
               }
                h+=1
            }
            while (h<=10){
                val resId = resources.getIdentifier(("textView"+h.toString()), "id", requireActivity().packageName)
                view.findViewById<TextView>(resId).visibility=View.GONE
                h+=1
            }
            Firebase.database.getReference("Article").child("Stati").child("s"+l).child("blok").get().addOnSuccessListener {
                dataModel.message.value=it.value.toString()
            }
        }
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Избраное").child("${K}").get().addOnSuccessListener {
            if(it.value.toString()=="1")
                view.findViewById<ImageButton>(R.id.like)!!.setImageResource(R.drawable.fullhear)
        }
        view.findViewById<ImageButton>(R.id.VoiceButton)!!.setOnClickListener { speakOut() }
        view.findViewById<ImageButton>(R.id.MuteButton)!!.setOnClickListener { speakOff() }
        view.findViewById<ImageButton>(R.id.like)!!.setOnClickListener {
            var pic = view.findViewById<ImageButton>(R.id.like)
            Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Избраное").child("${K}").get().addOnSuccessListener {
                if(it.value.toString()=="1") {
                    pic.setImageResource(R.drawable.heart)
                    Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Избраное").child("${K}").setValue(0)
                }else{
                    pic.setImageResource(R.drawable.fullhear)
                    Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Избраное").child("${K}").setValue(1)
                }
            }
        }
        return view
    }

    override fun onInit(status: Int) {
        tts!!.language = locale
    }

    private fun speakOut() {
        tts!!.speak(stat, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun speakOff(){
        tts!!.speak("",TextToSpeech.QUEUE_FLUSH, null, "")
    }

}