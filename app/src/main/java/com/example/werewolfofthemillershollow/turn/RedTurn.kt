package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.FatherOfWolves
import com.example.werewolfofthemillershollow.roles.RedWolf
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.util.Event

class RedTurn(role: RedWolf,var activity: GameActivity) : Turn<RedWolf>(activity) {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        return context.getString(R.string.red_instruction)
    }

    override fun addTurn(
        output: ArrayList<Turn<*>>,
        list: ArrayList<Role>,
        context: Context
    ): Boolean {
        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1) {
            output.add(RedTurn(list[index] as RedWolf,activity))
            return true
        }

        else
            Log.d("AddTurn","role not found")
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
        setRole(sub as RedWolf)


        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        events.add(Event.servant(activity,sub))
        return index
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return true
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        return true
    }
}