package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Barber
import com.example.werewolfofthemillershollow.roles.Knight
import com.example.werewolfofthemillershollow.roles.Role

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

    override fun usePrimary(singleTarget: Role?, multipleTargets: ArrayList<Role>?): Boolean {
        return getRole().usePrimaryAbility(role = singleTarget!!)
    }

    override fun useSecondary(singleTarget: Role?, multipleTargets: ArrayList<Role>?): Boolean {
        return false
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context) {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1)
            output.add(KnightTurn(list[index] as Knight))

        else
            Log.d("AddTurn","Knight role not found")
    }
}