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
    private var alivePlayers : ArrayList<Role>,
    private var onClick : OnClickListener? = null,
    private var onTargetClick : TargetAdapter.OnClickListener? = null) : AppCompatDialogFragment() {

    private lateinit var dialog : View

    private lateinit var icon : ImageView

    private lateinit var targetRV : RecyclerView

    private lateinit var targetAdapter : TargetAdapter

    private lateinit var doneButton : TextView

    private lateinit var cancelButton : TextView

    private lateinit var instruction : TextView

    private var state : Boolean = true

    interface OnClickListener{

        fun done(list : ArrayList<Role>)

        fun reset(list : ArrayList<Role>)

        fun cancel(list : ArrayList<Role>)

    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = android.app.AlertDialog.Builder(context)

        dialog = activity?.layoutInflater!!.inflate(R.layout.dialog_use_power,null)

        builder.setView(dialog)

        icon = dialog.findViewById(R.id.dialog_icon)

        targetRV = dialog.findViewById(R.id.dialog_targets_rv)
        targetRV.layoutManager = LinearLayoutManager(context)
        targetAdapter = TargetAdapter(list = turn.getTargets(alivePlayers), context = context!!, handler = onTargetClick)
        targetRV.adapter = targetAdapter

        doneButton = dialog.findViewById(R.id.dialog_done)
        doneButton.setOnClickListener {
            onClick?.done(list = turn.getTargets(alivePlayers))
        }

        cancelButton = dialog.findViewById(R.id.dialog_cancel)
        cancelButton.setOnClickListener {
            if (state){
                onClick?.cancel(list = turn.getTargets(alivePlayers))
                dismiss()
            }
            else
                onClick?.reset(list = turn.getTargets(alivePlayers))
        }

        instruction = dialog.findViewById(R.id.dialog_instruction)
        instruction.text = turn.getInstructions(context!!,alivePlayers)

        isCancelable = false

        return builder.create()
    }

    /**
     * Change the state to reset.
     */
    fun setResetState(){
        state = false
    }

    /**
     * Change the state to cancel.
     */
    fun setCancelState(){
        state = true
    }

}