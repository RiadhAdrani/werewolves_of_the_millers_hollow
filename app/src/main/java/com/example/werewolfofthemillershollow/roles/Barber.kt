package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.util.App
import com.example.werewolfofthemillershollow.util.Icons
import com.example.werewolfofthemillershollow.util.Ability

/**
 * A simple barber that can transform into a hunter when in danger.
 * Can choose a player to kill, but dies if the chosen one is not a wolf
 * @see Role
 * @param context context in which the class object is created
 */
class Barber(context: Context) : Role() {

    var givenSign : Boolean = false

    init {
        name = context.getString(App.BARBER_NAME)
        description = context.getString(App.BARBER_DESCRIPTION)
        team = App.BARBER_TEAM
        icon = App.BARBER_ICON

        val primary = object : Ability.Specification{

            override fun use(self: Role, role: Role, list : ArrayList<Role>): Boolean {

                role.kill(list)

                if (!role.isWolf() && !isKilled){
                    this@Barber.kill(list)
                    debug()
                }


                return true

            }

            override fun isUsable(): Boolean {
                return isKilled
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return targetRole != self
            }

        }

        primaryAbility = Ability(R.string.barber_ability, primary, App.ABILITY_ONCE, App.TARGET_SINGLE, Icons.hunterKill)

    }

    override fun canPlay(round: Int): Boolean {
        return (isKilled || !givenSign)
    }

    override fun new(context: Context, name: String, role: Role?): Barber {
        val output = Barber(context)

        output.debug(tag = "servant")

        output.player = name

        output.debug(tag = "servant")

        if (role != null){
            output.copyStatusEffects(role)

        }

        output.debug(tag = "servant")
        return output
    }

    override fun isUnique(): Boolean {
        return true
    }
}