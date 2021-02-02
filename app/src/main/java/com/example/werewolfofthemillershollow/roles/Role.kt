package com.example.werewolfofthemillershollow.roles

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons

/**
 * Blueprint class for all the existing roles in the game
 * @see App
 * @sample Sorcerer
 * @sample Werewolf
 */
abstract class Role {

    /**
     * name of the role
     * @see getName
     * @see setName
     */
    private var name : String? = null

    /**
     * getter for Role.name
     * @return name of the role
     * @see name
     * @see setName
     */
    fun getName() : String? = name

    /**
     * setter for Role.name
     * @see name
     * @param name new name of the role
     * @see getName
     */
    fun setName(name : String?) {
        this.name = name
    }

    /**
     * name of the player
     * @see getPlayer
     * @see setPlayer
     */
    private var player : String? = null

    /**
     * getter for Role.player
     * @return name of the player
     * @see player
     * @see setPlayer
     */
    fun getPlayer() : String? = player

    /**
     * setter for Role.player
     * @param name new name of the player
     * @see player
     * @see getPlayer
     */
    fun setPlayer(name : String?) {
        this.player = name
    }

    /**
     * description of the role
     * @see getDescription
     * @see setDescription
     */
    private var description : String? = null

    /**
     * getter for Role.description
     * @return the text of the description
     * @see description
     * @see setDescription
     */
    fun getDescription() : String? = description

    /**
     * setter for Role.description
     * @param description new description
     * @see description
     * @see getDescription
     */
    fun setDescription(description : String?) {
        this.description = description
    }

    /**
     * team with which the player is sided.
     * three (3) teams are available : village, wolves and solos
     * @see getTeam
     * @see setTeam
     * @see App.TEAM_VILLAGE
     * @see App.TEAM_WOLVES
     * @see App.TEAM_SOLO
     */
    private var team : Int? = null

    /**
     * getter for Role.team
     * @return id of the current team
     * @see setTeam
     * @see App.TEAM_VILLAGE
     * @see App.TEAM_WOLVES
     * @see App.TEAM_SOLO
     */
    fun getTeam() : Int? = team

    /**
     * setter for Role.team
     * @param team new team
     * @see getTeam
     * @see App.TEAM_VILLAGE
     * @see App.TEAM_WOLVES
     * @see App.TEAM_SOLO
     */
    fun setTeam(team : Int?) {
        this.team = team
    }

    /**
     * the number of times this role can use its primary ability
     * @see getPrimaryAbilityPower
     * @see setPrimaryAbilityPower
     * @see App.ABILITY_NONE
     * @see App.ABILITY_ONCE
     * @see App.ABILITY_INFINITE
     */
    private var primaryAbilityPower : Int? = null

    /**
     * getter for Role.primaryAbilityPower
     * @return id of the power type from App
     * @see primaryAbilityPower
     * @see setPrimaryAbilityPower
     * @see App.ABILITY_NONE
     * @see App.ABILITY_ONCE
     * @see App.ABILITY_INFINITE
     */
    fun getPrimaryAbilityPower() : Int? = primaryAbilityPower

    /**
     * setter for Role.primaryAbilityPower
     * @param primary id of the power type from App
     * @see primaryAbilityPower
     * @see getPrimaryAbilityPower
     * @see App.ABILITY_NONE
     * @see App.ABILITY_ONCE
     * @see App.ABILITY_INFINITE
     */
    fun setPrimaryAbilityPower(primary : Int?) {
        this.primaryAbilityPower = primary
    }

    /**
     * the number of times this role can use its secondary ability
     */
    private var secondaryAbilityPower : Int? = null

    /**
     * getter for Role.secondaryAbilityPower
     * @return id of the power type from App
     * @see secondaryAbilityPower
     * @see setSecondaryAbilityPower
     * @see App.ABILITY_NONE
     * @see App.ABILITY_ONCE
     * @see App.ABILITY_INFINITE
     */
    fun getSecondaryAbilityPower() : Int? = secondaryAbilityPower

    /**
     * setter for Role.secondaryAbilityPower
     * @param secondary id of the power type from App
     * @see secondaryAbilityPower
     * @see getSecondaryAbilityPower
     * @see App.ABILITY_NONE
     * @see App.ABILITY_ONCE
     * @see App.ABILITY_INFINITE
     */
    fun setSecondaryAbilityPower(secondary : Int?) {
        this.secondaryAbilityPower = secondary
    }

    /**
     * allow this role to use its primary ability
     * most roles has at least one ability
     * @see getCanUsePrimary
     * @see setCanUsePrimary
     * @see App
     */
    private var canUsePrimary :Boolean? = false

