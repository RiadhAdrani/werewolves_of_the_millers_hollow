package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Barber
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.roles.Seer
import com.example.werewolfofthemillershollow.utility.Event

class SeerTurn(role : Seer, var activity: GameActivity) : Turn<Seer>(activity) {

    init {
        setRole(role,)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        return context.getString(R.string.seer_instruction)
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun usePrimary(target: Role): Boolean {
        return getRole().usePrimaryAbility(role = target!!)
    }

    override fun useSecondary(target: Role): Boolean {
        return false
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1) {
            output.add(SeerTurn(list[index] as Seer, activity))
            return true
        }

        else
            Log.d("AddTurn","Seer role not found")
        return false
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        return true
    }

    override fun servant(activity: GameActivity): Int {
        if (activity.servantRef == null)
            return -1

        val index : Int = activity.playerList.indexOf(activity.servantRef)
        if (index == -1)
            return -1

        val player = activity.servantRef!!.getPlayer() ?: return -1
        val sub = getRole().new(activity, player, activity.servantRef)
        setRole(sub as Seer)

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        activity.events.add(Event.servant(activity,sub.getName()!!))
        return index
    }
}