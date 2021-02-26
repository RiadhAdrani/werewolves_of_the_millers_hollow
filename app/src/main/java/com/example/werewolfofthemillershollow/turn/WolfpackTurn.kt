package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.roles.Werewolf
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.Ability
import com.example.werewolfofthemillershollow.utility.Event

class WolfpackTurn(role : Werewolf, var activity: GameActivity) : Turn<Werewolf>(activity) {

    private var primaryAbility : Ability

    init {
        setRole(role)

        val primary = object : Ability.Specification{
            override fun use(self: Role, role: Role, list: ArrayList<Role>): Boolean {
                return Werewolf.wolfPackPower(role = role, list)
            }

            override fun isUsable(): Boolean {
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return true
            }
        }

        primaryAbility = Ability(primary, App.ABILITY_INFINITE, App.TARGET_SINGLE, Icons.packAttack)

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

    override fun getPlayer(list: ArrayList<Role>?): String {

        val team = ArrayList<Role>()
        for (role : Role in list!!){
            if (role.isWolf() || role.isInfected || role.team == App.WOLF_TEAM){
                team.add(role)
            }
        }

        return App.listToString(team, activity)

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

    override fun getPrimaryAbility(): Ability {
        return primaryAbility
    }

    override fun shouldUsePower(gameActivity: GameActivity): Boolean {
        return false
    }

    override fun servant(activity: GameActivity, events: ArrayList<Event>): Int {
        return -1
    }
}