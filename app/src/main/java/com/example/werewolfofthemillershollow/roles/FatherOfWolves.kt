package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * The Father of the Werewolves : Sided the wolf pack.
 * Has the ability to infect a non-wolf player once in the game.
 * @see Role
 * @see App.INFECT_NAME
 * @see App.INFECT_DESCRIPTION
 * @see App.INFECT_TEAM
 * @see App.INFECT_CAN_PRIMARY
 * @See App.INFECT_PRIMARY_POWER
 * @see App.INFECT_CAN_SECONDARY
 * @see App.INFECT_SECONDARY_POWER
 * @param context context in which the class object is created
 */
class FatherOfWolves(context: Context) :Role() {

    init {
        setName(context.getString(App.INFECT_NAME))
        setDescription(context.getString(App.INFECT_DESCRIPTION))
        setTeam(App.INFECT_TEAM)
        setCanUsePrimary(App.INFECT_CAN_PRIMARY)
        setCanUseSecondary(App.INFECT_CAN_SECONDARY)
        setPrimaryAbilityPower(App.INFECT_PRIMARY_POWER)
        setSecondaryAbilityPower(App.INFECT_SECONDARY_POWER)
    }

    /**
     * Infect a non-wolf chosen player
     * If he is not protected, the target joins the wolf pack : return false
     * If he is protected, the operation fails : return false
     * @return success -> true | fail -> fail
     * @param role player to be infected
     */
    override fun primaryAbility(role: Role): Boolean {

        return if (role.getIsGuarded() == false){
            role.setIsInfected(true)
            setCanUsePrimary(false)
            true
        } else {
            false
        }
    }

    /**
     * Not Available
     * @see App.INFECT_CAN_SECONDARY
     */
    override fun secondaryAbility(role: Role): Boolean {
        return false
    }

    override fun onDeath(role: Role): Boolean {
        return true
    }

    override fun canPlay(round: Int): Boolean {
        return round > 1
    }

    override fun isATarget(role: Role): Boolean {
        return role.getIsKilled() == true
    }

    override fun isUnique(): Boolean {
        return true
    }
}