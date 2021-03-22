package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.Ability

/**
 * A mighty Knight : Can use another player as a shield in case of receiving a fatal hit,
 * so the chosen player will receive the hit. Can't use his ability more than once.
 * @see Role
 * @param context context in which the class object is created
 */
class Knight(context: Context) : Role() {

    init {
        name = context.getString(App.KNIGHT_NAME)
        description = context.getString(App.KNIGHT_DESCRIPTION)
        team = App.KNIGHT_TEAM
        icon = App.KNIGHT_ICON

        val primary = object : Ability.Specification{
            override fun use(self: Role, role: Role, list : ArrayList<Role>): Boolean {
                if (!self.isKilled)
                    return false

                self.isKilled = false
                role.kill(list)
                return true
            }

            override fun isUsable(): Boolean {
                return isKilled
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return targetRole != self
            }

        }
        primaryAbility = Ability(R.string.knight_ability, primary, App.ABILITY_ONCE, App.TARGET_SINGLE, Icons.knightKill)
    }

    override fun canPlay(round: Int): Boolean {
        return true
    }


    override fun new(context: Context, name: String, role: Role?): Role {
        val output = Knight(context)

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