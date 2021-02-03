package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Barber
import com.example.werewolfofthemillershollow.roles.Role

class BarberTurn(role : Barber) : Turn<Barber>() {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        if (getRole().getIsKilled()!!)
            return context.getString(R.string.barber_instruction_killed)

        return context.getString(R.string.barber_instruction)
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
            output.add(BarberTurn(list[index] as Barber))

        else
            Log.d("AddTurn","Barber role not found")

    }
}