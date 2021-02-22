package com.example.werewolfofthemillershollow.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role

class VotingAdapter(
    private var list : ArrayList<Role>,
    private var onVote: OnVote
    ) : RecyclerView.Adapter<VotingAdapter.MyViewHolder>() {

    var votes = ArrayList<Int>()

    init {

        for (role : Role in list){
            votes.add(0)
        }

    }

    /**
     * Handle voting events.
     */
    interface OnVote{

        /**
         * Handle adding a vote to the player with the index equals to [position]
         * @param position position of the player.
         */
        fun onIncrement(adapter: VotingAdapter,position: Int)

        /**
         * Handle removing a vote to the player with the index equals to [position]
         * @param position position of the player.
         */
        fun onDecrement(adapter: VotingAdapter,position: Int)

    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val icon : ImageView = itemView.findViewById(R.id.item_icon)
        val player : TextView = itemView.findViewById(R.id.item_player)
        val add : ImageView = itemView.findViewById(R.id.item_add)
        val sub : ImageView = itemView.findViewById(R.id.item_sub)
        val vote : TextView = itemView.findViewById(R.id.item_vote)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vote, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val player = list[position]

        holder.icon.setImageResource(player.icon)
        holder.player.text = player.player
        holder.vote.text = "${votes[position]}"
        holder.add.setOnClickListener {
            onVote.onIncrement(this,holder.adapterPosition)
        }
        holder.sub.setOnClickListener {
            onVote.onDecrement(this,holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Add one vote to the player with the index equals to [position]
     * @param position position of the player.
     */
    fun addVote(position: Int){

        var currentVote = 0
        for (n : Int in votes){
            currentVote += n
        }

        if (currentVote >= votes.size)
            return

        votes[position] ++
        notifyItemChanged(position)

    }

    /**
     * Remove one votes to the player with the index equals to [position]
     * @param position position of the player.
     */
    fun decrementVote(position : Int){

        if (votes[position] <= 0)
            return

        votes[position] --
        notifyItemChanged(position)

    }

    /**
     * Reset all casted votes.
     */
    fun resetVotes(){

        votes.clear()

        for (role : Role in list){
            votes.add(0)
        }

        notifyDataSetChanged()

    }

    fun removePlayer(position: Int){

        list.removeAt(position)
        votes.removeAt(position)
        notifyItemRemoved(position)

    }
}