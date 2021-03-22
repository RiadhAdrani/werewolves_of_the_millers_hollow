package com.example.werewolfofthemillershollow.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import kotlin.math.abs

class VotingAdapter(
    var list : ArrayList<Role>,
    var voters : Int,
    private var execution : Boolean = false,
    ) : RecyclerView.Adapter<VotingAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val icon : ImageView = itemView.findViewById(R.id.item_icon)
        val player : TextView = itemView.findViewById(R.id.item_player)
        val add : ImageView = itemView.findViewById(R.id.item_add)
        val sub : ImageView = itemView.findViewById(R.id.item_sub)
        private val vote : TextView = itemView.findViewById(R.id.item_vote)

        fun bind(role: Role){

            icon.setImageResource(role.icon)
            player.text = role.player
            vote.text = "${role.vote}"

            add.setOnClickListener {
                addVote(adapterPosition)
            }

            sub.setOnClickListener {
                decrementVote(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vote, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Add one vote to the player with the index equals to [position]
     * @param position position of the player.
     */
    private fun addVote(position: Int){

        var currentVote = 0
        for (r : Role in list){
            currentVote += abs(r.vote)
        }

        if (currentVote >= voters && list[position].vote >= 0)
            return

        list[position].vote ++
        notifyItemChanged(position)

    }

    /**
     * Remove one votes to the player with the index equals to [position]
     * @param position position of the player.
     */
    private fun decrementVote(position : Int){

        if (list[position].vote <= 0 && !execution)
            return

        var currentVote = 0
        for (r : Role in list){
            currentVote += abs(r.vote)
        }

        if (execution && currentVote >= voters && list[position].vote <= 0)
            return

        if (list[position].vote == 0 && list.size > 1)
            return

        list[position].vote --
        notifyItemChanged(position)

    }

    /**
     * Reset all casted votes.
     */
    fun resetVotes(){

        for (r : Role in list){
            r.resetVotes()
        }

        notifyDataSetChanged()

    }

    private fun removePlayer(position : Int){
        list.removeAt(position)
        notifyDataSetChanged()
    }

    fun removeDead(){

        var index = 0

        while (index > list.size){
            if (list[index].isKilled){
                removePlayer(index)
            }
            else {
                index ++
            }
        }

    }
}