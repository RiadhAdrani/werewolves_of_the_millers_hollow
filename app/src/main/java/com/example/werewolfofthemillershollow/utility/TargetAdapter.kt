package com.example.werewolfofthemillershollow.utility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role

class TargetAdapter(
    private var list : ArrayList<Role>,
    private var context : Context,
    private var handler : OnClickListener? = null)
    : RecyclerView.Adapter<TargetAdapter.MyViewHolder>() {

    interface OnClickListener{

        fun onClick(position: Int)
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val icon : ImageView = itemView.findViewById(R.id.item_icon)
        val player : TextView = itemView.findViewById(R.id.item_player)
        val role : TextView = itemView.findViewById(R.id.item_role)
        val use : ImageView = itemView.findViewById(R.id.item_use)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_target, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentTarget = list[position]
        holder.player.text = currentTarget.getPlayer()
        holder.role.text = currentTarget.getName()
        holder.use.setOnClickListener {
            handler?.onClick(holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}