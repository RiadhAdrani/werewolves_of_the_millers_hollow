package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * Guardian : Sided with the village,
 * he has the ability to protect a chosen player every night (round).
 * Can't protect the same player twice in a row
 * @see Role
 * @see App.GUARDIAN_NAME
 * @see App.GUARDIAN_DESCRIPTION
 * @see App.GUARDIAN_TEAM
 * @see App.GUARDIAN_CAN_PRIMARY
 * @See App.GUARDIAN_PRIMARY_POWER
 * @see App.GUARDIAN_CAN_SECONDARY
 * @see App.GUARDIAN_SECONDARY_POWER
 * @param context context in which the class object is created
 */
class Guardian(context: Context) : Role() {

    init {
        setName(context.getString(App.GUARDIAN_NAME))
        setDescription(context.getString(App.GUARDIAN_DESCRIPTION))
        setTeam(App.GUARDIAN_TEAM)
        setCanUsePrimary(App.GUARDIAN_CAN_PRIMARY)
        setCanUseSecondary(App.GUARDIAN_CAN_SECONDARY)
        setPrimaryAbilityPower(App.GUARDIAN_PRIMARY_POWER)
        setSecondaryAbilityPower(App.GUARDIAN_SECONDARY_POWER)
    }

    /**
     * Protect a chosen player from the attacks of the wolves
     * @param role player to protect
     * @see App.GUARDIAN_PRIMARY_POWER
     * @see Werewolf
     * @see FatherOfWolves
     * @return true (always)
     */
    override fun primaryAbility(role: Role): Boolean {
        role.setIsGuarded(true)
        return true
    }

    /**
     * Not Available
     * @see App.GUARDIAN_CAN_SECONDARY
     */
    override fun secondaryAbility(role: Role): Boolean {
        return false
    }

    override fun onDeath(role: Role): Boolean {
        return true
    }

    override fun canPlay(round: Int): Boolean {
        return true
    }

    override fun isATarget(role: Role): Boolean {
        return role.getWasGuarded() == true
    }

    override fun isUnique(): Boolean {
        return true
    }
}