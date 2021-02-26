package com.example.werewolfofthemillershollow.roles

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.settings.Icons
import com.example.werewolfofthemillershollow.utility.Ability
import com.example.werewolfofthemillershollow.utility.Event
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
     * three (3) teams are available : village, wolves and solo.
     * @see App.TEAM_VILLAGE
     * @see App.TEAM_WOLVES
     * @see App.TEAM_SOLO
     */
    var team : Int? = null

    /**
     * Primary ability of the current role.
     * @see Ability
     */
    var primaryAbility : Ability? = null

    /**
     * Secondary ability of the current role.
     * @see Ability
     */
    var secondaryAbility : Ability? = null

    /**
     * Tertiary ability of the current role.
     * @see Ability
     */
    var tertiaryAbility : Ability? = null

    /**
     * indicates if the player is still playing or not
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
     * indicates if the role can play this round or not
     * @param round current night (round)
     * @return return true if the player can play this round
     */
    abstract fun canPlay(round : Int) : Boolean

    /**
     * set the current player isKilled to true.
     * @param list the list of players.
     */
    open fun kill(list : ArrayList<Role>){
        isKilled = true
    }

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
        gameActivity.events.add(Event.servant(context = gameActivity, targetRole = name))

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
         */
        const val NO_ERROR : Int = -1

        /**
         * value = (0)
         */
        const val NO_CAPTAIN : Int = 0

        /**
         * value = (1)
         */
        const val NO_VILLAGER : Int = 1

        /**
         * value = (2)
         */
        const val NO_WOLVES : Int = 2

        /**
         * value = (3)
         */
        const val UNBALANCED_TEAMS : Int = 3

        /**
         * value = (4)
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