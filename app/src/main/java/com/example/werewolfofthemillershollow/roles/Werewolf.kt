package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons

/**
 * Werewolf : Sided with the wolf pack.
 * No remarkable ability on his own
 * @see Role
 * @param context context in which the class object is created
 */
open class Werewolf(context: Context) : Role() {

    companion object{

        /**
         * Power of the wolf pack : allow the pack to choose a player to kill, even one of them ...
         * @param role player to kill
         */
        fun wolfPackPower(role : Role) : Boolean{
            if (!role.isGuarded) {
                role.isKilled = true
                return true
            }

            return false
        }

        /**
         * Returns the list of the current players making the wolfpack
         * @param list list of alive players
         */
        fun getWolfPack(list : ArrayList<Role>): ArrayList<Role>{

            val output = ArrayList<Role>()
            for (role : Role in list){
                if (role.isWolf() || role.isInfected || role.team == App.WOLF_TEAM)
                    if (role.isAlive)
                        output.add((role))
            }

            return output

        }

        /**
         * Returns the icon of the pack.
         */
        fun getPackIcon(): Int{
            return Icons.pack
        }

        /**
         * Return the icon representing the ability of the pack.
         */
        fun getPackPowerIcon(): Int{
            return Icons.packAttack
        }

        /**
         * returns how many players could be targeted by the wolfpack
         */
        fun getPackTargets(): Int = App.TARGET_SINGLE

    }

    init {
        name = context.getString(App.WOLF_NAME)
        description = context.getString(App.WOLF_DESCRIPTION)
        team = App.WOLF_TEAM
        icon = App.WOLF_ICON
    }

    override fun canPlay(round: Int): Boolean {
        return true
    }


    override fun new(context: Context, name: String, role: Role?): Role? {
        val output = Werewolf(context)
        output.player = name

        if (role != null){
            output.copyStatusEffects(role)
        }

        return output
    }

    override fun isWolf(): Boolean {
        return true
    }
}