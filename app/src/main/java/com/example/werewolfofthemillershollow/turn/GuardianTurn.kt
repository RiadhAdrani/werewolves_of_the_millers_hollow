package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Guardian
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.utility.Event

class GuardianTurn(role : Guardian, var activity: GameActivity) : Turn<Guardian>(activity) {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        return context.getString(R.string.guardian_instruction)
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1) {
            output.add(GuardianTurn(list[index] as Guardian, activity))
            return true
        }

        else
            Log.d("AddTurn","Guardian role not found")
        return false
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        return true
    }

    override fun servant(activity: GameActivity, events: ArrayList<Event>): Int {
        if (activity.servantRef == null)
            return -1

        val index : Int = activity.playerList.indexOf(activity.servantRef)
        if (index == -1)
            return -1

        val player = activity.servantRef!!.player ?: return -1
        val sub = getRole().new(activity, player, activity.servantRef)
        setRole(sub as Guardian)

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        events.add(Event.servant(activity,sub))
        return index
    }

}