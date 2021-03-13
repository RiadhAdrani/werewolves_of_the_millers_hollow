package com.example.werewolfofthemillershollow.utility

import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.*
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.turn.Turn

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
    private var onDone: OnDone) {

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

        Log.d("Action","${list.size}")

        val current = list[index]
        current.getRole().debug(tag = "Action")

        if (current.getOnCallAbility()!!.times == App.ABILITY_NONE){
            index ++
            onStart()
            return
        }

        val onClick = object : UsePowerDialog.OnClickListener{
            override fun done(
                ability: Ability,
                aliveList: ArrayList<Role>,
                deadList: ArrayList<Role>,
                adapter: TargetAdapter,
                activity: GameActivity,
                dialog: UsePowerDialog?
            ): Boolean {
                if (adapter.getTargets().isEmpty()){
                    val alert = AlertDialog(text = R.string.should_use_power)
                    alert.show(activity.supportFragmentManager, App.TAG_ALERT)
                    return false
                }

                for(index : Int in adapter.getTargets()){

                    val target : Role = adapter.getList()[index]

                    val i = activity.playerList.indexOf(target)

                    if (i == -1)
                        return false

                    ability.use(self = current.getRole(), role = activity.playerList[i], activity.playerList)

                }

                val onClick = object : AlertDialog.OnClick{
                    override fun onClick(alertDialog: AlertDialog) {
                        index ++
                        onStart()
                        alertDialog.dismiss()
                        dialog!!.dismiss()
                    }
                }

                val goodNightDialog = AlertDialog(
                    text = R.string.good_night,
                    rightButton = onClick,
                    cancelable = false)
                goodNightDialog.show(activity.supportFragmentManager, App.TAG_ALERT)
                return false
            }

            override fun reset(
                aliveList: ArrayList<Role>,
                deadList: ArrayList<Role>,
                adapter: TargetAdapter
            ) {
                adapter.emptyTargets()
            }
        }



        val proceed = object : AlertDialog.OnClick{
            override fun onClick(alertDialog: AlertDialog) {
                alertDialog.dismiss()
                val dialog = UsePowerDialog(
                    turn = current,
                    ability = current.getOnCallAbility()!!,
                    onClick = onClick,
                    onTargetClick = current.getOnTargetHandler(),
                    onDismissed = null,
                    cancelable = false,
                    gameActivity = activity)
                dialog.show(activity.supportFragmentManager, App.TAG_ALERT)
            }

        }

        val wakeDialog = AlertDialog(
            icon = current.getIcon(),
            text = -1,
            string = "${activity.getString(R.string.good_night_all)} \n ${activity.getString(R.string.wake_up)} ${current.getRoleToDisplay(activity, activity.playerList)}",
            rightButton = proceed,
            cancelable = false
            )
        wakeDialog.show(activity.supportFragmentManager, App.TAG_ALERT)

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

            val dialog = EventsDialog(gameActivity = activity, this.events, onClick = onClick, cancelable = false)
            dialog.show(activity.supportFragmentManager,App.TAG_ALERT)

        }
    }

    private fun setList(){

        list.clear()

        for (turn : Turn<*> in activity.turnList){
            if (turn.getRole().isKilled && turn.getOnCallAbility() != null){
                if (turn.getRoleToDisplay(activity.baseContext,activity.playerList)
                    == activity.getString(R.string.captain_name) && turn.getRole().isServed)
                    turn.servant(activity,events)
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