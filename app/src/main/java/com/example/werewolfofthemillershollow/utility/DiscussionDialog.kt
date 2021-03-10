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
    var onNext: OnNext? = null,
    private var cancelable : Boolean? = false) : AppCompatDialogFragment() {

    interface OnNext{
        fun onNext():Boolean
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
            gameActivity.barberRef!!.primaryAbility!!.times == App.ABILITY_NONE || !gameActivity.barberRef!!.isAlive -> {
                barber.visibility = View.GONE
                Log.d("Role","Barber has no ability")
            }
            else -> {
                App.blink(barber, Color.YELLOW, 1500L)
                barber.setOnClickListener {

                    val done = object : OnAction.OnDone{
                        override fun onDone(onAction: OnAction) {
                            if (gameActivity.barberTurnRef!!.getRole().primaryAbility!!.times == App.ABILITY_NONE ||
                                !gameActivity.barberTurnRef!!.getRole().givenSign ||
                                gameActivity.barberTurnRef!!.getRole().isKilled)
                                barber.visibility = View.GONE
                        }
                    }

                    val onDismissed = object : UsePowerDialog.OnDismissed {
                        override fun onDismissed() {
                            val onAction = OnAction(activity = gameActivity, onDone = done)
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
            if (onNext != null){
                if (onNext!!.onNext()){
                    dismiss()
                }
            }
        }

        return builder.create()
    }
}