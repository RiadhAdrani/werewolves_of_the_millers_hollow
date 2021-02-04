package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.roles.Werewolf
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons

class WolfpackTurn(role : Werewolf) : Turn<Werewolf>() {

    init {
        setRole(role)
    }

    override fun getInstructions(context: Context, list: ArrayList<Role>?): String {
        return context.getString(R.string.wolf_instruction)
    }

    override fun canPlay(round: Int, list: ArrayList<Role>?): Boolean {
        return Werewolf.getWolfPack(list!!).size > 0
    }

    override fun getIcon(): Int {
        return Werewolf.getPackIcon()
    }

    override fun getPrimaryIcon(): Int {
        return Werewolf.getPackPowerIcon()
    }

    override fun getSecondaryIcon(): Int {
        return Icons.noAbility
    }

    override fun getPlayer(list: ArrayList<Role>?): String {

        var output = ""
        var index = 0
        for (role : Role in list!!){
            if (role.isWolf() || role.getIsInfected()!! || role.getTeam()!! == App.WOLF_TEAM){
                output += if (index == 0)
                    role.getPlayer()
                else
                    ", " + role.getPlayer()

                index++
            }
        }

        output += "."
        return output

    }

    override fun usePrimary(target: Role): Boolean {
        return Werewolf.wolfPackPower(role = target)
    }

    override fun useSecondary(target: Role): Boolean {
        return false
    }

    override fun getCanUsePrimary(): Boolean {
        return true
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context) {

        for (role : Role in list){
            if (role.isWolf()){
                output.add(WolfpackTurn(Werewolf(context)))
                return
            }
        }

        Log.d("AddTurn","no wolf was found")
    }

    override fun getRoleToDisplay(context: Context?, list: ArrayList<Role>?): String {
        return context?.getString(R.string.wolfpack) + " (${Werewolf.getWolfPack(list!!).size})"
    }

    override fun getPrimaryTargets(): Int {
        return Werewolf.getPackTargets()
    }
}