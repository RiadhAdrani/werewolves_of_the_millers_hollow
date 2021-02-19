package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.Ability

/**
 * Seer : has the ability
 * @see Role
 * @param context context in which the class object is created
 */
class Seer(context: Context) : Role() {

    /**
     * The currently seen role name by the seer
     */
    private var seenRole : String? = "None"

    /**
     * getter for seen role
     * @return seenRole
     */
    fun getSeenRole() : String? {
        return seenRole
    }


    init {
        name = context.getString(App.SEER_NAME)
        description = context.getString(App.SEER_DESCRIPTION)
        team = App.SEER_TEAM
        icon = App.SEER_ICON

        val primary = object : Ability.Specification{
            override fun use(self: Role, role: Role): Boolean {
                seenRole = role.name
                return true
            }

            override fun isTarget(self: Role, targetRole: Role): Boolean {
                return true
            }
        }
        primaryAbility = Ability(primary,App.ABILITY_INFINITE,App.TARGET_SINGLE, Icons.seerSee)

    }

    override fun canPlay(round: Int): Boolean {
        return true
    }

    override fun resetStatusEffects() {
        super.resetStatusEffects()
        seenRole = "None"
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
}