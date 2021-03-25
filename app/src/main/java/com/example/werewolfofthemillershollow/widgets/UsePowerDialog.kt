package com.example.werewolfofthemillershollow.widgets

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
import com.example.werewolfofthemillershollow.adapters.TargetAdapter
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.turn.Turn
import com.example.werewolfofthemillershollow.util.Ability

/**
 * Fragment dialog allowing the current player to target the a chosen player with his ability.
 * @param turn current role turn. see [Turn].
 * @param ability ability to use. see [Ability].
 * @param onClick handle click event in the dialog fragment.
 * @param onTargetClick handle click event in the recycler view displaying target players.
 * @param onDismissed handle on dismissed event.
 * @param cancelable indicates if the dialog is cancelable or not.
 * @param gameActivity context. see [GameActivity].
 * @see TargetAdapter
 */
class UsePowerDialog(
    private var turn : Turn<*>,
    var ability: Ability,
    private var onClick : OnClickListener? = null,
    private var onTargetClick : TargetAdapter.OnClickListener? = null,
    var onDismissed : OnDismissed? = null,
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

        /**
         * Executes when done button is pressed.
         * @param ability ability used.
         * @param aliveList list of alive players.
         * @param deadList list of dead players.
         * @param adapter target adapter.
         * @param activity game activity.
         * @param dialog current use power dialog.
         * @return true to execute [onDismissed].
         */
        fun done(
            ability: Ability,
            aliveList : ArrayList<Role>,
            deadList: ArrayList<Role>,
            adapter: TargetAdapter,
            activity: GameActivity,
            dialog : UsePowerDialog? = null): Boolean

        /**
         * Reset the target adapter.
         * @param aliveList list of alive players.
         * @param deadList list of dead players.
         * @param adapter target adapter.
         */
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
        icon.setImageResource(ability.icon)

        role = dialog.findViewById(R.id.dialog_header)
        role.text = turn.getRoleToDisplay(context, gameActivity.playerList)

        targetRV = dialog.findViewById(R.id.dialog_targets_rv)
        targetRV.layoutManager = LinearLayoutManager(context)
        targetAdapter = TargetAdapter(
            list = ability.targetList(turn.getRole(),gameActivity.playerList),
            dialog =  this,
            handler = onTargetClick
        )
        targetRV.adapter = targetAdapter

        doneButton = dialog.findViewById(R.id.dialog_done)
        doneButton.setOnClickListener {

            val dismiss = onClick?.done(
                ability = ability,
                aliveList = gameActivity.playerList,
                deadList = gameActivity.deadList,
                adapter = targetAdapter,
                activity = gameActivity,
                dialog = this
            )
            if (dismiss!! && onDismissed != null )
                onDismissed?.onDismissed()
        }

        cancelButton = dialog.findViewById(R.id.dialog_cancel)
        cancelButton.setOnClickListener {
            if (state){
                dismiss()
            }
            else{
                onClick?.reset(aliveList = gameActivity.playerList, deadList = gameActivity.deadList, adapter = targetAdapter)
                setCancelState()
            }
        }

        instruction = dialog.findViewById(R.id.dialog_instruction)
        instruction.text = turn.getInstructions(context!!, gameActivity.playerList)

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