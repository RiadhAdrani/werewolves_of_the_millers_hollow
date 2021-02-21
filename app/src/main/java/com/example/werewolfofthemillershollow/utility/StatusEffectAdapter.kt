package com.example.werewolfofthemillershollow.utility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R

class StatusEffectAdapter(private var list: ArrayList<StatusEffect>,
                          private var context: Context,
                          listener : OnClickListener? = null)
    : RecyclerView.Adapter<StatusEffectAdapter.MyViewHolder>()
{

    private var listener : OnClickListener? = null

    init {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_status_effect, parent, false)
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = list[position]
        holder.icon.setImageResource(currentItem.icon!!)
        holder.text.text = currentItem.getName(context = context)

        if (listener != null){
            holder.itemView.setOnClickListener {
                listener!!.onClick(holder.adapterPosition)
            }
            holder.icon.setOnLongClickListener {
                return@setOnLongClickListener listener!!.onHold(holder.adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val icon : ImageView = itemView.findViewById(R.id.item_icon)
        val text : TextView = itemView.findViewById(R.id.item_text)
    }

    /**
     * Interface for methods handling on click events for the adapter.
     */
    interface OnClickListener{

        /**
         * OnClick handler
         */
        fun onClick(index : Int)

        /**
         * OnLongClick handler
         */
        fun onHold(index : Int): Boolean = true
    }

    /**
     * Update the list of status effects with a new one.
     */
    fun setList(list : ArrayList<StatusEffect>){
        this.list = list
        notifyDataSetChanged()
    }

}