    /**
     * getter for Role.canUsePrimary
     * @return the ability to use primary or not
     * @see setCanUsePrimary
     * @see App
     */
    fun getCanUsePrimary() : Boolean? = canUsePrimary

    /**
     * setter for Role.canUsePrimary
     * @param can new value
     * @see getCanUsePrimary
     * @see App
     */
    fun setCanUsePrimary(can : Boolean?) {
        canUsePrimary = can
    }

    /**
     * allow this role to use its secondary ability
     * only few roles has a second ability
     * @see getCanUseSecondary
     * @see setCanUseSecondary
     * @see App
     */
    private var canUseSecondary : Boolean? = false

    /**
     * getter for Role.canUseSecondary
     * @return the ability to use secondary or not
     * @see canUseSecondary
     * @see setCanUseSecondary
     * @see App
     */
    fun getCanUseSecondary() : Boolean? = canUseSecondary

    /**
     * setter for Role.canUseSecondary
     * @param can new value
     * @see canUseSecondary
     * @see getCanUseSecondary
     * @see App
     */
    fun setCanUseSecondary(can : Boolean?) {
        canUseSecondary = can
    }

    /**
     * indicates if the player is still playing or not
     * @see getIsAlive
     * @see setIsAlive
     * @see onDeath
     * @see die
     */
    private var isAlive : Boolean? = true

    /**
     * getter for Role.isAlive
     * @return isAlive
     * @see isAlive
     * @see setIsAlive
     */
    fun getIsAlive() : Boolean? = isAlive

    /**
     * setter for Role.isAlive
     * @param alive new value
     * @see isAlive
     * @see getIsAlive
     */
    fun setIsAlive(alive : Boolean?) {
        isAlive = alive
    }


    /**
     * indicates if the role is chosen by the servant or not
     * @see Servant
     * @see getIsServed
     * @see setIsServed
     */
    private var isServed : Boolean? = false

    /**
     * getter for Role.isServed
     * @return isServed
     * @see isServed
     * @see setIsServed
     * @see Servant
     */
    fun getIsServed() : Boolean? = isServed

    /**
     * setter for Role.isServed
     * @param served new value
     * @see isServed
     * @see getIsServed
     * @see Servant
     */
    fun setIsServed(served : Boolean?) {
        isServed = served
    }

    /**
     * indicates if the role is protected by the guardian or not
     * @see Guardian
     * @see wasGuarded
     * @see getIsGuarded
     * @see setIsGuarded
     */
    private var isGuarded : Boolean? = false

    /**
     * getter for Role.isGuarded
     * @return isGuarded
     * @see isGuarded
     * @see setIsGuarded
     * @see Guardian
     */
    fun getIsGuarded() : Boolean? = isGuarded

    /**
     * setter for Role.isGuarded
     * @param guarded new value
     * @see isGuarded
     * @see getIsGuarded
     * @see Guardian
     */
    fun setIsGuarded(guarded : Boolean?) {
        isGuarded = guarded
    }

    /**
     * indicates if the role was protected by the guardian or not
     * in the last night (turn)
     * @see Guardian
     * @see isGuarded
     * @see getWasGuarded
     * @see setWasGuarded
     */
    private var wasGuarded : Boolean? = false

    /**
     * getter for Role.wasGuarded
     * @return wasGuarded
     * @see wasGuarded
     * @see setWasGuarded
     * @see Guardian
     */
    fun getWasGuarded() : Boolean? = wasGuarded

    /**
     * setter for Role.wasGuarded
     * @param wasGuarded new value
     * @see wasGuarded
     * @see getWasGuarded
     * @see Guardian
     */
    fun setWasGuarded(wasGuarded : Boolean?) {
        this.wasGuarded = wasGuarded
    }


    /**
     * indicates if the role has received a fatal hit or not
     * @see isAlive
     * @see getIsKilled
     * @see setIsKilled
     */
    private var isKilled : Boolean? = false

    /**
     * getter for Role.isKilled
     * @return isKilled
     * @see isKilled
     * @see setIsKilled
     */
    fun getIsKilled() : Boolean? = isKilled

    /**
     * setter for Role.isKilled
     * @param killed new value
     * @see isKilled
     * @see getIsKilled
     */
    fun setIsKilled(killed : Boolean?) {
        this.isKilled = killed
    }

    /**
     * indicates if the role is infected by the father of wolves or not
     * @see getIsInfected
     * @see setIsInfected
     * @see FatherOfWolves
     */
    private var isInfected : Boolean? = false

    /**
     * getter for Role.isInfected
     * @return isInfected
     * @see isInfected
     * @see setIsInfected
     * @see FatherOfWolves
     */
    fun getIsInfected() : Boolean? = isInfected

