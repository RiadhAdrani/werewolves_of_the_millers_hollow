package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.Ability

/**
 * Guardian : Sided with the village,
 * he has the ability to protect a chosen player every night (round).
 * Can't protect the same player twice in a row
 * @see Role
 * @param context context in which the class object is created
 */
class Guardian(context: Context) : Role() {

    init {
        name = context.getString(App.GUARDIAN_NAME)
        description = context.getString(App.GUARDIAN_DESCRIPTION)
        team = App.GUARDIAN_TEAM
        icon = App.GUARDIAN_ICON

        val primary = object : Ability.Specification{
            override fun use(self: Role, role: Role): Boolean {
                role.isGuarded = true
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return !targetRole.wasGuarded
            }
        }

        primaryAbility = Ability(primary,App.ABILITY_INFINITE, App.TARGET_SINGLE, Icons.guardianProtect)
    }


    override fun canPlay(round: Int): Boolean {
        return true
    }


    override fun new(context: Context, name: String, role: Role?): Role {
        val output = Guardian(context)

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