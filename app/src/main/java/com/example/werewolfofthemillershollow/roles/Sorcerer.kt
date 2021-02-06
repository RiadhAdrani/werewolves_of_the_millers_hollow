package com.example.werewolfofthemillershollow.roles

import android.content.Context
import android.util.Log
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
        setHasPrimary(App.SORCERER_CAN_PRIMARY)
        setHasSecondary(App.SORCERER_CAN_SECONDARY)
        setPrimaryType(App.SORCERER_PRIMARY_POWER)
        setPrimaryTargets(App.SORCERER_PRIMARY_TARGET)
        setSecondaryType(App.SORCERER_SECONDARY_POWER)
        setSecondaryTargets(App.SORCERER_SECONDARY_TARGET)
        setIcon(App.SORCERER_ICON)
        setPrimaryIcon(App.SORCERER_PRIMARY_ICON)
        setSecondaryIcon(App.SORCERER_SECONDARY_ICON)
        setPrimaryTargets(App.TARGET_SINGLE)
        setSecondaryTargets(App.TARGET_SINGLE)
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
        Log.d("SorcererClass","${role.getName()} is healed")
        return true
    }

    override fun onDeath(role: Role): Boolean {
        return true
    }

    override fun canPlay(round: Int): Boolean {
        return true
    }

    override fun isATargetPrimary(role: Role): Boolean {
        return role.getIsKilled() == false
    }

    override fun isUnique(): Boolean {
        return true
    }

    override fun isATargetSecondary(role: Role): Boolean {
        return role.getIsKilled()!!
    }
}