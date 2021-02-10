package com.example.werewolfofthemillershollow.roles

import android.content.Context
import com.example.werewolfofthemillershollow.settings.App

/**
 * The captainRef of the village :
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
         * When the current captainRef dies,
         * he should choose another role to
         * be the new captainRef
         * @param role player to be new captainRef
         */
        fun newCaptain(role : Role){
            role.setIsCaptain(true)
        }

        /**
         * Return the number of targets that could be affected by the captainRef.
         */
        fun getCaptainTargets(): Int = App.TARGET_SINGLE

        /**
         * Return the targets that could be chosen by the current captainRef to be a the new captainRef.
         * @param list list of alive players.
         */
        fun newCaptainTargets(list : ArrayList<Role>): ArrayList<Role>{

            val output = ArrayList<Role>()

            for (role : Role in list){
                if (!role.getIsCaptain()!!)
                    output.add(role)
            }

            return output

        }

        /**
         * return the list of player that could be targeted.
         */
        fun targets(list : ArrayList<Role>): ArrayList<Role>{
            return list
        }

        /**
         * Seek and find the current captainRef in the list of players
         * @param list list of players
         * @return return the current captainRef
         */
        fun findCaptain(list : ArrayList<Role>): Role?{

            for (role : Role in list){
                if (role.getIsCaptain()!!)
                    return role
            }
            return null
        }

    }

    init {
        setName(context.getString(App.CAPTAIN_NAME))
        setDescription(context.getString(App.CAPTAIN_DESCRIPTION))
        setTeam(App.CAPTAIN_TEAM)
        setIsCaptain(true)
        setHasPrimary(App.CAPTAIN_CAN_PRIMARY)
        setHasSecondary(App.CAPTAIN_CAN_SECONDARY)
        setPrimaryType(App.CAPTAIN_PRIMARY_POWER)
        setSecondaryType(App.CAPTAIN_SECONDARY_POWER)
        setIcon(App.CAPTAIN_ICON)
        setPrimaryIcon(App.CAPTAIN_PRIMARY_ICON)
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

    override fun isATargetPrimary(role: Role): Boolean {
        return true
    }

    override fun new(context: Context, name: String, role: Role?): Role {
        val output = Captain(context)
        output.setPlayer(name)

        if (role != null){
            output.copyStatusEffects(role)
        }

        return output
    }

    override fun isUnique(): Boolean {
        return true
    }

}