package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.Ability

/**
 * The Father of the Werewolves : Sided the wolf pack.
 * Has the ability to infect a non-wolf player once in the game.
 * @see Role
 * @param context context in which the class object is created
 */
class FatherOfWolves(context: Context) :Werewolf(context) {

    init {
        name = context.getString(App.INFECT_NAME)
        description = context.getString(App.INFECT_DESCRIPTION)
        team = App.INFECT_TEAM
        icon = App.INFECT_ICON

        val primary = object : Ability.Specification{
            override fun use(self: Role, role: Role, list: ArrayList<Role>): Boolean {
                if (!role.isKilled)
                    return false

                role.isKilled = false
                role.isInfected = true
                role.team = if (role.team == App.VILLAGER_TEAM) App.WOLF_TEAM else role.team
                return true
            }

            override fun isUsable(): Boolean {
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return targetRole.isKilled
            }
        }
        primaryAbility = Ability(primary,App.ABILITY_ONCE, App.TARGET_SINGLE, Icons.fatherInfect)

    }

    override fun canPlay(round: Int): Boolean {
        return round > 1
    }

    override fun new(context: Context, name: String, role: Role?): Role {
        val output = FatherOfWolves(context)
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