    /**
     * setter for Role.isInfected
     * @param infect new value
     * @see isInfected
     * @see getIsInfected
     * @see FatherOfWolves
     */
    fun setIsInfected(infect : Boolean?) {
        this.isInfected = infect
    }


    /**
     * indicates if the role is the captain or not
     * a non-captain can receive the captain status
     * if he is chosen by the previous one (captain)
     * @see Captain
     * @see getIsCaptain
     * @see setIsCaptain
     */
    private var isCaptain : Boolean? = false

    /**
     * getter for Role.isCaptain
     * @return isCaptain
     * @see isCaptain
     * @see setIsCaptain
     * @see Captain
     */
    fun getIsCaptain() : Boolean? = isCaptain

    /**
     * setter for Role.isCaptain
     * @param captain new value
     * @see isInfected
     * @see getIsCaptain
     * @see Captain
     */
    fun setIsCaptain(captain : Boolean?) {
        this.isCaptain = captain
    }

    /**
     * indicates if the role talks first when the discussion start
     */
    private var isTalking : Boolean? = false

    /**
     * getter for Role.isTalking
     * @return isTalking
     * @see isTalking
     * @see setIsTalking
     * @see Captain
     */
    fun getIsTalking() : Boolean? = isTalking

    /**
     * setter for Role.isTalking
     * @param talk new value
     * @see isTalking
     * @see getIsTalking
     * @see Captain
     */
    fun setIsTalking(talk : Boolean?) {
        this.isTalking = talk
    }

    /**
     * Role icon
     */
    private var icon : Int? = Icons.info

    /**
     * getter for Role.icon
     * @return icon
     */
    fun getIcon() : Int? = icon

    /**
     * setter for Role.icon
     * @param icon new icon
     */
    fun setIcon(icon : Int?) {
        this.icon = icon
    }

    /**
     * primary ability of this role
     * @return return true if the operation was successful else false
     * @param role target role
     * @see canUsePrimary
     * @see primaryAbilityPower
     */
    abstract fun primaryAbility(role : Role) : Boolean

    /**
     * secondary ability of this role
     * @return return true if the operation was successful else false
     * @param role target role
     * @see canUseSecondary
     * @see secondaryAbilityPower
     */
    abstract fun secondaryAbility(role : Role) : Boolean

    /**
     * action that will be executed just before the death
     * of the role
     * @param role possible target role
     * @see die
     * @see isAlive
     */
    abstract fun onDeath(role : Role) : Boolean

    /**
     * indicates if the role can play this round or not
     * @param round current night (round)
     * @return return true if the player can play this round
     */
    abstract fun canPlay(round : Int) : Boolean

    /**
     * indicates if the specified role could be targeted or not
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

    /**
     * Return whereas the role is a wolf or not
     */
    open fun isWolf():Boolean {
        return false
    }

    /**
     * Return whether the role can be represented by only one or many players.
     */
    open fun isUnique() : Boolean{
        return false
    }

    /**
     * Display All Info about the object class
     * in the logcat for debug purpose.
     * @param name custom name to be given to the role (default is Role name)
     * @param tag custom tag (default is "DEBUG_ROLE")
     * @see Role
     * @see Log.d
     */
    fun debug(name : String? = getName(), tag : String = "DEBUG_ROLE") {
        Log.d(tag,"$name Debug Start ----------------------------------------------")
        Log.d(tag,"$name Name : ${getName()}")
        Log.d(tag,"$name Description : ${getDescription()}")
        Log.d(tag,"$name Team : ${getName()}")
        Log.d(tag,"$name Primary : ${getCanUsePrimary()}")
        Log.d(tag,"$name PrimaryPower : ${getPrimaryAbilityPower()}")
        Log.d(tag,"$name Secondary : ${getCanUseSecondary()}")
        Log.d(tag,"$name SecondaryPower : ${getCanUseSecondary()}")
        Log.d(tag,"$name isAlive : ${getIsAlive()}")
        Log.d(tag,"$name isKilled : ${getIsKilled()}")
        Log.d(tag,"$name isInfected : ${getIsInfected()}")
        Log.d(tag,"$name isServed : ${getIsServed()}")
        Log.d(tag,"$name isGuarded : ${getIsGuarded()}")
        Log.d(tag,"$name wasGuarded : ${getWasGuarded()}")
        Log.d(tag,"$name isTalking : ${getIsTalking()}")
        Log.d(tag,"$name isCaptain : ${getIsCaptain()}")
        Log.d(tag,"Debug End ----------------------------------------------")
    }

