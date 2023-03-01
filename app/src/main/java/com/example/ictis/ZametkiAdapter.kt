package com.example.ictis

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            cartochka.setOnTouchListener(object : OnTouchListener {
                var startTime: Long = 0
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> startTime = System.currentTimeMillis()
                        MotionEvent.ACTION_MOVE -> {}
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            val totalTime: Long = System.currentTimeMillis() - startTime
                            val totalSecunds = totalTime / 1000
                            if (totalSecunds >= 1) {
                                listener.onLong(zam)
                            }else{
                                listener.onClick(zam)
                            }
                        }
                    }
                    return true
                }
            })
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
        notifyDataSetChanged()
    }

    fun deler(a: Int){
        var t=0
        run lit@{
            ZametkiList.forEach {
                if (it._id == a) {
                    ZametkiList.removeAt(t)
                    notifyDataSetChanged()
                    return@lit
                }
                t++
            }
        }
    }

    interface Listener{
        fun onClick(zam: ZamOne)
        fun onLong(zam: ZamOne)
    }
}