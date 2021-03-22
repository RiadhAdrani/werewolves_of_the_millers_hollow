package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.R
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

    }

    init {
        name = context.getString(App.CAPTAIN_NAME)
        description = context.getString(App.CAPTAIN_DESCRIPTION)
        team = App.CAPTAIN_TEAM
        isCaptain = true
        icon = App.CAPTAIN_ICON

        val primary = object : Ability.Specification{

            override fun use(self: Role, role: Role, list: ArrayList<Role>): Boolean {
                chooseTalker(role)
                return true
            }

            override fun isUsable(): Boolean {
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return true
            }
        }

        primaryAbility = Ability(
            R.string.captain_ability_talk,
            primary,
            App.ABILITY_INFINITE,
            App.TARGET_SINGLE,
            Icons.captainDiscuss
        )

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