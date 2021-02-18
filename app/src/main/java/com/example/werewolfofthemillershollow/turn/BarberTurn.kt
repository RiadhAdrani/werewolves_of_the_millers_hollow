package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Barber
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.AlertDialog
import com.example.werewolfofthemillershollow.utility.Event
import com.example.werewolfofthemillershollow.utility.TargetAdapter
import com.example.werewolfofthemillershollow.utility.UsePowerDialog

class BarberTurn(role : Barber, private var activity: GameActivity) : Turn<Barber>(activity) {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        if (getRole().isKilled)
            return context.getString(R.string.barber_instruction_killed)

        return context.getString(R.string.barber_instruction)
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun usePrimary(target: Role): Boolean {
        return getRole().usePrimaryAbility(role = target)
    }

    override fun useSecondary(target: Role): Boolean {
        return false
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1){
            output.add(BarberTurn(list[index] as Barber, activity = activity))
            return true
        }

        Log.d("AddTurn","Barber role not found")
        return false

    }

    override fun canPrimary(): Boolean {
        return getRole().isKilled
    }

    override fun onStart(activity: GameActivity): Boolean {

        if (getRole().isKilled){

            if (!getHasPrimary()){
                val dialog = AlertDialog(
                    text = R.string.no_power,
                    icon = Icons.noAbility,
                    cancelable = false
                )
                dialog.show(activity.supportFragmentManager, App.TAG_ALERT)
                return false
            }

            return true
        }

        return false
    }

    override fun getOnStartOnClickHandler(): UsePowerDialog.OnClickListener {
        return getPrimaryOnClickHandler()
    }

    override fun getOnStartTargets(list: ArrayList<Role>): ArrayList<Role> {
        return getTargetsPrimary(list)
    }

    override fun getOnStartOnTargetHandler(): TargetAdapter.OnClickListener? {

        return if (getHasPrimary())
            getPrimaryOnTargetHandler()
        else null
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        if (getRole().isKilled)
            return true

        return false
    }

    override fun servant(activity: GameActivity): Int {

        if (activity.servantRef == null)
            return -1

        val index : Int = activity.playerList.indexOf(activity.servantRef)
        Log.d("servant","servant index = $index")

        if (index == -1)
            return -1

        val player = activity.servantRef!!.player ?: return -1

        val sub = getRole().new(activity, player, activity.servantRef)
        sub.debug(tag = "servant")

        setRole(sub)
        getRole().debug(tag = "servant")

        activity.playerList.removeAt(index)
        activity.playerList.add(index, sub)
        activity.events.add(Event.servant(activity,sub.name))

        activity.barberRef = getRole()

        return index
    }


}