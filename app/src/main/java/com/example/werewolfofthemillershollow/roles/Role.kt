package com.example.werewolfofthemillershollow.roles

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.StatusEffect
import java.io.Serializable

/**
 * Blueprint class for all the existing roles in the game
 * @see App
 */
abstract class Role : Serializable {

    /**
     * name of the role
     */
    var name : String = "role"

    /**
     * name of the player
     */
    var player : String? = null


    /**
     * description of the role
     */
    var description : String = "description"


    /**
     * team with which the player is sided.
     * three (3) teams are available : village, wolves and solos
     * @see getTeam
     * @see setTeam
     * @see App.TEAM_VILLAGE
     * @see App.TEAM_WOLVES
     * @see App.TEAM_SOLO
     */
    var team : Int? = null

    /**
     * the number of times this role can use its primary ability
     * @see getPrimaryType
     * @see setPrimaryType
     * @see App.ABILITY_NONE
     * @see App.ABILITY_ONCE
     * @see App.ABILITY_INFINITE
     */
    private var primaryType : Int? = null

    /**
     * getter for Role.primaryType
     * @return id of the power type from App
     * @see primaryType
     * @see setPrimaryType
     * @see App.ABILITY_NONE
     * @see App.ABILITY_ONCE
     * @see App.ABILITY_INFINITE
     */
    fun getPrimaryType() : Int? = primaryType

    /**
     * setter for Role.primaryType
     * @param primary id of the power type from App
     * @see primaryType
     * @see getPrimaryType
     * @see App.ABILITY_NONE
     * @see App.ABILITY_ONCE
     * @see App.ABILITY_INFINITE
     */
    fun setPrimaryType(primary : Int?) {
        this.primaryType = primary
    }

    /**
     * the number of times this role can use its secondary ability
     */
    private var secondaryType : Int? = null

    /**
     * getter for Role.secondaryType
     * @return id of the power type from App
     * @see secondaryType
     * @see setSecondaryType
     * @see App.ABILITY_NONE
     * @see App.ABILITY_ONCE
     * @see App.ABILITY_INFINITE
     */
    fun getSecondaryType() : Int? = secondaryType

    /**
     * setter for Role.secondaryType
     * @param secondary id of the power type from App
     * @see secondaryType
     * @see getSecondaryType
     * @see App.ABILITY_NONE
     * @see App.ABILITY_ONCE
     * @see App.ABILITY_INFINITE
     */
    fun setSecondaryType(secondary : Int?) {
        this.secondaryType = secondary
    }

    /**
     * allow this role to use its primary ability
     * most roles has at least one ability
     * @see getHasPrimary
     * @see setHasPrimary
     * @see App
     */
    private var hasPrimary :Boolean? = false

    /**
     * getter for Role.hasPrimary
     * @return the ability to use primary or not
     * @see setHasPrimary
     * @see App
     */
    fun getHasPrimary() : Boolean? = hasPrimary

    /**
     * setter for Role.hasPrimary
     * @param can new value
     * @see App
     */
    fun setHasPrimary(can : Boolean?) {
        hasPrimary = can
    }

    /**
     * allow this role to use its secondary ability
     * only few roles has a second ability
     * @see App
     */
    private var hasSecondary : Boolean? = false

    /**
     * getter for Role.hasSecondary
     * @return the ability to use secondary or not
     * @see App
     */
    fun getHasSecondary() : Boolean? = hasSecondary

    /**
     * setter for Role.hasSecondary
     * @param can new value
     * @see App
     */
    fun setHasSecondary(can : Boolean?) {
        hasSecondary = can
    }

    /**
     * determine whether this role can use its tertiary ability or no.
     */
    private var hasTertiary : Boolean = false

    /**
     * getter for Role.hasTertiary
     * @return (true) if this role has the ability, (false) otherwise.
     */
    fun getHasTertiary(): Boolean = hasSecondary!!

    /**
     * setter for Role.hasTertiary.
     * @param can new value.
     */
    fun setHasTertiary(can : Boolean){
        hasTertiary = can
    }

    /**
     * determine how many times can this role use its tertiary ability
     * @see hasTertiary
     */
    private var tertiaryType : Int = App.ABILITY_NONE

