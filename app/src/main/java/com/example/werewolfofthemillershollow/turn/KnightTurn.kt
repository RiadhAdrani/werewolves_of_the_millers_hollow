package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Knight
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.AlertDialog
import com.example.werewolfofthemillershollow.utility.Event
import com.example.werewolfofthemillershollow.utility.TargetAdapter
import com.example.werewolfofthemillershollow.utility.UsePowerDialog

class KnightTurn(role : Knight, var activity: GameActivity) : Turn<Knight>(activity) {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {

        if (getRole().isKilled)
            return context.getString(R.string.knight_instruction_targeted)

        return context.getString(R.string.knight_instruction_peace)
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1) {
            output.add(KnightTurn(list[index] as Knight, activity))
            return true
        }

        else
            Log.d("AddTurn","Knight role not found")
        return false
    }

    override fun onStart(activity: GameActivity): Boolean {

        if (getRole().isKilled){

            if (getPrimaryAbility()!!.times != App.ABILITY_NONE){
                val dialog = AlertDialog(
                    text = R.string.no_power,
                    icon = Icons.noAbility,
                    cancelable = false)
                dialog.show(activity.supportFragmentManager, App.TAG_ALERT)
                return false
            }

            return true
        }

        return false
    }

    override fun getOnStartOnClickHandler(): UsePowerDialog.OnClickListener {
        return getOnClickHandler()
    }

    override fun getOnStartOnTargetHandler(): TargetAdapter.OnClickListener? {

        return if (getPrimaryAbility()!!.times != App.ABILITY_NONE)
            getOnTargetHandler()
        else null
    }

    override fun getOnStartTargets(list: ArrayList<Role>): ArrayList<Role> {
        return getPrimaryAbility()!!.targetList(self = getRole(),list = list)
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        return getRole().isKilled
    }

    override fun servant(activity: GameActivity): Int {
        if (activity.servantRef == null)
            return -1

        val index : Int = activity.playerList.indexOf(activity.servantRef)
        if (index == -1)
            return -1

        val player = activity.servantRef!!.player ?: return -1
        val sub = getRole().new(activity, player, activity.servantRef)
        setRole(sub as Knight)

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        activity.events.add(Event.servant(activity,sub.name))
        return index
    }
}