package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.FatherOfWolves
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.*

class InfectTurn(role : FatherOfWolves, var activity: GameActivity) : Turn<FatherOfWolves>(activity) {

    init {
        setRole(role)
    }

    private fun infectionRoutine(infected : String = activity.getString(R.string.none)): Boolean{

        val onClick = object : AlertDialog.OnClick{
            override fun onClick(alertDialog: AlertDialog) {

                alertDialog.dismiss()

                val skip = object : AlertDialog.OnClick{
                    override fun onClick(alertDialog: AlertDialog) {
                        activity.next()
                        alertDialog.dismiss()
                    }
                }

                AlertDialog.displayDialog(
                    icon = Icons.wolves,
                    activity = activity,
                    text = R.string.good_night,
                    rightButton = skip,
                    cancelable = false)
            }
        }


        val onTouch = object : AlertDialog.OnClick{
            override fun onClick(alertDialog: AlertDialog) {
                AlertDialog.displayDialog(
                    activity = activity,
                    icon = Icons.wolves,
                    text = R.string.infect_wake_pack,
                    rightButton = onClick,
                    cancelable = false
                )
                alertDialog.dismiss()
            }
        }

        AlertDialog.displayDialog(
            activity = activity,
            icon = Icons.infectInfect,
            text = -1,
            contentText = " ${activity.getString(R.string.good_night)}\n" +
                    "${activity.getString(R.string.infection_effect_touch)} " +
                    "(${activity.getString(R.string.infection_touch )} ${infected}).",
            rightButton = onTouch,
            cancelable = false)

        return true
    }

    override fun onSkip(activity: GameActivity): Boolean {
        return infectionRoutine()
    }

    override fun getOnClickHandler(): UsePowerDialog.OnClickListener {

        return object : UsePowerDialog.OnClickListener{
            override fun done(
                ability: Ability,
                aliveList: ArrayList<Role>,
                deadList: ArrayList<Role>,
                adapter: TargetAdapter,
                activity: GameActivity,
                dialog: UsePowerDialog?
            ): Boolean {

                if (adapter.getList().isEmpty()){
                    infectionRoutine()
                    dialog!!.dismiss()
                    return false
                }

                for(index : Int in adapter.getTargets()){

                    val target : Role = adapter.getList()[index]

                    val i = activity.playerList.indexOf(target)

                    if (i == -1)
                        return false
                    else {
                        val success = getPrimaryAbility()!!.use(getRole(),activity.playerList[i], activity.playerList)
                        if (success){
                            infectionRoutine(if (success) activity.playerList[i].player!! else activity.getString(R.string.none))
                        }

                        dialog!!.dismiss()
                    }

                }

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
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {

        return context.getString(R.string.infect_instruction_nobody)
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1) {
            output.add(InfectTurn(list[index] as FatherOfWolves,activity))
            return true
        }

        else
            Log.d("AddTurn","role not found")
        return false
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        return false
    }

    override fun servant(activity: GameActivity, events: ArrayList<Event>): Int {
        if (activity.servantRef == null)
            return -1

        val index : Int = activity.playerList.indexOf(activity.servantRef)
        if (index == -1)
            return -1

        val player = activity.servantRef!!.player ?: return -1
        val sub = getRole().new(activity, player, activity.servantRef)
        sub.primaryAbility!!.times = getRole().primaryAbility!!.times
        setRole(sub as FatherOfWolves)
        

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        events.add(Event.servant(activity,sub))
        return index
    }
}