    /**
     * getter for Role.tertiaryType
     * @return (Boolean) true, false
     */
    fun getTertiaryType() : Int = tertiaryType

    /**
     * setter for Role.tertiaryType
     * @param power new value.
     */
    fun setTertiaryType(power : Int){
        tertiaryType = power
    }

    /**
     * indicates if the player is still playing or not
     * @see onDeath
     * @see die
     */
    var isAlive : Boolean = true

    /**
     * indicates if the role is chosen by the servantRef or not
     * @see Servant
     */
    var isServed : Boolean = false

    /**
     * indicates if the role is protected by the guardian or not
     * @see Guardian
     * @see wasGuarded
     */
    var isGuarded : Boolean = false

    /**
     * indicates if the role was protected by the guardian or not
     * in the last night (turn)
     * @see Guardian
     * @see isGuarded
     */
    var wasGuarded : Boolean = false

    /**
     * indicates if the role has received a fatal hit or not
     * @see isAlive
     */
    var isKilled : Boolean = false

    /**
     * indicates if the role is infected by the father of wolves or not
     * @see FatherOfWolves
     */
    var isInfected : Boolean = false

    /**
     * indicates if the role is the captainRef or not
     * a non-captainRef can receive the captainRef status
     * if he is chosen by the previous one (captainRef)
     * @see Captain
     */
    var isCaptain : Boolean = false

    /**
     * indicates if the role talks first when the discussion start
     */
    var isTalking : Boolean = false

    /**
     * Role icon
     */
    var icon : Int = Icons.person

    /**
     * Primary ability icon
     */
    private var primaryIcon : Int? = Icons.noAbility

    /**
     * getter for Role.primaryIcon
     * @return icon as id in res
     */
    fun getPrimaryIcon() : Int? = primaryIcon

    /**
     * setter for Role.primaryIcon
     * @param icon new icon id
     */
    fun setPrimaryIcon(icon : Int?) {
        this.primaryIcon = icon
    }

    /**
     * Secondary ability icon
     */
    private var secondaryIcon : Int? = Icons.noAbility

    /**
     * getter for Role.secondaryIcon
     * @return icon as id in res
     */
    fun getSecondaryIcon() : Int? = secondaryIcon

    /**
     * setter for Role.secondaryIcon.
     * @param icon new icon id
     */
    fun setSecondaryIcon(icon : Int?){
        this.secondaryIcon = icon
    }

    /**
     * Tertiary ability icon
     */
    private var tertiaryIcon : Int = Icons.noAbility

    /**
     * getter for Role.tertiaryIcon
     * @return icon as id in res
     */
    fun getTertiaryIcon() : Int = tertiaryIcon

    /**
     * setter for Role.tertiaryIcon.
     * @param icon new icon id
     */
    fun setTertiaryIcon(icon : Int){
        this.tertiaryIcon = icon
    }

    /**
     * determine how many player could be targeted with the primary ability.
     */
    private var primaryTargets : Int = App.TARGET_NONE

    /**
     * getter for Role.primaryTargets
     */
    fun getPrimaryTargets() : Int = primaryTargets

    /**
     * setter for Role.primaryTargets
     * @param targets new target rule
     */
    fun setPrimaryTargets(targets : Int){
        primaryTargets = targets
    }

    /**
     * determine how many player could be targeted with the secondary ability.
     */
    private var secondaryTargets : Int = App.TARGET_NONE

    /**
     * getter for Role.secondaryTargets
     */
    fun getSecondaryTargets() : Int = secondaryTargets

    /**
     * setter for Role.secondaryTargets
     * @param targets new target rule
     */
    fun setSecondaryTargets(targets : Int){
        secondaryTargets = targets
    }

    /**
     * determine how many player could be targeted with the tertiary ability.
     */
    private var tertiaryTargets: Int = App.TARGET_NONE

    /**
     * getter for Role.tertiaryTargets
     */
    fun getTertiaryTargets() : Int = tertiaryTargets

    /**
     * setter for Role.tertiaryTargets
     * @param targets new target rule
     */
    fun setTertiaryTargets(targets : Int){
        tertiaryTargets = targets
    }

