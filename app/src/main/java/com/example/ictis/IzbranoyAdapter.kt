package com.example.ictis

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ictis.databinding.StatyaBinding
import kotlin.math.abs

class IzbranoyAdapter(val listener: Listener): RecyclerView.Adapter<IzbranoyAdapter.ArticleHolder>() {
    val ArticleList=ArrayList<Article>()

    class ArticleHolder(item: View): RecyclerView.ViewHolder(item) {
        private val swipeThreshold = 100
        val binding = StatyaBinding.bind(item)
        fun bind(artic: Article, listener: Listener) = with(binding){
            StatyaName.text=artic.title
            var startX=0f
            var startY=0f
            cartoteka.setOnTouchListener{ _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                        startY = event.y
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        val distanceX = event.x - startX
                        val distanceY = event.y - startY
                        if (abs(distanceX) > abs(distanceY) && abs(distanceX) > swipeThreshold) {
                            listener.onSvayp(artic)
                        }else{
                            listener.onClick(artic)
                        }
                        false
                    }
                    else -> false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.statya,parent,false)
        return  ArticleHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bind(ArticleList[position], listener )
    }

    override fun getItemCount(): Int {
        return ArticleList.size
    }

    fun articCrate(artic: Article){
        ArticleList.add(Article(ArticleList.size,artic.title,artic.number))
        notifyDataSetChanged()
    }

    fun deleter(){
        var t=ArticleList.size
        var h=0
        while(h<t){
            ArticleList.removeAt(0)
            h+=1
        }
        notifyDataSetChanged()
    }

    fun deler(a: Int){
        var t=0
        run lit@{
            ArticleList.forEach {
                if (it._id == a) {
                    ArticleList.removeAt(t)
                    notifyDataSetChanged()
                    return@lit
                }
                t++
            }
        }
    }


    interface Listener{
        fun onClick(artic: Article)
        fun onSvayp(artic: Article)
    }
}