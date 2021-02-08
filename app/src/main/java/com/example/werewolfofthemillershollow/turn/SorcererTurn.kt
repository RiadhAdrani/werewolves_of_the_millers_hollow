package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.R
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
                return role.getName()+ " " + context.getString(R.string.was_killed) + ".. " + output
            }
        }

        return context.getString(R.string.nobody_was_killed) + " " + output
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return getRole().canPlay(round)
    }

    override fun usePrimary(target: Role): Boolean {
        return getRole().usePrimaryAbility(role = target)
    }

    override fun canSecondary(): Boolean {
        return getRole().getHasSecondary()!!
    }

    override fun useSecondary(target: Role): Boolean {
        Log.d("Role","using secondary")
        return getRole().useSecondaryAbility(role = target)
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        val index = Role.roleInList(role = getRole(), list = list)

        if (index != -1) {
            output.add(SorcererTurn(list[index] as Sorcerer))
            return true
        }

        else
            Log.d("AddTurn","role not found")
        return false
    }
}