    /**
     * primary ability of this role
     * @return return true if the operation was successful else false
     * @param role target role
     * @see hasPrimary
     * @see primaryType
     */
    abstract fun primaryAbility(role : Role) : Boolean

    /**
     * secondary ability of this role
     * @return return true if the operation was successful else false
     * @param role target role
     * @see hasSecondary
     * @see secondaryType
     */
    abstract fun secondaryAbility(role : Role) : Boolean

    /**
     * third ability of this role
     * @return return true if the operation was successful else false
     * @param role target role
     * @see hasTertiary
     * @see tertiaryType
     */
    open fun tertiaryAbility(role : Role): Boolean{
        return false
    }

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
     * indicates if the specified role could be targeted with the primary ability or not
     * @param role role to check
     * @return true if role could be targeted, else false
     */
    abstract fun isATargetPrimary(role : Role) : Boolean

    /**
     * indicates if the specified role could be targeted with the secondary ability or not.
     * @param role role to check
     * @return true if could be targeted, else false.
     */
    open fun isATargetSecondary(role : Role): Boolean{
        return false
    }

    /**
     * indicates if the specified role could be targeted with the tertiary ability or not.
     * @param role role to check
     * @return true if could be targeted, else false.
     */
    open fun isATargetTertiary(role: Role): Boolean{
        return false
    }
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
     * @see primaryType
     * @see hasPrimary
     * @see primaryAbility
     */
    fun usePrimaryAbility(role : Role) : Boolean{

        if (hasPrimary == true){
            if (primaryAbility(role)){
                if (primaryType == App.ABILITY_ONCE) hasPrimary = false
                return true
            }
        }

        return false
    }

    /**
     * use the secondary ability of this role
     * @param role target role
     * @return true if the operation executed successfully, else false
     * @see secondaryType
     * @see hasSecondary
     * @see secondaryAbility
     */
    fun useSecondaryAbility(role : Role) : Boolean{

        Log.d("Role","Role Class: Using secondary")

        if (hasSecondary == true){
            if (secondaryAbility(role)){
                Log.d("Role","Used Secondary")
                if (secondaryType == App.ABILITY_ONCE) hasSecondary = false

                Log.d("Role","Role Class: Used secondary")
                return true
            }
        }

        Log.d("Role","Role Class: Cannot use secondary")
        return false
    }

