package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * A mighty Knight : Can use another player as a shield in case of receiving a fatal hit,
 * so the chosen player will receive the hit. Can't use his ability more than once.
 * @see Role
 * @see App.KNIGHT_NAME
 * @see App.KNIGHT_DESCRIPTION
 * @see App.KNIGHT_TEAM
 * @see App.KNIGHT_CAN_PRIMARY
 * @See App.KNIGHT_PRIMARY_POWER
 * @see App.KNIGHT_CAN_SECONDARY
 * @see App.KNIGHT_SECONDARY_POWER
 * @param context context in which the class object is created
 */
class Knight(context: Context) : Role() {

    init {
        setName(context.getString(App.KNIGHT_NAME))
        setDescription(context.getString(App.KNIGHT_DESCRIPTION))
        setTeam(App.KNIGHT_TEAM)
        setHasPrimary(App.KNIGHT_CAN_PRIMARY)
        setHasSecondary(App.KNIGHT_CAN_SECONDARY)
        setPrimaryType(App.KNIGHT_PRIMARY_POWER)
        setSecondaryType(App.KNIGHT_SECONDARY_POWER)
        setIcon(App.KNIGHT_ICON)
        setPrimaryIcon(App.KNIGHT_PRIMARY_ICON)
        setPrimaryTargets(App.TARGET_SINGLE)
    }

    /**
     * Choose a player to receive the fatal hit.
     * @param role chosen player
     * @return true
     */
    override fun primaryAbility(role: Role): Boolean {
        setIsKilled(false)
        role.setIsKilled(true)
        return true
    }

    override fun secondaryAbility(role: Role): Boolean {
        return false
    }

    override fun onDeath(role: Role): Boolean {
        return true
    }

    override fun canPlay(round: Int): Boolean {
        return true
    }

    override fun isATargetPrimary(role: Role): Boolean {
        return role != this
    }

    override fun new(context: Context, name: String, role: Role?): Role {
        val output = Knight(context)
        output.setPlayer(name)

        if (role != null){
            output.copyStatusEffects(role)
        }

        return output
    }

    override fun isUnique(): Boolean {
        return true
    }
}