package com.example.werewolfofthemillershollow.turn

import android.content.Context
import com.example.werewolfofthemillershollow.R
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
        TODO("Not yet implemented")
    }

    override fun useSecondary(singleTarget: Role?, multipleTargets: ArrayList<Role>?): Boolean {
        TODO("Not yet implemented")
    }
}