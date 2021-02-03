package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Barber
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

    override fun usePrimary(singleTarget: Role?, multipleTargets: ArrayList<Role>?): Boolean {
        return getRole().usePrimaryAbility(role = singleTarget!!)
    }

    override fun useSecondary(singleTarget: Role?, multipleTargets: ArrayList<Role>?): Boolean {
        return false
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context) {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1)
            output.add(InfectTurn(list[index] as FatherOfWolves))

        else
            Log.d("AddTurn","role not found")
    }
}