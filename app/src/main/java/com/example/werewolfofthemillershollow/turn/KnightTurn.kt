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
import com.example.werewolfofthemillershollow.utility.TargetAdapter
import com.example.werewolfofthemillershollow.utility.UsePowerDialog

class KnightTurn(role : Knight) : Turn<Knight>() {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {

        if (getRole().getIsKilled()!!)
            return context.getString(R.string.knight_instruction_targeted)

        return context.getString(R.string.knight_instruction_peace)
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

        if (index != -1) {
            output.add(KnightTurn(list[index] as Knight))
            return true
        }

        else
            Log.d("AddTurn","Knight role not found")
        return false
    }

    override fun canPrimary(): Boolean {
        return getRole().getIsKilled()!!
    }

    override fun onStart(activity: GameActivity): Boolean {

        if (getRole().getIsKilled()!!){

            if (!getHasPrimary()){
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
        return getPrimaryOnClickHandler()
    }

    override fun getOnStartOnTargetHandler(): TargetAdapter.OnClickListener? {

        return if (getHasPrimary())
            getPrimaryOnTargetHandler()
        else null
    }

    override fun getOnStartTargets(list: ArrayList<Role>): ArrayList<Role> {
        return getTargetsPrimary(list)
    }
}