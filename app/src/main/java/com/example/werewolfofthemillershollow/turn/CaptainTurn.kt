package com.example.werewolfofthemillershollow.turn

import android.content.Context
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Captain
import com.example.werewolfofthemillershollow.roles.Role

class CaptainTurn(role : Captain) : Turn<Captain>() {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        if (getRole().getIsKilled()!!)
            return context.getString(R.string.captain_instruction_inherit)

        return context.getString(R.string.captain_instruction)
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
}