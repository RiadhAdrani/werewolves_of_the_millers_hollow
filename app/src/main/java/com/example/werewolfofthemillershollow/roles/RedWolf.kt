package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.util.Ability
import com.example.werewolfofthemillershollow.util.App
import com.example.werewolfofthemillershollow.util.Icons

class RedWolf(context : Context) : Role() {

    init {
        name = context.getString(App.RED_WOLF_NAME)
        description = context.getString(App.RED_WOLF_DESCRIPTION)
        team = App.RED_WOLF_TEAM
        icon = App.RED_WOLF_ICON

        val primary = object : Ability.Specification{
            override fun use(self: Role, role: Role, list : ArrayList<Role>): Boolean {

                role.isIntimidated = true
                return false
            }

            override fun isUsable(): Boolean {
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return !targetRole.wasIntimidated && targetRole.team != App.WOLF_TEAM
            }

        }
        primaryAbility = Ability(R.string.red_ability, primary, App.ABILITY_INFINITE, App.TARGET_SINGLE, Icons.wolves)
    }

    override fun new(context: Context, name: String, role: Role?): Role {
        val output = RedWolf(context)

        output.player = name

        if (role != null){
            output.copyStatusEffects(role)
        }

        return output
    }

    override fun canPlay(round: Int): Boolean {
        return true
    }

    override fun isUnique(): Boolean {
        return true
    }
}