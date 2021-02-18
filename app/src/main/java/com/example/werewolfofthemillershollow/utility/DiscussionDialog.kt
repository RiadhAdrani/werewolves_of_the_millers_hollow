package com.example.werewolfofthemillershollow.utility

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App

/**
 * Display a discussion dialog.
 * @param gameActivity a reference to the game activity.
 */
class DiscussionDialog(
    private var playerList : ArrayList<Role>,
    private var gameActivity: GameActivity,
    private var display : String? = null,
    var onVote: OnVote? = null,
    private var cancelable : Boolean? = false) : AppCompatDialogFragment() {

    interface OnVote{
        fun onVote():Boolean
    }

    private lateinit var dialog : View

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = android.app.AlertDialog.Builder(context)

        dialog = activity?.layoutInflater!!.inflate(R.layout.dialog_discussion,null)

        builder.setView(dialog)

        isCancelable = cancelable!!

        val barber : ImageView = dialog.findViewById(R.id.dialog_barber)
        when {
            gameActivity.barberRef == null -> {
                barber.visibility = View.GONE
                Log.d("Role","Barber does not exist")
            }
            gameActivity.barberRef!!.getPrimaryType() == App.ABILITY_NONE || !gameActivity.barberRef!!.isAlive -> {
                barber.visibility = View.GONE
                Log.d("Role","Barber has no ability")
            }
            else -> {
                App.blink(barber, Color.YELLOW, 1500L)
                barber.setOnClickListener {

                }
                Log.d("Role","Barber exists")
            }
        }

        val text : TextView = dialog.findViewById(R.id.dialog_text)
        when {
            display != null -> {
                text.text = display
            }
            playerList.size == gameActivity.playerList.size -> {
                text.text = getString(R.string.discussion_description)
            }
            playerList.size > 1 -> {
                text.text = "${App.listToString(playerList, activity!!)} ${getString(R.string.discussion_description_group)}"
            }
            else -> {
                text.text = "${App.listToString(playerList, activity!!)} ${getString(R.string.discussion_description_single)}"
            }
        }

        val vote : TextView = dialog.findViewById(R.id.dialog_button)
        vote.setOnClickListener {
            if (onVote != null){
                if (onVote!!.onVote()){
                    dismiss()
                }
            }
        }

        return builder.create()
    }
}