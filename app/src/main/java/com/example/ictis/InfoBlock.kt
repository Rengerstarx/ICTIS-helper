package com.example.ictis

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class InfoBlock : Fragment(), BlockAdapter.Listener, ArticlePoiskAdapter.Listener {

    val adapter=BlockAdapter(this)
    val adapter2=ArticlePoiskAdapter(this)
    lateinit var outputTV: EditText
    lateinit var micIV: ImageButton
    private val REQUEST_CODE_SPEECH_INPUT = 1
    lateinit var rc1: RecyclerView
    lateinit var rc2: RecyclerView
    lateinit var constr: ConstraintLayout
    private lateinit var auth: FirebaseAuth
    private val dataModel:DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info_block, container, false)
        auth = Firebase.auth
        Firebase.database.getReference("Users").child(auth.currentUser?.uid.toString()).child("Имя").get().addOnSuccessListener {
            var m="Привет, "+it.value.toString()+"\n"+"Добро пожаловать в ICTIS helper"
            view.findViewById<TextView>(R.id.textView).text=m
        }
        view.findViewById<RecyclerView>(R.id.rcView).layoutManager = GridLayoutManager(context,2)
        view.findViewById<RecyclerView>(R.id.rcView).adapter=adapter
        view.findViewById<RecyclerView>(R.id.rcView3).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.rcView3).adapter=adapter2
        rc1=view.findViewById(R.id.rcView)
        rc2=view.findViewById(R.id.rcView3)
        constr=view.findViewById(R.id.hello)
        outputTV=view.findViewById(R.id.poisk)
        micIV=view.findViewById(R.id.idIVMic)
        Firebase.database.getReference("Article").child("CountS").get().addOnSuccessListener {
            var t=it.value.toString().toInt()
            var h=1
            while(h<=t) {
                Firebase.database.getReference("Article").child("Stati").child("s" + h.toString())
                    .child("blok").get().addOnSuccessListener {
                    if (it.value.toString().contains("стипен", ignoreCase = true)) {
                        adapter.articCrate(Blocks(h + 1, it.value.toString(), R.drawable.fellow))
                    } else if (it.value.toString().contains("общеж", ignoreCase = true)) {
                        adapter.articCrate(Blocks(h + 1, it.value.toString(), R.drawable.dominotry))
                    } else if (it.value.toString().contains("воен", ignoreCase = true)) {
                        adapter.articCrate(Blocks(h + 1, it.value.toString(), R.drawable.war))
                    } else if (it.value.toString().contains("поступ", ignoreCase = true)) {
                        adapter.articCrate(Blocks(h + 1, it.value.toString(), R.drawable.postoop))
                    } else if (it.value.toString().contains("сотруд", ignoreCase = true)) {
                        adapter.articCrate(Blocks(h + 1, it.value.toString(), R.drawable.prepod))
                    } else {
                        adapter.articCrate(Blocks(h + 1, it.value.toString(), R.drawable.spravki))
                    }
                }
                h += 1
            }
        }
        view.findViewById<ImageButton>(R.id.poiskButton).setOnClickListener {
            if (rc2.visibility == View.VISIBLE) {
                rc2.visibility = View.GONE
                rc1.visibility = View.VISIBLE
                constr.visibility = View.VISIBLE
            } else {
                rc2.visibility = View.VISIBLE
                rc1.visibility = View.GONE
                constr.visibility = View.GONE
                poisk228()
            }
        }
            micIV.setOnClickListener {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
                } catch (e: Exception) {
                }
            }
            return view

        }

    fun poisk228(){
        adapter2.deleter()
            Firebase.database.getReference("Article").child("CountS").get()
                .addOnSuccessListener {
                    var t = it.value.toString().toInt()
                    var h = 1
                    var str = outputTV.text.toString()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val res: ArrayList<String> = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                outputTV.setText(Objects.requireNonNull(res)[0])
                rc2.visibility = View.VISIBLE
                rc1.visibility = View.GONE
                constr.visibility = View.GONE
                poisk228()
            }
        }
    }


    override fun onClickR(articleFound: ArticleFound) {
        rc2.visibility = View.GONE
        rc1.visibility = View.VISIBLE
        constr.visibility = View.VISIBLE
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