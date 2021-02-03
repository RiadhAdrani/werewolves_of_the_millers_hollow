package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Barber
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.roles.Sorcerer

class SorcererTurn(role : Sorcerer) : Turn<Sorcerer>() {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {

        val output : String = context.getString(R.string.sorcerer_instruction_heal) + context.getString(R.string.sorcerer_instruction_kill)

        for (role : Role in list!!){
            if (role.getIsKilled()!!){
                return role.getName()+ " " + context.getString(R.string.was_killed) + " " + output
            }
        }

        return context.getString(R.string.nobody_was_killed) + " " + output
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun usePrimary(singleTarget: Role?, multipleTargets: ArrayList<Role>?): Boolean {
        return getRole().usePrimaryAbility(role = singleTarget!!)
    }

    override fun useSecondary(singleTarget: Role?, multipleTargets: ArrayList<Role>?): Boolean {
        return getRole().useSecondaryAbility(role = singleTarget!!)
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context) {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1)
            output.add(SorcererTurn(list[index] as Sorcerer))

        else
            Log.d("AddTurn","role not found")
    }
}