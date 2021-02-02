package com.example.werewolfofthemillershollow.turn

import android.content.Context
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

    override fun usePrimary(singleTarget: Role?, multipleTargets: ArrayList<Role>?): Boolean {
        TODO("Not yet implemented")
    }

    override fun useSecondary(singleTarget: Role?, multipleTargets: ArrayList<Role>?): Boolean {
        TODO("Not yet implemented")
    }
}