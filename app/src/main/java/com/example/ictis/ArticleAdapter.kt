package com.example.ictis

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ictis.databinding.BlockBinding
import com.example.ictis.databinding.FragmentArticleBinding
import com.example.ictis.databinding.StatyaBinding
import java.io.Console

class ArticleAdapter(val listener: Listener): RecyclerView.Adapter<ArticleAdapter.ArticleHolder>() {
    val ArticleList=ArrayList<Article>()

    class ArticleHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = StatyaBinding.bind(item)
        fun bind(artic: Article, listener: Listener) = with(binding){
            StatyaName.text=artic.title
            StatyaName.setOnClickListener{
                listener.onClick(artic)
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
    }

    interface Listener{
        fun onClick(artic: Article)
    }
}