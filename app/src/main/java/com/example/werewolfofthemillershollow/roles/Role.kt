package com.example.werewolfofthemillershollow.roles

import com.example.werewolfofthemillershollow.settings.App

/**
 * Blueprint class for all the existing roles in the game
 * @see App
 */
abstract class Role {

    /**
     * name of the role
     */
    private var name : String? = null

    /**
     * name of the player
     */
    private var player : String? = null

    /**
     * description of the role
     */
    private var description : String? = null

    /**
     * team with which the player is sided.
     * three (3) teams are available : village, wolves and solos
     * @see App.TEAM_VILLAGE
     * @see App.TEAM_WOLVES
     * @see App.TEAM_SOLO
     */
    private var team : Int? = null

    /**
     * the number of times this role can use its primary ability
     */
    private var primaryAbilityPower : Int? = null

    /**
     * the number of times this role can use its secondary ability
     */
    private var secondaryAbilityPower : Int? = null

    /**
     * allow this role to use its primary ability
     * most roles has at least one ability
     */
    private var canUsePrimary :Boolean? = false

    /**
     * allow this role to use its secondary ability
     * only few roles has a second ability
     */
    private var canUseSecondary : Boolean? = false

    /**
     * indicates if the player is still playing or not
     */
    private var isAlive : Boolean? = true

    /**
     * indicates if the role is chosen by the servant or not
     */
    private var isServed : Boolean? = false

    /**
     * indicates if the role is protected by the guardian or not
     */
    private var isGuarded : Boolean? = false

    /**
     * indicates if the role was protected by the guardian or not
     * in the last night (turn)
     */
    private var wasGuarded : Boolean? = false

    /**
     * indicates if the role has received a fatal hit or not
     */
    private var isKilled : Boolean? = false

    /**
     * indicates if the role is infected by the father of wolves or not
     */
    private var isInfected : Boolean? = false

    /**
     * indicates if the role is the captain or not
     * a non-captain can receive the captain status
     * if he is chosen by the previous one (captain)
     */
    private var isCaptain : Boolean? = false

    /**
     * indicates if the role talks first when the discussion start
     */
    private var isTalking : Boolean? = false

    /**
     * primary ability of this role
     * @return return true if the operation was successful else false
     * @param role target role
     */
    abstract fun primaryAbility(role : Role) : Boolean

    /**
     * secondary ability of this role
     * @return return true if the operation was successful else false
     * @param role target role
     */
    abstract fun secondaryAbility(role : Role) : Boolean

    /**
     * action that will be executed just before the death
     * of the role
     * @param role possible target role
     */
    abstract fun onDeath(role : Role) : Boolean

    /**
     * indicates if the role can play this round or not
     * @param round : current night (round)
     * @return return true if the player can play
     */
    abstract fun canPlay(round : Int) : Boolean

    /**
     * indicates if the a specific role could be targeted or not
     * @param role role to check
     * @return true if role could be targeted, else false
     */
    abstract fun isATarget(role : Role) : Boolean

    /**
     * kill the role
     * @return return nothing
     * @see onDeath
     */
    fun die(){
        this.isAlive = false
    }

    /**
     * use the primary ability of this role
     * @param role target role
     * @return true if the operation executed successfully, else false
     * @see primaryAbilityPower
     * @see canUsePrimary
     * @see primaryAbility
     */
    fun usePrimaryAbility(role : Role) : Boolean{

        if (canUsePrimary == true){
            if (primaryAbility(role)){
                if (primaryAbilityPower == App.ABILITY_ONCE) canUsePrimary = false
                return true
            }
        }

        return false
    }

    /**
     * use the secondary ability of this role
     * @param role target role
     * @return true if the operation executed successfully, else false
     * @see secondaryAbilityPower
     * @see canUseSecondary
     * @see secondaryAbility
     */
    fun useSecondaryAbility(role : Role) : Boolean{

        if (canUseSecondary == true){
            if (primaryAbility(role)){
                if (secondaryAbilityPower == App.ABILITY_ONCE) canUseSecondary = false
                return true
            }
        }

        return false
    }

    /**
     * reset the status of this role when another night (round) starts
     * @see wasGuarded
     * @see isGuarded
     * @see isTalking
     * @see isInfected
     */
    fun resetStatusEffects(){

        wasGuarded = false

        if (isGuarded == true) wasGuarded = true

        isGuarded = false

        isTalking = false

        if (isInfected == true){

            if (team == App.TEAM_VILLAGE){
                team = App.TEAM_WOLVES
                isInfected = false
            }

        }

    }

}