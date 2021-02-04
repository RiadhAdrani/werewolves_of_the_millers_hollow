package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.roles.Servant


class ServantTurn(role: Servant) : Turn<Servant>() {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        return context.getString(R.string.servant_instructions)
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

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context) {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1)
            output.add(ServantTurn(list[index] as Servant))

        else
            Log.d("AddTurn","role not found")
    }

}