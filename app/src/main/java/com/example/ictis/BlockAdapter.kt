package com.example.ictis

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.ictis.databinding.BlockBinding

class BlockAdapter(val listener: Listener): RecyclerView.Adapter<BlockAdapter.BlockHolder>() {

    val BlockList=ArrayList<Blocks>()

    class BlockHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = BlockBinding.bind(item)
        fun bind(block: Blocks, listener: Listener) = with(binding){
            BlockName.text=block.title
            imageView31.setImageResource(block.imageId)
            carder.setOnClickListener{
                listener.onClick(block)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.block,parent,false)
        return  BlockHolder(view)
    }

    override fun onBindViewHolder(holder: BlockHolder, position: Int) {
        holder.bind(BlockList[position], listener )
    }

    override fun getItemCount(): Int {
        return BlockList.size
    }

    fun articCrate(blok: Blocks){
        var marker=true
        var l=0
        while (l<BlockList.size) {
            if (blok.title == BlockList[l].title) {
                marker = false
                break
            }
            l += 1
        }
        if(marker)
            BlockList.add(Blocks(BlockList.size,blok.title, blok.imageId))
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(blok: Blocks)
    }
}