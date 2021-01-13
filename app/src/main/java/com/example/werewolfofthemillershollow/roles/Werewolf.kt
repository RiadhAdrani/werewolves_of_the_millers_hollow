package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * Werewolf : Sided with the wolf pack.
 * No remarkable ability on his own
 * @see Role
 * @see App.WOLF_NAME
 * @see App.WOLF_DESCRIPTION
 * @see App.WOLF_TEAM
 * @see App.WOLF_CAN_PRIMARY
 * @See App.WOLF_PRIMARY_POWER
 * @see App.WOLF_CAN_SECONDARY
 * @see App.WOLF_SECONDARY_POWER
 * @param context context in which the class object is created
 */
class Werewolf(context: Context) : Role() {

    companion object{

        /**
         * Power of the wolf pack : allow the pack to choose a player to kill, even one of them ...
         * @param role player to kill
         */
        fun wolfPackPower(role : Role){
            role.setIsKilled(true)
        }

    }

    init {
        setName(context.getString(App.WOLF_NAME))
        setDescription(context.getString(App.WOLF_DESCRIPTION))
        setTeam(App.WOLF_TEAM)
        setCanUsePrimary(App.WOLF_CAN_PRIMARY)
        setCanUseSecondary(App.WOLF_CAN_SECONDARY)
        setPrimaryAbilityPower(App.WOLF_PRIMARY_POWER)
        setSecondaryAbilityPower(App.WOLF_SECONDARY_POWER)
    }

    /**
     * Not Available
     * @see App.WOLF_CAN_PRIMARY
     */
    override fun primaryAbility(role: Role): Boolean {
        return false
    }

    /**
     * Not Available
     * @see App.WOLF_CAN_SECONDARY
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
        return true
    }


    override fun isWolf() : Boolean{
        return true
    }
}