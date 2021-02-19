package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.Ability

/**
 * The captainRef of the village :
 * has the ability to assign who will start talking first in the discussion
 * @see Role
 * @param context context in which the class object is created
 */
class Captain(context: Context) : Role() {

    companion object{

        /**
         * Choose a player to talk first
         * @param role chosen player
         */
        fun chooseTalker(role : Role){
            role.isTalking = true
        }

        /**
         * When the current captainRef dies,
         * he should choose another role to
         * be the new captainRef
         * @param role player to be new captainRef
         */
        fun newCaptain(role : Role){
            role.isCaptain = true
        }

        /**
         * Return the number of targets that could be affected by the captainRef.
         */
        fun getCaptainTargets(): Int = App.TARGET_SINGLE

        /**
         * Return the targets that could be chosen by the current captainRef to be a the new captainRef.
         * @param list list of alive players.
         */
        fun newCaptainTargets(list : ArrayList<Role>): ArrayList<Role>{

            val output = ArrayList<Role>()

            for (role : Role in list){
                if (!role.isCaptain)
                    output.add(role)
            }

            return output

        }

        /**
         * return the list of player that could be targeted.
         */
        fun targets(list : ArrayList<Role>): ArrayList<Role>{
            return list
        }

        /**
         * Seek and find the current captainRef in the list of players
         * @param list list of players
         * @return return the current captainRef
         */
        fun findCaptain(list : ArrayList<Role>): Role?{

            for (role : Role in list){
                if (role.isCaptain)
                    return role
            }
            return null
        }

    }

    init {
        name = context.getString(App.CAPTAIN_NAME)
        description = context.getString(App.CAPTAIN_DESCRIPTION)
        team = App.CAPTAIN_TEAM
        isCaptain = true
        icon = App.CAPTAIN_ICON

        val primary = object : Ability.Specification{

            override fun use(self: Role, role: Role): Boolean {
                chooseTalker(role)
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return true
            }
        }

        primaryAbility = Ability(primary,App.ABILITY_INFINITE, App.TARGET_SINGLE, Icons.captainChoose)

    }

    override fun canPlay(round: Int): Boolean {
        return true
    }


    override fun new(context: Context, name: String, role: Role?): Role {
        val output = Captain(context)

        output.player = name

        if (role != null){
            output.copyStatusEffects(role)
        }

        return output
    }

    override fun isUnique(): Boolean {
        return true
    }

}