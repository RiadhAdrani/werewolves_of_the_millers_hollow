package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.roles.Werewolf
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons

class WolfpackTurn(role : Werewolf, var activity: GameActivity) : Turn<Werewolf>(activity) {

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

        val team = ArrayList<Role>()
        for (role : Role in list!!){
            if (role.isWolf() || role.isInfected || role.team == App.WOLF_TEAM){
                team.add(role)
            }
        }

        return App.listToString(team, activity)

    }

    override fun usePrimary(target: Role): Boolean {

        val boolean = Werewolf.wolfPackPower(role = target)

        if (boolean)
            activity.wolfTargets.add(target)

        return boolean
    }

    override fun getTargetsPrimary(list: ArrayList<Role>): ArrayList<Role> {
        return list
    }

    override fun useSecondary(target: Role): Boolean {
        return false
    }

    override fun getHasPrimary(): Boolean {
        return true
    }

    override fun addTurn(output: ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean {

        for (role : Role in list){
            if (role.isWolf()){
                output.add(WolfpackTurn(Werewolf(context), activity))
                return true
            }
        }

        Log.d("AddTurn","no wolf was found")
        return false
    }

    override fun getRoleToDisplay(context: Context?, list: ArrayList<Role>?): String {
        return context?.getString(R.string.wolfpack) + " (${Werewolf.getWolfPack(list!!).size})"
    }

    override fun getPrimaryTargets(): Int {
        return Werewolf.getPackTargets()
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        return false
    }

    override fun servant(activity: GameActivity): Int {
        return -1
    }
}