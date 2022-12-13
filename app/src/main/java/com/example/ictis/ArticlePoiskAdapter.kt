package com.example.ictis

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ictis.databinding.ArticlefoundBinding
import com.example.ictis.databinding.BlockBinding

class ArticlePoiskAdapter(val listener: Listener): RecyclerView.Adapter<ArticlePoiskAdapter.ArticlePoiskAdapterHolder>() {

    val ArticlePoiskList=ArrayList<ArticleFound>()

    class ArticlePoiskAdapterHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ArticlefoundBinding.bind(item)
        fun bind(articleFound: ArticleFound, listener: Listener) = with(binding){
            NameOfArtic.text=articleFound.title
            NameOfArtic.setOnClickListener{
                listener.onClickR(articleFound)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlePoiskAdapterHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.articlefound,parent,false)
        return  ArticlePoiskAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlePoiskAdapterHolder, position: Int) {
        holder.bind(ArticlePoiskList[position], listener )
    }

    override fun getItemCount(): Int {
        return ArticlePoiskList.size
    }

    fun articFoundCrate(articleFound: ArticleFound){
        ArticlePoiskList.add(ArticleFound(ArticlePoiskList.size,articleFound.title,articleFound.number))
        notifyDataSetChanged()
    }

    fun deleter(){
        var t=ArticlePoiskList.size
        var h=0
        while(h<t){
            ArticlePoiskList.removeAt(0)
            h+=1
        }
    }

    interface Listener{
        fun onClickR(articleFound: ArticleFound)
    }
}