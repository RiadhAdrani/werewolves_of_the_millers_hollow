package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.Ability

/**
 * Sorcerer of the village, has two potions (abilities) that could be used once each:
 * The first kill and the second revive.
 * @see Role
 * @param context context in which the class object is created
 */
class Sorcerer(context: Context) : Role() {

    init {
        name = context.getString(App.SORCERER_NAME)
        description = context.getString(App.SORCERER_DESCRIPTION)
        team = App.SORCERER_TEAM
        icon = App.SORCERER_ICON

        val primary = object : Ability.Specification{
            override fun use(self: Role, role: Role, list: ArrayList<Role>): Boolean {
                role.kill(list)
                role.isKilledBySorcerer = true
                return true
            }

            override fun isUsable(): Boolean {
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return !targetRole.isKilled
            }
        }

        primaryAbility = Ability(primary, App.ABILITY_ONCE, App.TARGET_SINGLE, Icons.sorcererKill)

        val secondary = object : Ability.Specification{
            override fun use(self: Role, role: Role, list : ArrayList<Role>): Boolean {
                role.isKilled = false
                return true
            }

            override fun isUsable(): Boolean {
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return targetRole.isKilled && !targetRole.isKilledBySorcerer
            }
        }
        secondaryAbility = Ability(secondary, App.ABILITY_ONCE, App.TARGET_SINGLE, Icons.sorcererRevive)
    }

    override fun canPlay(round: Int): Boolean {
        return true
    }

    override fun isUnique(): Boolean {
        return true
    }

    override fun isATargetSecondary(role: Role): Boolean {
        return role.isKilled
    }

    override fun new(context: Context, name: String, role: Role?): Role {
        val output = Sorcerer(context)
        output.player = name

        if (role != null){
            output.copyStatusEffects(role)
        }

        return output
    }
}