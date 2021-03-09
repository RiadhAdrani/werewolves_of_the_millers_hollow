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
    private var onVoteCast: OnVoteCast,
    private var execution : Boolean = false
    ) : AppCompatDialogFragment() {

    interface OnVoteCast{
        fun onVoteCast(dialog : VotingDialog)
    }

    init {

        for (role : Role in list){
            role.resetVotes()
            voters += role.vote
        }

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
        if (gameActivity.barberTurnRef != null){
            if (!gameActivity.barberTurnRef!!.getRole().isKilled
                && gameActivity.barberRef!!.primaryAbility!!.times != App.ABILITY_NONE){

                App.blink(barber, Color.YELLOW, 1000)

                barber.setOnClickListener {

                    val done = object : OnAction.OnDone{
                        override fun onDone(onAction: OnAction) {

                            if (gameActivity.barberTurnRef!!.getRole().primaryAbility!!.times == App.ABILITY_NONE ||
                                    !gameActivity.barberTurnRef!!.getRole().givenSign)
                                barber.visibility = View.GONE

                            if (execution){
                                dismiss()
                                gameActivity.newRound()
                            }

                        }
                    }

                    val onDismissed = object : UsePowerDialog.OnDismissed {
                        override fun onDismissed() {
                            val onAction = OnAction(activity = gameActivity, adapter = adapter, onDone = done)
                            onAction.onStart()
                        }
                    }

                    val power = UsePowerDialog(
                        turn = gameActivity.barberTurnRef!!,
                        ability = gameActivity.barberTurnRef!!.getPrimaryAbility()!!,
                        onClick = gameActivity.barberTurnRef!!.getOnCallOnClickHandler(),
                        onTargetClick = gameActivity.barberTurnRef!!.getOnTargetHandler(),
                        cancelable = true,
                        onDismissed = onDismissed ,
                        gameActivity = gameActivity
                    )
                    power.show(activity!!.supportFragmentManager, App.TAG_ALERT)

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