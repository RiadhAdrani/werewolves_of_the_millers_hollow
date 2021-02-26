package com.example.werewolfofthemillershollow.utility

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App

class VotingDialog(
    private var gameActivity:GameActivity,
    private var list : ArrayList<Role>,
    private var voters : Int,
    private var title : String,
    private var text : String,
    private var onBarberCall : GameActivity.OnCall?,
    private var onVoteCast: OnVoteCast,
    private var execution : Boolean = false
    ) : AppCompatDialogFragment() {

    interface OnVoteCast{
        fun onVoteCast(dialog : VotingDialog)
    }

    lateinit var adapter : VotingAdapter

    private lateinit var dialog : View

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = android.app.AlertDialog.Builder(context)

        dialog = activity?.layoutInflater!!.inflate(R.layout.dialog_vote,null)

        builder.setView(dialog)

        val header : TextView = dialog.findViewById(R.id.dialog_header)
        header.text = title

        val description : TextView = dialog.findViewById(R.id.dialog_text)
        description.text = text

        val barber : ImageView = dialog.findViewById(R.id.dialog_barber)
        if (gameActivity.barberRef != null){
            if (!gameActivity.barberRef!!.isKilled && gameActivity.barberRef!!.primaryAbility!!.times != App.ABILITY_NONE){
                App.blink(barber, Color.YELLOW, 1000)
                barber.setOnClickListener {
                    if (onBarberCall != null){
                        // onBarberCall?.onCall(this)
                    }
                }
            } else {
                barber.visibility = View.GONE
            }
        } else {
            barber.visibility = View.GONE
        }

        val rv : RecyclerView = dialog.findViewById(R.id.dialog_content_rv)
        adapter = VotingAdapter(list = list, voters = voters, execution = execution)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter

        val next : TextView = dialog.findViewById(R.id.dialog_next)
        next.setOnClickListener {
            onVoteCast.onVoteCast(this)
        }

        val reset : TextView = dialog.findViewById(R.id.dialog_reset)
        reset.setOnClickListener {
            adapter.resetVotes()
        }

        isCancelable = false

        return builder.create()
    }
}