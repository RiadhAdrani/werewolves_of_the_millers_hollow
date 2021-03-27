package com.example.werewolfofthemillershollow.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role

class DocCharactersAdapter(val list : ArrayList<Role>, val onClick : OnCharacterClick): RecyclerView.Adapter<DocCharactersAdapter.CharacterHolder>() {

    interface OnCharacterClick{
        fun onClick(position: Int)
    }

    inner class CharacterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.item_name)
        private val desc : TextView = itemView.findViewById(R.id.item_description)
        val icon : ImageView = itemView.findViewById(R.id.item_icon)

        fun bind(role: Role){
            name.text = role.name
            desc.text = role.description
            icon.setImageResource(role.icon)
            itemView.setOnClickListener {
                onClick.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DocCharactersAdapter.CharacterHolder {
        return CharacterHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_role_docs,parent,false))
    }

    override fun onBindViewHolder(holder: DocCharactersAdapter.CharacterHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}