package com.example.werewolfofthemillershollow.utility

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.MainActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.Icons

/**
 * Display Events that happened during the last night (round).
 * @param gameActivity activity.
 * @param events list of events.
 * @param onClick override action with a new interface implementation.
 * @param cancelable set if the dialog is cancelable or not. true by default.
 */
class EventsDialog(
    private var gameActivity : GameActivity,
    private var events : ArrayList<Event>,
    private var onClick: OnClick,
    private var cancelable : Boolean? = true): AppCompatDialogFragment() {

    private lateinit var dialog : View

    interface OnClick{

        fun onClick(): Boolean

    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = android.app.AlertDialog.Builder(context)

        dialog = activity?.layoutInflater!!.inflate(R.layout.dialog_events,null)

        builder.setView(dialog)

        val icon : ImageView = dialog.findViewById(R.id.dialog_icon)
        icon.setImageResource(Icons.moon)

        val isOver = Role.isGameOver(gameActivity.playerList, gameActivity)

        val text : TextView = dialog.findViewById(R.id.dialog_text)
        val temp = if (isOver != 0) Role.isGameOverMessage(isOver) else R.string.today_event
        text.setText(temp)

        val events : RecyclerView = dialog.findViewById(R.id.dialog_event_list)
        events.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        events.adapter = EventAdapter(context = context!!, list = this.events)

        val button : TextView = dialog.findViewById(R.id.dialog_button)
        button.setOnClickListener {

            if (isOver != 0){
                val i = Intent(gameActivity, MainActivity::class.java)
                startActivity(i)
                gameActivity.finish()
                return@setOnClickListener
            }

            if (onClick.onClick()){
                dismiss()
            }
        }

        isCancelable = cancelable!!

        return builder.create()
    }
}