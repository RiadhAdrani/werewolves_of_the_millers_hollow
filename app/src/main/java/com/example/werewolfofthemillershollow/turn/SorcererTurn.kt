package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Barber
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.roles.Sorcerer
import com.example.werewolfofthemillershollow.utility.Event

class SorcererTurn(role : Sorcerer, var activity: GameActivity) : Turn<Sorcerer>(activity) {

    init {
        setRole(role,)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {

        val output : String = context.getString(R.string.sorcerer_instruction_heal) + context.getString(R.string.sorcerer_instruction_kill)
        val dead = ArrayList<Role>()

        for (role : Role in list!!){
            if (role.getIsKilled()!!){
                dead.add(role)
            }
        }

        var deadString = ""
        if (dead.isNotEmpty()){
            for (role : Role in dead){
                if (dead.indexOf(role) == 0){
                    deadString += role.getPlayer()
                    continue
                }
                if (dead.indexOf(role) == dead.size-1){
                    deadString += " " + context.getString(R.string.and) +" "+ role.getPlayer()
                    continue
                }
                else
                    deadString += ", "+role.getPlayer()
            }

            return if (dead.size == 1)
                deadString +" " + context.getString(R.string.was_killed) + "... " + output
            else
                deadString +" " + context.getString(R.string.were_killed) + "... " + output

        }

        return context.getString(R.string.nobody_was_killed) + "..." + output
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun usePrimary(target: Role): Boolean {
        return getRole().usePrimaryAbility(role = target)
    }

    override fun canSecondary(): Boolean {
        return getRole().getHasSecondary()!!
    }

    override fun useSecondary(target: Role): Boolean {
        Log.d("Role","using secondary")
        return getRole().useSecondaryAbility(role = target)
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

    override fun servant(activity: GameActivity): Int {
        if (activity.servantRef == null)
            return -1

        val index : Int = activity.playerList.indexOf(activity.servantRef)
        if (index == -1)
            return -1

        val player = activity.servantRef!!.getPlayer() ?: return -1
        val sub = getRole().new(activity, player, activity.servantRef)
        setRole(sub as Sorcerer)

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        activity.events.add(Event.servant(activity,sub.getName()!!))
        return index
    }
}