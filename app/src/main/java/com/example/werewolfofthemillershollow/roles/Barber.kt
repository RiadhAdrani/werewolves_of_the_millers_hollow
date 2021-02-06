package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * A simple barber that can transform into a hunter when in danger.
 * Can choose a player to kill, but dies if the chosen one is not a wolf
 * @see Role
 * @see App.BARBER_NAME
 * @see App.BARBER_DESCRIPTION
 * @see App.BARBER_TEAM
 * @see App.BARBER_CAN_PRIMARY
 * @See App.BARBER_PRIMARY_POWER
 * @see App.BARBER_CAN_SECONDARY
 * @see App.BARBER_SECONDARY_POWER
 * @param context context in which the class object is created
 */
class Barber(context: Context) : Role() {

    init {
        setName(context.getString(App.BARBER_NAME))
        setDescription(context.getString(App.BARBER_DESCRIPTION))
        setTeam(App.BARBER_TEAM)
        setHasPrimary(App.BARBER_CAN_PRIMARY)
        setHasSecondary(App.BARBER_CAN_SECONDARY)
        setPrimaryType(App.BARBER_PRIMARY_POWER)
        setSecondaryType(App.BARBER_SECONDARY_POWER)
        setIcon(App.BARBER_ICON)
        setPrimaryIcon(App.BARBER_PRIMARY_ICON)
        setPrimaryTargets(App.TARGET_SINGLE)
    }

    override fun primaryAbility(role: Role): Boolean {
        role.setIsKilled(true)

        if (role::class.java != Werewolf::class.java)
            setIsKilled(true)

        return true
    }

    override fun secondaryAbility(role: Role): Boolean {
        return false
    }

    override fun onDeath(role: Role): Boolean {
        return primaryAbility(role = role)
    }

    override fun canPlay(round: Int): Boolean {
        return ( getIsKilled() == true || round == 1)
    }

    override fun isATargetPrimary(role: Role): Boolean {
        return role != this
    }

    override fun isUnique(): Boolean {
        return true
    }
}