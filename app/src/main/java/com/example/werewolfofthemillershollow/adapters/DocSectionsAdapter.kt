package com.example.werewolfofthemillershollow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.util.DocSection

class DocSectionsAdapter(
    private val list : ArrayList<DocSection>,
    var context: Context,
    val onSectionClick: OnSectionClick) : RecyclerView.Adapter<DocSectionsAdapter.SectionHolder>() {

    interface OnSectionClick{
        fun onClick(position: Int)
    }

    inner class SectionHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.item_name)
        val description : TextView = itemView.findViewById(R.id.item_description)
        val icon : ImageView = itemView.findViewById(R.id.item_icon)

        fun bind(section : DocSection){

            name.text = context.getString(section.name)
            description.text = context.getString(section.description)
            icon.setImageResource(section.icon)

            itemView.setOnClickListener {
                onSectionClick.onClick(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionHolder {
        return SectionHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_docs_section, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SectionHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}