    /**
     * use the secondary ability of this role
     * @param role target role
     * @return true if the operation executed successfully, else false
     * @see tertiaryType
     * @see hasTertiary
     * @see tertiaryAbility
     */
    fun useTertiaryAbility(role : Role) : Boolean{

        if (hasTertiary){
            if (tertiaryAbility(role)){
                if (tertiaryType == App.ABILITY_ONCE) hasTertiary = false
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
    open fun resetStatusEffects(){

        wasGuarded = false

        if (isGuarded) wasGuarded = true

        isGuarded = false

        isTalking = false

        if (isInfected){
            if (team == App.TEAM_VILLAGE){
                team = App.TEAM_WOLVES
            }
        }

    }

    /**
     * copy the status effect from another player to this role.
     * * This method don't apply the "alive status".
     * @param role player to copy data from.
     */
    fun copyStatusEffects(role: Role){

        isServed = role.isServed
        isCaptain = role.isCaptain
        isKilled = role.isKilled
        isInfected = role.isInfected
        wasGuarded = role.wasGuarded
        isGuarded = role.isGuarded
        isTalking = role.isTalking

    }

    open fun new(context: Context, name: String, role: Role? = null): Role?{
        return null
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
     * Returns a list of status effects affecting this role.
     * @return array list of StatusEffect
     * @see StatusEffect
     */
    fun getStatusEffects(): ArrayList<StatusEffect>{

        val output = ArrayList<StatusEffect>()

        if (isServed){
            output.add(StatusEffect.servant())
        }

        if (isGuarded){
            output.add(StatusEffect.shield())
        }

        if (isInfected){
            output.add(StatusEffect.infection())
        }

        if (isCaptain){
            output.add(StatusEffect.captain())
        }

        return output
    }

    /**
     * make the appropriate changes to the game to and replace the servant with this role.
     * @param gameActivity game activity class
     * @return true if the operation succeeded, false otherwise.
     */
    open fun servant(gameActivity: GameActivity): Boolean{

        if (gameActivity.servantRef == null)
            return false

        val index = gameActivity.playerList.indexOf(gameActivity.servantRef)

        if (index == -1)
            return false

        val sub : Role = new(context = gameActivity, role = gameActivity.servantRef, name = gameActivity.servantRef!!.player!!)
            ?: return false

        gameActivity.playerList.removeAt(index)
        gameActivity.playerList.add(index, sub)

        gameActivity.servantRef = null
        isServed = false

        return true

    }

    /**
     * Display All Info about the object class
     * in the logcat for debug purpose.
     * @param name custom name to be given to the role (default is Role name)
     * @param tag custom tag (default is "DEBUG_ROLE")
     * @see Role
     * @see Log.d
     */
    fun debug(
        name : String = this.name,
        tag : String = "DEBUG_ROLE",
        ) {
        Log.d(tag,"$name Debug Start ----------------------------------------------")
        Log.d(tag,"$name Name : ${this.name}")
        Log.d(tag,"$name Player : ${this.player}")
        Log.d(tag,"$name Description : $description")
        Log.d(tag,"$name Team : ${this.team}")
        Log.d(tag,"$name Primary : ${getHasPrimary()}")
        Log.d(tag,"$name PrimaryPower : ${getPrimaryType()}")
        Log.d(tag,"$name Secondary : ${getHasSecondary()}")
        Log.d(tag,"$name SecondaryPower : ${getHasSecondary()}")
        if (isAlive) Log.d(tag,"$name isAlive : $isAlive")
        if (isKilled )Log.d(tag,"$name isKilled : $isKilled")
        if (isInfected) Log.d(tag,"$name isInfected : $isInfected")
        if (isServed) Log.d(tag,"$name isServed : $isServed")
        if (isGuarded) Log.d(tag,"$name isGuarded : $isGuarded")
        if (wasGuarded) Log.d(tag,"$name wasGuarded : $wasGuarded")
        if (isTalking) Log.d(tag,"$name isTalking : $isTalking")
        if (isCaptain) Log.d(tag,"$name isCaptain : $isCaptain")
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
         * value = (4)
         * @sample isListValid
         */
        const val FEW_PLAYERS : Int = 4

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
                FEW_PLAYERS -> return R.string.not_enough_player
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
         * @return index of the role. returns (-1) if not found.
         */
        fun roleInList(role : Role, list : ArrayList<Role>) : Int {

            for (r : Role in list){
                if (r.name == role.name) return list.indexOf(r)
            }

            return -1
        }

        /**
         * Delete a role from a list and return its position.
         * @param role role to be deleted
         * @param list list in which searching and deleting will be made
         * @return index of deleted item.
         */
        fun deleteRole(role : Role, list : ArrayList<Role>) : Int {

            for (r : Role in list){
                if (r.name == role.name) {
                    val index : Int = list.indexOf(r)
                    list.removeAt(index)
                    return index
                }
            }

            return -1

        }

        /**
         * Indicates if a captainRef exits in the given list of role or not.
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

                if (role.team == App.TEAM_VILLAGE && role.isAlive)
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

                if (role.team == App.TEAM_WOLVES && role.isAlive)
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

            if (list.size < 7)
                return FEW_PLAYERS

            if (!captainExists(list))
                return NO_CAPTAIN

            if (villagerNumber(list) == 0){
                return NO_VILLAGER
            }

            if (wolvesNumber(list) == 0){
                return NO_WOLVES
            }

            if (wolvesNumber(list) > list.size/3){
                return UNBALANCED_TEAMS
            }

            return NO_ERROR
        }

        /**
         * Fill the player names of a given list of roles with random uuid
         * @param list list to be filled with uuid
         */
        fun fillWithDummyNames(list : ArrayList<Role>) : ArrayList<Role>{

            for (role : Role in list){
                role.player = "${role.name} ${App.random(min = 1, max = 100)}"
            }

            return list

        }

    }
}