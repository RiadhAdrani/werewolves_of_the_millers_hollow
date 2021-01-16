package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * Servant : is sided with the village.
 * has the ability to choose a player and take his power upon his death
 * @see Role
 * @see App.SERVANT_NAME
 * @see App.SERVANT_DESCRIPTION
 * @see App.SERVANT_TEAM
 * @see App.SERVANT_CAN_PRIMARY
 * @See App.SERVANT_PRIMARY_POWER
 * @see App.SERVANT_CAN_SECONDARY
 * @see App.SERVANT_SECONDARY_POWER
 * @param context context in which the class object is created
 */
class Servant(context: Context) : Role() {

    init {
        setName(context.getString(App.SERVANT_NAME))
        setDescription(context.getString(App.SERVANT_DESCRIPTION))
        setTeam(App.SERVANT_TEAM)
        setCanUsePrimary(App.SERVANT_CAN_PRIMARY)
        setCanUseSecondary(App.SERVANT_CAN_SECONDARY)
        setPrimaryAbilityPower(App.SERVANT_PRIMARY_POWER)
        setSecondaryAbilityPower(App.SERVANT_SECONDARY_POWER)
    }

    /**
     * Choose a player to receive his power
     * upon his death
     * @param role chosen player
     * @return true (always)ù
     * @see canUsePrimary
     * @see primaryAbilityPower
     */
    override fun primaryAbility(role: Role): Boolean {
        role.setIsServed(true)
        return true
    }

    /**
     * Not Available
     * @see App.SERVANT_CAN_SECONDARY
     */
    override fun secondaryAbility(role: Role): Boolean {
        return false
    }

    /**
     * executes before death
     * @return true (always)
     */
    override fun onDeath(role: Role): Boolean {
        return true
    }

    override fun canPlay(round: Int): Boolean {
        return round == 1
    }

    override fun isATarget(role: Role): Boolean {
        return role == this
    }

    override fun isUnique(): Boolean {
        return true
    }
}