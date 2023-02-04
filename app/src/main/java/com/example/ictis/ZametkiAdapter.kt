package com.example.ictis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ictis.databinding.BlockBinding
import com.example.ictis.databinding.ZametkaBinding

class ZametkiAdapter(val listener: ZametkiAdapter.Listener): RecyclerView.Adapter<ZametkiAdapter.ZametkiHolder>() {

    val ZametkiList=ArrayList<ZamOne>()

    class ZametkiHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ZametkaBinding.bind(item)
        fun bind(zam: ZamOne, listener: Listener) = with(binding){
            StatyaName.text=zam.title
            zamtxt.text=zam.txt
            cartochka.setOnClickListener{
                listener.onClick(zam)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZametkiHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.zametka,parent,false)
        return  ZametkiHolder(view)
    }

    override fun onBindViewHolder(holder: ZametkiHolder, position: Int) {
        holder.bind(ZametkiList[position], listener )
    }

    override fun getItemCount(): Int {
        return ZametkiList.size
    }

    fun articCrate(zam: ZamOne){
        ZametkiList.add(zam)
        notifyDataSetChanged()
    }

    fun deleter(){
        var t=ZametkiList.size
        var h=0
        while(h<t){
            ZametkiList.removeAt(0)
            h+=1
        }
    }

    interface Listener{
        fun onClick(zam: ZamOne)
    }
}