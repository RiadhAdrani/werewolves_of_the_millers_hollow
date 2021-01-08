package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * The captain of the village :
 * has the ability to assign who will start talking first in the discussion
 * @see Role
 * @see App.CAPTAIN_NAME
 * @see App.CAPTAIN_DESCRIPTION
 * @see App.CAPTAIN_TEAM
 * @see App.CAPTAIN_CAN_PRIMARY
 * @See App.CAPTAIN_PRIMARY_POWER
 * @see App.CAPTAIN_CAN_SECONDARY
 * @see App.CAPTAIN_SECONDARY_POWER
 * @param context context in which the class object is created
 */
class Captain(context: Context) : Role() {

    companion object{

        /**
         * Choose a player to talk first
         * @param role chosen player
         */
        fun chooseTalker(role : Role){
            role.setIsTalking(true)
        }

        /**
         * When the current captain dies,
         * he should choose another role to
         * be the new captain
         * @param role player to be new captain
         */
        fun newCaptain(role : Role){
            role.setIsCaptain(true)
        }


    }

    init {
        setName(context.getString(App.CAPTAIN_NAME))
        setDescription(context.getString(App.CAPTAIN_DESCRIPTION))
        setTeam(App.CAPTAIN_TEAM)
        setCanUsePrimary(App.CAPTAIN_CAN_PRIMARY)
        setCanUseSecondary(App.CAPTAIN_CAN_SECONDARY)
        setPrimaryAbilityPower(App.CAPTAIN_PRIMARY_POWER)
        setSecondaryAbilityPower(App.CAPTAIN_SECONDARY_POWER)
    }

    /**
     * Choose a player to talk first
     * @param role chosen player
     */
    override fun primaryAbility(role: Role): Boolean {
        chooseTalker(role)
        return true
    }

    override fun secondaryAbility(role: Role): Boolean {
        return false
    }

    override fun onDeath(role: Role): Boolean {
        newCaptain(role)
        return true
    }

    override fun canPlay(round: Int): Boolean {
        return true
    }

    override fun isATarget(role: Role): Boolean {
        return true
    }
}