package com.example.werewolfofthemillershollow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.util.Icons

/**
 * Adapter allowing the display of a list of roles in a recyclerView
 * @param context creation context
 * @param list list of roles
 */
class RoleAdapter(context : Context, list : ArrayList<Role>) : RecyclerView.Adapter<RoleAdapter.MyViewHolder>() {

    private var list : ArrayList<Role>
    private var context : Context
    private var listener : OnItemClick? = null

    /**
     * Handle click events on Items.
     * @see RoleAdapter
     */
    interface OnItemClick {

        fun onClick(position : Int)
        fun onHold(position: Int) : Boolean
    }

    /**
     * Return the current list of items displayed.
     */
    fun getList() : ArrayList<Role> = list

    /**
     * Setter for list of items displayed.
     * @param list new list of items
     */
    fun setList(list : ArrayList<Role>) {
        this.list = list
        notifyDataSetChanged()
    }

    init {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).
            inflate(R.layout.item_role,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val icon : ImageView = itemView.findViewById(R.id.item_icon)
        val text : TextView = itemView.findViewById(R.id.item_text)
        
        fun bind(role : Role){
            icon.setImageDrawable(Icons.getDrawableIcon(icon = role.icon, context = context))
            text.text = role.name

            itemView.setOnClickListener {
                if (listener != null) listener!!.onClick(adapterPosition)
            }

            itemView.setOnLongClickListener {
                if (listener != null) return@setOnLongClickListener listener!!.onHold(adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    /**
     * Override the adapter's listener with a new one.
     * @param listener new listener
     */
    fun setListener(listener : OnItemClick){
        this.listener = listener
    }

    /**
     * Add an element to the list displayed by the adapter
     * @param item item to insert
     * @param position insertion position
     */
    fun addItem(item : Role, position: Int){
        list.add(position,item)
        notifyItemInserted(position)
    }

    /**
     * Remove an element from the list displayed by the adapter
     * @param position position of the item to be removed
     */
    fun removeItem(position : Int){
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}