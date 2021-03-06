package com.example.werewolfofthemillershollow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.util.Event

class EventAdapter(var context: Context, var list : ArrayList<Event>): RecyclerView.Adapter<EventAdapter.MyViewHolder>() {

    init {
        list.sortBy { it.importance }
        list.reverse()
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val icon : ImageView = itemView.findViewById(R.id.item_icon)
        val text : TextView = itemView.findViewById(R.id.item_text)

        fun bind(event : Event){
            icon.setImageResource(event.getIcon())
            text.text = event.getMessage()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}