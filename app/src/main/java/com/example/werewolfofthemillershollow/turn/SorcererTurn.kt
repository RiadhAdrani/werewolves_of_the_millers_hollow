package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.roles.Sorcerer
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.utility.*

class SorcererTurn(role : Sorcerer, var activity: GameActivity) : Turn<Sorcerer>(activity) {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {

        val output : String = context.getString(R.string.sorcerer_instruction_heal) + context.getString(R.string.sorcerer_instruction_kill)
        val dead = ArrayList<Role>()

        for (role : Role in list!!){
            if (role.isKilled && !role.isKilledBySorcerer){
                dead.add(role)
            }
        }

        return if (dead.isEmpty()){
            "(${activity.getString(R.string.none)}) ${context.getString(R.string.was_killed)}. $output "
        } else {
            if (dead.size == 1){
                "(${dead[0].player}) ${context.getString(R.string.was_killed)}. $output "
            } else {
                "(${App.listToString(dead, activity)}) ${context.getString(R.string.was_killed)}. $output "
            }
        }
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1) {
            output.add(SorcererTurn(list[index] as Sorcerer, activity))
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
        setRole(sub as Sorcerer)

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        events.add(Event.servant(activity,sub))
        return index
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
                if (adapter.getTargets().isEmpty()){
                    val alert = AlertDialog(text = R.string.should_use_power)
                    alert.show(activity.supportFragmentManager, App.TAG_ALERT)
                    return false
                }

                Log.d("Role","Turn Class : using secondary ability")

                for(index : Int in adapter.getTargets()){

                    val target : Role = adapter.getList()[index]

                    val i = activity.playerList.indexOf(target)

                    if (i == -1)
                        return false

                    ability.use(self = getRole(), role = activity.playerList[i], activity.playerList)
                    activity.playerList[i].debug()

                }

                dialog!!.dismiss()

                if (getPrimaryAbility()!!.times == getSecondaryAbility()!!.times && getPrimaryAbility()!!.times == App.ABILITY_NONE){
                    val onClick = object : AlertDialog.OnClick{
                        override fun onClick(alertDialog: AlertDialog) {
                            activity.next()
                            alertDialog.dismiss()
                        }
                    }

                    AlertDialog.displayDialog(
                        activity = activity,
                        text = R.string.good_night,
                        rightButton = onClick,
                        cancelable = false)
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
}