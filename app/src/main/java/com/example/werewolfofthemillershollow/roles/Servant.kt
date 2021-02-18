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
        name = context.getString(App.SERVANT_NAME)
        description = context.getString(App.SERVANT_DESCRIPTION)
        team = App.SERVANT_TEAM
        setHasPrimary(App.SERVANT_CAN_PRIMARY)
        setHasSecondary(App.SERVANT_CAN_SECONDARY)
        setPrimaryType(App.SERVANT_PRIMARY_POWER)
        setSecondaryType(App.SERVANT_SECONDARY_POWER)
        icon = App.SERVANT_ICON
        setPrimaryIcon(App.SERVANT_PRIMARY_ICON)
        setPrimaryTargets(App.TARGET_SINGLE)
    }

    private var target : Role? = null

    fun getTarget() : Role? = target

    override fun new(context: Context, name: String, role: Role?): Role {
        val output = Servant(context)

        output.player = name

        if (role != null){
            output.copyStatusEffects(role)
        }

        return output
    }

    /**
     * Choose a player to receive his power
     * upon his death
     * @param role chosen player
     * @return true (always)
     */
    override fun primaryAbility(role: Role): Boolean {
        role.isServed = true
        target = role
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

    override fun isATargetPrimary(role: Role): Boolean {
        return role != this
    }

    override fun isUnique(): Boolean {
        return true
    }
}