package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * Sorcerer of the village, has two potions (abilities) that could be used once each:
 * The first kill and the second revive.
 * @see Role
 * @see App.SORCERER_NAME
 * @see App.SORCERER_DESCRIPTION
 * @see App.SORCERER_TEAM
 * @see App.SORCERER_CAN_PRIMARY
 * @See App.SORCERER_PRIMARY_POWER
 * @see App.SORCERER_CAN_SECONDARY
 * @see App.SORCERER_SECONDARY_POWER
 * @param context context in which the class object is created
 */
class Sorcerer(context: Context) : Role() {

    init {
        setName(context.getString(App.SORCERER_NAME))
        setDescription(context.getString(App.SORCERER_DESCRIPTION))
        setTeam(App.SORCERER_TEAM)
        setCanUsePrimary(App.SORCERER_CAN_PRIMARY)
        setCanUseSecondary(App.SORCERER_CAN_SECONDARY)
        setPrimaryAbilityPower(App.SORCERER_PRIMARY_POWER)
        setSecondaryAbilityPower(App.SORCERER_SECONDARY_POWER)
    }

    /**
     * Kill a chosen player.
     * @param role to kill
     * @return true
     */
    override fun primaryAbility(role: Role): Boolean {
        role.setIsKilled(true)
        return true
    }

    /**
     * Heal a recently killed player
     * @param role player to revive
     * @return true
     */
    override fun secondaryAbility(role: Role): Boolean {

        role.setIsKilled(false)
        role.setIsAlive(true)

        return true
    }

    override fun onDeath(role: Role): Boolean {
        return true
    }

    override fun canPlay(round: Int): Boolean {
        return true
    }

    override fun isATarget(role: Role): Boolean {
        return role.getIsKilled() == false
    }

    override fun isUnique(): Boolean {
        return true
    }
}