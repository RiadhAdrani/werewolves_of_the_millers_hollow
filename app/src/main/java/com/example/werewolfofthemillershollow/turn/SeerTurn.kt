package com.example.werewolfofthemillershollow.turn

import android.content.Context
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.roles.Seer

class SeerTurn(role : Seer) : Turn<Seer>() {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        return context.getString(R.string.seer_instruction)
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