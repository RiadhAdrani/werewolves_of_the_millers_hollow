package com.example.werewolfofthemillershollow.util

import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.adapters.VotingAdapter
import com.example.werewolfofthemillershollow.roles.*
import com.example.werewolfofthemillershollow.turn.Turn
import com.example.werewolfofthemillershollow.widgets.AlertDialog
import com.example.werewolfofthemillershollow.widgets.EventsDialog
import com.example.werewolfofthemillershollow.widgets.UsePowerDialog

/**
 * Class that handle actions after a role has used his power in the morning discussion.
 * An example of the usage of an instance of this class is
 * when a [Barber] uses his power to try and kill a wolf: his action could lead to many deaths,
 * and with that the need of a solid system that
 * handle all these deaths and role-swapping ... etc.
 * @param activity context of type [GameActivity].
 * @param adapter in case of an action in the voting phase, this could be used to update the adapter.
 * @param onDone overridden [onDone] interface.
 */
class OnAction(
    private var activity: GameActivity,
    private var adapter: VotingAdapter? = null,
    private var onDone: OnDone
) {

    interface OnDone{
        fun onDone(onAction: OnAction)
    }

    var list : ArrayList<Turn<*>> = ArrayList()

    var index : Int = -1

    var events : ArrayList<Event> = ArrayList()

    init {
        setList()
    }

    fun onStart(){

        if (list.isEmpty() || index >= list.size){
            onComplete()
            return
        }

        val current = list[index]

        if (current.getOnCallAbility()!!.times == App.ABILITY_NONE){
            index ++
            onStart()
            return
        }

        val proceed = object : AlertDialog.OnClick{
            override fun onClick(alertDialog: AlertDialog) {

                alertDialog.dismiss()
                val dialog = UsePowerDialog(
                    turn = current,
                    ability = current.getOnCallAbility()!!,
                    onClick = current.getOnActionOnClickHandler(this@OnAction),
                    onTargetClick = current.getOnTargetHandler(),
                    onDismissed = null,
                    cancelable = false,
                    gameActivity = activity)

                dialog.show(activity.supportFragmentManager, App.TAG_ALERT)

            }
        }

        AlertDialog.displayDialog(
            activity = activity,
            icon = current.getIcon(),
            text = -1,
            contentText = "${activity.getString(R.string.good_night)} \n ${activity.getString(R.string.wake_up)} ${current.getRoleToDisplay(activity, activity.playerList)}",
            rightButton = proceed,
            cancelable = false
        )

    }

    private fun onComplete(){

        setList()

        if (list.isNotEmpty()) {
            onStart()
        }
        else{

            resolve()
            val onClick = object : EventsDialog.OnClick{
                override fun onClick(): Boolean {
                    onDone.onDone(this@OnAction)
                    adapter?.notifyDataSetChanged()
                    return true
                }
            }

            val dialog = EventsDialog(
                gameActivity = activity,
                events = this.events,
                onClick = onClick,
                cancelable = false)
            dialog.show(activity.supportFragmentManager,App.TAG_ALERT)

        }
    }

    private fun setList(){

        list.clear()

        for (turn : Turn<*> in activity.turnList){
            if (turn.getRole().isKilled && turn.getOnCallAbility() != null){
                if (turn.getRole().isCaptain && turn.getRole().isServed){
                    turn.servant(activity,events)
                    continue
                }
                if (turn.getOnCallAbility()!!.times != App.ABILITY_NONE)
                    list.add(turn)
            }
        }
        index = if (list.isEmpty()) -1 else 0
    }


    /**
     * function used to initialize the discussion in the morning.
     */
    private fun resolve(){

        for (turn : Turn<*> in activity.turnList){
            turn.getRole().debug()
            if (turn.getRole().isKilled){
                if (turn.getRole().isServed)
                    turn.servant(activity, this.events)
            }

        }

        var i = 0
        while (i < activity.turnList.size){
            if (activity.turnList[i].getRole().isKilled && !activity.turnList[i].getRole().isServed){
                activity.turnList.removeAt(i)
                i--
            }

            i++
        }

        adapter?.removeDead()

        i=0
        while (i < activity.playerList.size){

            if (activity.playerList[i].isKilled){

                events.add(Event.died(activity, activity.playerList[i]))

                if (activity.playerList[i].isServed){
                    activity.playerList[i].servant(activity, events)
                }

                activity.deadList.add(activity.playerList[i])

                activity.playerList.removeAt(i)
                i--
            }
            i++
        }


    }

}