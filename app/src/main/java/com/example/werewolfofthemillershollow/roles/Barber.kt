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
        name = context.getString(App.BARBER_NAME)
        description = context.getString(App.BARBER_DESCRIPTION)
        team = App.BARBER_TEAM
        setHasPrimary(App.BARBER_CAN_PRIMARY)
        setHasSecondary(App.BARBER_CAN_SECONDARY)
        setPrimaryType(App.BARBER_PRIMARY_POWER)
        setSecondaryType(App.BARBER_SECONDARY_POWER)
        icon = App.BARBER_ICON
        setPrimaryIcon(App.BARBER_PRIMARY_ICON)
        setPrimaryTargets(App.TARGET_SINGLE)
    }

    override fun primaryAbility(role: Role): Boolean {
        role.isKilled = true

        if (role::class.java != Werewolf::class.java)
            isKilled = true

        return true
    }

    override fun secondaryAbility(role: Role): Boolean {
        return false
    }

    override fun onDeath(role: Role): Boolean {
        return primaryAbility(role = role)
    }

    override fun canPlay(round: Int): Boolean {
        return (isKilled || round == 1)
    }

    override fun isATargetPrimary(role: Role): Boolean {
        return role != this
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