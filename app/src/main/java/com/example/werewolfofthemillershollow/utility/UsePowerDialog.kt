package com.example.werewolfofthemillershollow.utility

import android.annotation.SuppressLint
import android.app.Dialog
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
import com.example.werewolfofthemillershollow.turn.Turn

/**
 * Fragment dialog allowing the current player to target the a chosen player with his ability.
 * @param turn current role turn.
 * @param alivePlayers list of players.
 * @param onClick handle click event in the dialog fragment.
 * @param onTargetClick handle click event in the recycler view displaying target players.
 * @see TargetAdapter
 */
class UsePowerDialog(
    private var turn : Turn<*>,
    var ability: Ability,
    private var headerIcon : Int,
    private var alivePlayers : ArrayList<Role>,
    private var deadPlayers : ArrayList<Role>,
    private var targetPlayers : ArrayList<Role>,
    private var onClick : OnClickListener? = null,
    private var onTargetClick : TargetAdapter.OnClickListener? = null,
    private var onDismissed : OnDismissed? = null,
    private var cancelable : Boolean? = true,
    private var gameActivity : GameActivity) : AppCompatDialogFragment() {

    private lateinit var dialog : View

    private lateinit var icon : ImageView

    private lateinit var targetRV : RecyclerView

    private lateinit var targetAdapter : TargetAdapter

    private lateinit var doneButton : TextView

    private lateinit var cancelButton : TextView

    private lateinit var instruction : TextView

    private lateinit var role : TextView

    private var state : Boolean = true

    interface OnClickListener{

        fun done(ability: Ability, aliveList : ArrayList<Role>, deadList: ArrayList<Role>, adapter: TargetAdapter, activity: GameActivity, dialog : UsePowerDialog? = null)

        fun reset(aliveList : ArrayList<Role>, deadList: ArrayList<Role>, adapter: TargetAdapter)

    }

    interface OnDismissed{

        fun onDismissed()

    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = android.app.AlertDialog.Builder(context)

        dialog = activity?.layoutInflater!!.inflate(R.layout.dialog_use_power,null)

        builder.setView(dialog)

        icon = dialog.findViewById(R.id.dialog_icon)
        icon.setImageResource(headerIcon)

        role = dialog.findViewById(R.id.dialog_header)
        role.text = turn.getRoleToDisplay(context, alivePlayers)

        targetRV = dialog.findViewById(R.id.dialog_targets_rv)
        targetRV.layoutManager = LinearLayoutManager(context)
        targetAdapter = TargetAdapter(
            list = targetPlayers,
            dialog =  this,
            handler = onTargetClick
        )
        targetRV.adapter = targetAdapter

        doneButton = dialog.findViewById(R.id.dialog_done)
        doneButton.setOnClickListener {
            onClick?.done(
                ability = ability,
                aliveList = alivePlayers,
                deadList = deadPlayers,
                adapter = targetAdapter,
                activity = gameActivity,
                dialog = this
            )
            onDismissed?.onDismissed()
        }

        cancelButton = dialog.findViewById(R.id.dialog_cancel)
        cancelButton.setOnClickListener {
            if (state){
                dismiss()
            }
            else{
                onClick?.reset(aliveList = alivePlayers, deadList = deadPlayers, adapter = targetAdapter)
                setCancelState()
            }
        }

        instruction = dialog.findViewById(R.id.dialog_instruction)
        instruction.text = turn.getInstructions(context!!,alivePlayers)

        isCancelable = false

        if (this.cancelable != null){
            if (!cancelable!!)
                setCancelState()
        }

        return builder.create()
    }

    /**
     * Change the state to reset.
     */
    fun setResetState(){
        state = false
        cancelButton.setText(R.string.reset)

        if (!cancelable!!)
            cancelButton.visibility = View.VISIBLE
    }

    /**
     * Change the state to cancel.
     */
    fun setCancelState(){
        if (cancelable!!){
            state = true
            cancelButton.setText(R.string.cancel)
        }
        else
            cancelButton.visibility = View.INVISIBLE

    }

}