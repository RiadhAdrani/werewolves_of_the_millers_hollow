package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * Seer : has the ability
 * @see Role
 * @see App.SEER_NAME
 * @see App.SEER_DESCRIPTION
 * @see App.SEER_TEAM
 * @see App.SEER_CAN_PRIMARY
 * @See App.SEER_PRIMARY_POWER
 * @see App.SEER_CAN_SECONDARY
 * @see App.SEER_SECONDARY_POWER
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

    /**
     * setter for seen role
     * @param roleName new seen role name
     */
    fun setSeenRole(roleName : String?) {
        seenRole = roleName
    }

    init {
        setName(context.getString(App.SEER_NAME))
        setDescription(context.getString(App.SEER_DESCRIPTION))
        setTeam(App.SEER_TEAM)
        setCanUsePrimary(App.SEER_CAN_PRIMARY)
        setCanUseSecondary(App.SEER_CAN_SECONDARY)
        setPrimaryAbilityPower(App.SEER_PRIMARY_POWER)
        setSecondaryAbilityPower(App.SEER_SECONDARY_POWER)
    }

    override fun primaryAbility(role: Role): Boolean {
        seenRole = role.getName()
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

    override fun isATarget(role: Role): Boolean {
        return true
    }

    override fun isUnique(): Boolean {
        return true
    }
}