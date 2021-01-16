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
import com.example.werewolfofthemillershollow.settings.Icons

/**
 * Adapter allowing the display of a list of roles in a recyclerView
 * @param context creation context
 * @param list list of roles
 */
class RoleAdapter(context : Context, list : ArrayList<Role>) : RecyclerView.Adapter<RoleAdapter.MyViewHolder>() {

    private var list : ArrayList<Role>? = null
    private var context : Context? = null
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
    fun getList() : ArrayList<Role> = list!!

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
        val item : Role = list!![position]

        holder.icon.setImageDrawable(Icons.getDrawableIcon(icon = item.getIcon()!!, context = context!!))
        holder.text.text = item.getName()

        holder.itemView.setOnClickListener {
            if (listener != null) listener!!.onClick(holder.adapterPosition)
        }

        holder.itemView.setOnLongClickListener{
            if (listener != null) return@setOnLongClickListener listener!!.onHold(holder.adapterPosition)
            return@setOnLongClickListener true

        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val icon : ImageView = itemView.findViewById(R.id.item_icon)
        val text : TextView = itemView.findViewById(R.id.item_text)
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
        list?.add(position,item)
        notifyItemInserted(position)
    }

    /**
     * Remove an element from the list displayed by the adapter
     * @param position position of the item to be removed
     */
    fun removeItem(position : Int){
        list?.removeAt(position)
        notifyItemRemoved(position)
    }
}