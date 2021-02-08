package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.FatherOfWolves
import com.example.werewolfofthemillershollow.roles.Role

class InfectTurn(role : FatherOfWolves) : Turn<FatherOfWolves>() {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {

        for (role : Role in list!!){
            if (role.getIsKilled()!!)
                return context.getString(R.string.infect_instruction)
        }

        return context.getString(R.string.infect_instruction_nobody)
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
            output.add(InfectTurn(list[index] as FatherOfWolves))
            return true
        }

        else
            Log.d("AddTurn","role not found")
        return false
    }
}