    companion object {

        /**
         * value = (-1)
         * @sample isListValid
         */
        const val NO_ERROR : Int = -1

        /**
         * value = (0)
         * @sample isListValid
         */
        const val NO_CAPTAIN : Int = 0

        /**
         * value = (1)
         * @sample isListValid
         */
        const val NO_VILLAGER : Int = 1

        /**
         * value = (2)
         * @sample isListValid
         */
        const val NO_WOLVES : Int = 2

        /**
         * value = (3)
         * @sample isListValid
         */
        const val UNBALANCED_TEAMS : Int = 3

        /**
         * Returns the error message depending on its id.
         * @see NO_WOLVES
         * @see NO_VILLAGER
         * @see NO_CAPTAIN
         * @see UNBALANCED_TEAMS
         * @param error error id
         * @return returns a string id from the resource
         */
        fun errorMessage(error : Int): Int{

            when (error){
                NO_CAPTAIN -> return R.string.no_captain
                NO_VILLAGER -> return R.string.no_villager
                NO_WOLVES -> return R.string.no_wolves
                UNBALANCED_TEAMS -> return R.string.unbalanced_teams
            }

            return R.string.no_string

        }

        /**
         * Return a list of all the roles available
         * @param context calling context
         * @return array list of Role
         */
        fun getRoles(context: Context) : ArrayList<Role> {

            val list = ArrayList<Role>()

            list.add(Servant(context))
            list.add(Guardian(context))
            list.add(Werewolf(context))
            list.add(FatherOfWolves(context))
            list.add(Sorcerer(context))
            list.add(Seer(context))
            list.add(Knight(context))
            list.add(Barber(context))
            list.add(Captain(context))
            list.add(Villager(context))

            return list
        }

        /**
         * Return a list of specified roles depending on the number provided
         * @param context calling context
         * @param numberOfRoles number of roles needed
         * @return array list of Role
         */
        fun getRoles(context: Context, numberOfRoles : Int) : ArrayList<Role>{

            val list = ArrayList<Role>()

            if (numberOfRoles > 8) list.add(Servant(context))
            list.add(Guardian(context))
            if (numberOfRoles > 7) list.add(Werewolf(context))
            list.add(FatherOfWolves(context))
            list.add(Sorcerer(context))
            list.add(Seer(context))
            if (numberOfRoles > 9) list.add(Knight(context))
            list.add(Barber(context))
            list.add(Captain(context))
            list.add(Villager(context))

            return list
        }

        /**
         * Return whether the role exists in the list or not
         * @param role role to be found
         * @param list list in which searching will be made
         */
        fun isRoleInList(role : Role, list : ArrayList<Role>) : Boolean {

            for (r : Role in list){
                if (r.getName().equals(role.getName())) return true
            }

            return false
        }

        /**
         * Delete a role from a list and return its position.
         * @param role role to be deleted
         * @param list list in which searching and deleting will be made
         * @return index of deleted item.
         */
        fun deleteRole(role : Role, list : ArrayList<Role>) : Int {

            for (r : Role in list){
                if (r.getName().equals(role.getName())) {
                    val index : Int = list.indexOf(r)
                    list.removeAt(index)
                    return index
                }
            }

            return -1

        }

        /**
         * Indicates if a captain exits in the given list of role or not.
         * @param list list to be checked
         */
        fun captainExists(list : ArrayList<Role>) : Boolean{

            for (role : Role in list){
                if (role.isCaptain!!){
                    return true
                }
            }

            return false
        }

        /**
         * Indicates the number of player in the village team.
         * @param list list to be checked
         */
        fun villagerNumber(list : ArrayList<Role>) : Int {

            var number = 0

            for (role : Role in list){

                if (role.team == App.TEAM_VILLAGE)
                    number ++
            }

            return number

        }

        /**
         * Indicates the number of player in the village team.
         * @param list list to be checked
         */
        fun wolvesNumber(list : ArrayList<Role>) : Int {

            var number = 0

            for (role : Role in list){

                if (role.team == App.TEAM_WOLVES)
                    number ++
            }

            return number

        }

        /**
         * Indicates if a list of roles is valid for a game or not.
         * @param list list to be checked
         * @see NO_CAPTAIN
         * @see NO_VILLAGER
         * @see NO_WOLVES
         * @see UNBALANCED_TEAMS
         * @return returns multiple values depending the type of issue. Returns -1 in case of no problem.
         */
        fun isListValid(list : ArrayList<Role>) : Int{

            if (!captainExists(list))
                return NO_CAPTAIN

            if (villagerNumber(list) == 0){
                return NO_VILLAGER
            }

            if (wolvesNumber(list) == 0){
                return NO_WOLVES
            }

            if (wolvesNumber(list) == villagerNumber(list)){
                return UNBALANCED_TEAMS
            }

            return NO_ERROR
        }

    }
}