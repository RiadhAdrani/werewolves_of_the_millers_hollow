package com.example.werewolfofthemillershollow.utility

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.Icons

class TargetAdapter(
    private var list : ArrayList<Role>,
    private var dialog: UsePowerDialog,
    private var handler : OnClickListener? = null)
    : RecyclerView.Adapter<TargetAdapter.MyViewHolder>() {

    fun getList(): ArrayList<Role> = list

    private var targets : ArrayList<Int> = ArrayList()

    fun getTargets() : ArrayList<Int> = targets

    fun emptyTargets(){
        targets.clear()
        notifyDataSetChanged()
    }

    fun addTarget(element : Int){
        Log.d("TargetAdapter","Inner Class : added element $element")
        targets.add(element)
        notifyItemChanged(element)
    }

    fun removeTarget(element : Int){
        if (targets.remove(element)){
            Log.d("TargetAdapter","Inner Class : removed element $element")
            notifyItemChanged(element)
            return
        }
        Log.d("TargetAdapter","Inner Class : element $element does not exist")
    }

    interface OnClickListener{

        fun onClick(position: Int, dialog: UsePowerDialog, adapter : TargetAdapter)
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

        holder.player.text = currentTarget.player
        holder.role.text = currentTarget.name
        holder.icon.setImageResource(currentTarget.icon)

        if (position !in targets){
            holder.use.setImageResource(Icons.done)
        }
        else {
            holder.use.setImageResource(Icons.goal)
        }

        holder.use.setOnClickListener {
            handler?.onClick(
                holder.adapterPosition,
                dialog,
                this
            )
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}