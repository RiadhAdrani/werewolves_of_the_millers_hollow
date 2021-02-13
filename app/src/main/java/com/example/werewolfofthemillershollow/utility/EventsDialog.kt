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
import com.example.werewolfofthemillershollow.settings.Icons

class EventsDialog(private var events : ArrayList<Event>, private var onClick: OnClick, private var cancelable : Boolean? = true): AppCompatDialogFragment() {

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

        val text : TextView = dialog.findViewById(R.id.dialog_text)
        text.setText(R.string.today_event)

        val events : RecyclerView = dialog.findViewById(R.id.dialog_event_list)
        events.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        events.adapter = EventAdapter(context = context!!, list = this.events)

        val button : TextView = dialog.findViewById(R.id.dialog_button)
        button.setOnClickListener {

            if (onClick.onClick()){
                dismiss()
            }
        }

        isCancelable = cancelable!!

        return builder.create()
    }
}