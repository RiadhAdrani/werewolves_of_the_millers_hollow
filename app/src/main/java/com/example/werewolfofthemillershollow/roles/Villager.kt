package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * A simple villager :
 * has no special ability expect his intelligence.
 * @see Role
 * @see App.VILLAGER_NAME
 * @see App.VILLAGER_DESCRIPTION
 * @see App.VILLAGER_TEAM
 * @see App.VILLAGER_CAN_PRIMARY
 * @See App.VILLAGER_PRIMARY_POWER
 * @see App.VILLAGER_CAN_SECONDARY
 * @see App.VILLAGER_SECONDARY_POWER
 * @param context context in which the class object is created
 */
class Villager(context: Context) : Role() {

    init {
        setName(context.getString(App.VILLAGER_NAME))
        setDescription(context.getString(App.VILLAGER_DESCRIPTION))
        setTeam(App.VILLAGER_TEAM)
        setHasPrimary(App.VILLAGER_CAN_PRIMARY)
        setHasSecondary(App.VILLAGER_CAN_SECONDARY)
        setPrimaryType(App.VILLAGER_PRIMARY_POWER)
        setSecondaryType(App.VILLAGER_SECONDARY_POWER)
        setIcon(App.VILLAGER_ICON)
    }

    override fun primaryAbility(role: Role): Boolean {
        return false
    }

    override fun secondaryAbility(role: Role): Boolean {
        return false
    }

    override fun onDeath(role: Role): Boolean {
        return true
    }

    override fun canPlay(round: Int): Boolean {
        return false
    }

    override fun isATargetPrimary(role: Role): Boolean {
        return false
    }
}