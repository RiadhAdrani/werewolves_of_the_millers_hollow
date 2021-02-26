package com.example.werewolfofthemillershollow.utility

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
 * @param onDone overridden
 */
class OnAction(
    private var activity: GameActivity,
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

        if (list.isEmpty() || index < 0 || index == list.size){
            onComplete()
            return
        }

        val current = list[index]

        if (current.getOnCallAbility() != null){
            if (current.getOnCallAbility()!!.times == App.ABILITY_NONE){
                index ++
                onStart()
                return
            }
        }

        val onClick = object : UsePowerDialog.OnClickListener{
            override fun done(
                ability: Ability,
                aliveList: ArrayList<Role>,
                deadList: ArrayList<Role>,
                adapter: TargetAdapter,
                activity: GameActivity,
                dialog: UsePowerDialog?
            ) {
                if (adapter.getTargets().isEmpty()){
                    val alert = AlertDialog(text = R.string.should_use_power)
                    alert.show(activity.supportFragmentManager, App.TAG_ALERT)
                    return
                }

                for(index : Int in adapter.getTargets()){

                    val target : Role = adapter.getList()[index]

                    val i = activity.playerList.indexOf(target)

                    if (i == -1)
                        return

                    ability.use(self = current.getRole(), role = activity.playerList[i], activity.playerList)
                    activity.playerList[i].debug()

                }

                val onClick = object : AlertDialog.OnClick{
                    override fun onClick(alertDialog: AlertDialog) {

                        alertDialog.dismiss()
                        dialog!!.dismiss()
                        index ++
                        onStart()
                    }
                }

                val goodNightDialog = AlertDialog(
                    text = R.string.good_night,
                    rightButton = onClick,
                    cancelable = false)
                goodNightDialog.show(activity.supportFragmentManager, App.TAG_ALERT)
            }

            override fun reset(
                aliveList: ArrayList<Role>,
                deadList: ArrayList<Role>,
                adapter: TargetAdapter
            ) {
                adapter.emptyTargets()
            }
        }

        val dialog = UsePowerDialog(
            turn = current,
            ability = current.getOnCallAbility()!!,
            onClick = onClick, current.getOnTargetHandler(),
            onDismissed = null,
            cancelable = false,
            gameActivity = activity)
        dialog.show(activity.supportFragmentManager, App.TAG_ALERT)

    }

    private fun onComplete(){

        setList()
        if (list.isNotEmpty())
            onStart()
        else{

            resolve()

            val onClick = object : EventsDialog.OnClick{
                override fun onClick(): Boolean {
                    onDone.onDone(this@OnAction)
                    return true
                }
            }

            val dialog = EventsDialog(this.events,onClick,false)
            dialog.show(activity.supportFragmentManager,App.TAG_ALERT)

        }
    }

    private fun setList(){

        for (turn : Turn<*> in activity.turnList){
            if (turn.getRole().isKilled && turn.getOnCallAbility() != null){
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

        i=0
        while (i < activity.playerList.size){
            activity.playerList[i].resetStatusEffects()

            if (activity.playerList[i].isKilled){
                if (activity.playerList[i].isServed){
                    activity.playerList[i].servant(activity)
                }
                events.add(Event.died(activity, activity.playerList[i].player!!))
                activity.deadList.add(activity.playerList[i])
                activity.playerList.removeAt(i)
                i--
            }
            i++
        }

    }

}