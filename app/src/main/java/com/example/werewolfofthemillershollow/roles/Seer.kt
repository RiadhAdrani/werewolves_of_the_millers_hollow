package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.util.App
import com.example.werewolfofthemillershollow.util.Icons
import com.example.werewolfofthemillershollow.util.Ability

/**
 * Seer : has the ability to know the real role of players.
 * @see Role
 * @param context context in which the class object is created
 */
class Seer(context: Context) : Role() {

    private var none : String = context.getString(R.string.none)
    /**
     * The currently seen role name by the seer
     */
    private var seenRole : String = none

    /**
     * getter for seen role
     * @return seenRole
     */
    fun getSeenRole() : String {
        return seenRole
    }


    init {
        name = context.getString(App.SEER_NAME)
        description = context.getString(App.SEER_DESCRIPTION)
        team = App.SEER_TEAM
        icon = App.SEER_ICON

        val primary = object : Ability.Specification{
            override fun use(self: Role, role: Role, list: ArrayList<Role>): Boolean {
                seenRole = role.name
                return true
            }

            override fun isUsable(): Boolean {
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return true
            }
        }
        primaryAbility = Ability(R.string.seer_ability, primary, App.ABILITY_INFINITE, App.TARGET_SINGLE, Icons.seerSee)

    }

    override fun canPlay(round: Int): Boolean {
        return true
    }

    override fun new(context: Context, name: String, role: Role?): Role {
        val output = Seer(context)

        output.player = name

        if (role != null){
            output.copyStatusEffects(role)
        }

        return output
    }

    override fun isUnique(): Boolean {
        return true
    }

    override fun resetStatusEffects() {
        seenRole = none
        super.resetStatusEffects()
    }
}