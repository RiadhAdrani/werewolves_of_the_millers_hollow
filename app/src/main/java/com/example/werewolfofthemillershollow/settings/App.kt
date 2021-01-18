package com.example.werewolfofthemillershollow.settings

import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.*


/**
 * contains settings and constants for the app.
 * Could be accessed globally
 */
abstract class App {

    companion object {

        // ABILITY
        /*** can use ability once*/
        const val ABILITY_ONCE : Int = 1

        /*** cannot use ability*/
        const val ABILITY_NONE : Int = 0

        /*** can use ability an infinite amount of time*/
        const val ABILITY_INFINITE : Int = -1

        // ---------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------

        /*** identifier for the village team*/
        const val TEAM_VILLAGE : Int = 0

        /*** identifier for the wolves team*/
        const val TEAM_WOLVES : Int = 1

        /*** identifier for the solo team*/
        const val TEAM_SOLO : Int = 2

        // ---------------------------------------------------------------------------------------
        // SERVANT
        // ---------------------------------------------------------------------------------------

        /*** Servant name resource id
         * @see Servant
         * */
        const val SERVANT_NAME : Int = R.string.servant_name

        /**
         * Servant description resource id
         * @see Servant
         * */
        const val SERVANT_DESCRIPTION : Int = R.string.servant_description

        /**
         * Servant team
         * @see Servant
         */
        const val SERVANT_TEAM : Int = TEAM_VILLAGE

        /**
         * Servant has a primary ability
         * @see Servant
         */
        const val SERVANT_CAN_PRIMARY : Boolean = true

        /**
         * Servant has a secondary ability
         * @see Servant
         */
        const val SERVANT_CAN_SECONDARY : Boolean = false

        /**
         * * Servant primary ability power
         * @see Servant
         * */
        const val SERVANT_PRIMARY_POWER : Int = ABILITY_ONCE

        /**
         * * Servant secondary ability power
         * @see Servant
         * */
        const val SERVANT_SECONDARY_POWER : Int = ABILITY_NONE

        // ---------------------------------------------------------------------------------------
        // GUARDIAN
        // ---------------------------------------------------------------------------------------

        /*** Guardian name resource id
         * @see Guardian
         * */
        const val GUARDIAN_NAME : Int = R.string.guardian_name

        /**
         * Guardian description resource id
         * @see Guardian
         * */
        const val GUARDIAN_DESCRIPTION : Int = R.string.guardian_description

        /**
         * Guardian team
         * @see Guardian
         */
        const val GUARDIAN_TEAM : Int = TEAM_VILLAGE

        /**
         * Guardian has a primary ability
         * @see Guardian
         */
        const val GUARDIAN_CAN_PRIMARY : Boolean = true

        /**
         * Guardian has a secondary ability
         * @see Guardian
         */
        const val GUARDIAN_CAN_SECONDARY : Boolean = false

        /**
         * * Guardian primary ability power
         * @see Guardian
         * */
        const val GUARDIAN_PRIMARY_POWER : Int = ABILITY_INFINITE

        /**
         * * Guardian secondary ability power
         * @see Guardian
         * */
        const val GUARDIAN_SECONDARY_POWER : Int = ABILITY_NONE

        // ---------------------------------------------------------------------------------------
        // WEREWOLF
        // ---------------------------------------------------------------------------------------

        /*** Werewolf name resource id
         * @see Werewolf
         * */
        const val WOLF_NAME : Int = R.string.wolf_name

        /**
         * Werewolf description resource id
         * @see Werewolf
         * */
        const val WOLF_DESCRIPTION : Int = R.string.wolf_description

        /**
         * Werewolf team
         * @see Werewolf
         */
        const val WOLF_TEAM : Int = TEAM_WOLVES

        /**
         * Werewolf has a primary ability
         * @see Werewolf
         */
        const val WOLF_CAN_PRIMARY : Boolean = false

        /**
         * Werewolf has a secondary ability
         * @see Werewolf
         */
        const val WOLF_CAN_SECONDARY : Boolean = false

        /**
         * * Werewolf primary ability power
         * @see Werewolf
         * */
        const val WOLF_PRIMARY_POWER : Int = ABILITY_NONE

        /**
         * * Werewolf secondary ability power
         * @see Werewolf
         * */
        const val WOLF_SECONDARY_POWER : Int = ABILITY_NONE

        // ---------------------------------------------------------------------------------------
        // INFECT
        // ---------------------------------------------------------------------------------------

        /*** Father of wolves name resource id
         * @see FatherOfWolves
         * */
        const val INFECT_NAME : Int = R.string.infect_name

        /**
         * Father of wolves description resource id
         * @see FatherOfWolves
         * */
        const val INFECT_DESCRIPTION : Int = R.string.infect_description

        /**
         * Father of wolves team
         * @see FatherOfWolves
         */
        const val INFECT_TEAM : Int = TEAM_WOLVES

        /**
         * Father of wolves has a primary ability
         * @see FatherOfWolves
         */
        const val INFECT_CAN_PRIMARY : Boolean = true

        /**
         * Father of wolves has a secondary ability
         * @see FatherOfWolves
         */
        const val INFECT_CAN_SECONDARY : Boolean = false

        /**
         * * Father of wolves primary ability power
         * @see FatherOfWolves
         * */
        const val INFECT_PRIMARY_POWER : Int = ABILITY_ONCE

        /**
         * * Father of wolves secondary ability power
         * @see FatherOfWolves
         * */
        const val INFECT_SECONDARY_POWER : Int = ABILITY_NONE

        // ---------------------------------------------------------------------------------------
        // SORCERER
        // ---------------------------------------------------------------------------------------

        /*** Sorcerer name resource id
         * @see Sorcerer
         * */
        const val SORCERER_NAME : Int = R.string.sorcerer_name

        /**
         * Sorcerer description resource id
         * @see Sorcerer
         * */
        const val SORCERER_DESCRIPTION : Int = R.string.sorcerer_description

        /**
         * Sorcerer team
         * @see Sorcerer
         */
        const val SORCERER_TEAM : Int = TEAM_VILLAGE

        /**
         * Sorcerer has a primary ability ?
         * @see Sorcerer
         */
        const val SORCERER_CAN_PRIMARY : Boolean = true

        /**
         * Sorcerer has a secondary ability ?
         * @see Sorcerer
         */
        const val SORCERER_CAN_SECONDARY : Boolean = true

        /**
         * * Sorcerer primary ability power
         * @see Sorcerer
         * */
        const val SORCERER_PRIMARY_POWER : Int = ABILITY_ONCE

        /**
         * * Sorcerer secondary ability power
         * @see Sorcerer
         * */
        const val SORCERER_SECONDARY_POWER : Int = ABILITY_NONE

        // ---------------------------------------------------------------------------------------
        // SEER
        // ---------------------------------------------------------------------------------------

        /*** Seer name resource id
         * @see Seer
         * */
        const val SEER_NAME : Int = R.string.seer_name

        /**
         * Seer description resource id
         * @see Seer
         * */
        const val SEER_DESCRIPTION : Int = R.string.seer_description

        /**
         * Seer team
         * @see Seer
         */
        const val SEER_TEAM : Int = TEAM_VILLAGE

        /**
         * Seer has a primary ability ?
         * @see Seer
         */
        const val SEER_CAN_PRIMARY : Boolean = true

        /**
         * Seer has a secondary ability ?
         * @see Seer
         */
        const val SEER_CAN_SECONDARY : Boolean = false

        /**
         * * Seer primary ability power
         * @see Seer
         * */
        const val SEER_PRIMARY_POWER : Int = ABILITY_INFINITE

        /**
         * * Seer secondary ability power
         * @see Seer
         * */
        const val SEER_SECONDARY_POWER : Int = ABILITY_NONE

        // ---------------------------------------------------------------------------------------
        // KNIGHT
        // ---------------------------------------------------------------------------------------

        /*** Knight name resource id
         * @see Knight
         * */
        const val KNIGHT_NAME : Int = R.string.knight_name

        /**
         * Knight description resource id
         * @see Knight
         * */
        const val KNIGHT_DESCRIPTION : Int = R.string.knight_description

        /**
         * Knight team
         * @see Knight
         */
        const val KNIGHT_TEAM : Int = TEAM_VILLAGE

        /**
         * Knight has a primary ability ?
         * @see Knight
         */
        const val KNIGHT_CAN_PRIMARY : Boolean = true

        /**
         * Knight has a secondary ability ?
         * @see Knight
         */
        const val KNIGHT_CAN_SECONDARY : Boolean = false

        /**
         * * Knight primary ability power
         * @see Knight
         * */
        const val KNIGHT_PRIMARY_POWER : Int = ABILITY_ONCE

        /**
         * * Knight secondary ability power
         * @see Knight
         * */
        const val KNIGHT_SECONDARY_POWER : Int = ABILITY_NONE

        // ---------------------------------------------------------------------------------------
        // BARBER
        // ---------------------------------------------------------------------------------------

        /*** Barber name resource id
         * @see Barber
         * */
        const val BARBER_NAME : Int = R.string.barber_name

        /**
         * Barber description resource id
         * @see Barber
         * */
        const val BARBER_DESCRIPTION : Int = R.string.barber_description

        /**
         * Barber team
         * @see Barber
         */
        const val BARBER_TEAM : Int = TEAM_VILLAGE

        /**
         * Barber has a primary ability ?
         * @see Barber
         */
        const val BARBER_CAN_PRIMARY : Boolean = true

        /**
         * Barber has a secondary ability ?
         * @see Barber
         */
        const val BARBER_CAN_SECONDARY : Boolean = false

        /**
         * * Barber primary ability power
         * @see Barber
         * */
        const val BARBER_PRIMARY_POWER : Int = ABILITY_ONCE

        /**
         * * Barber secondary ability power
         * @see Barber
         * */
        const val BARBER_SECONDARY_POWER : Int = ABILITY_NONE

        // ---------------------------------------------------------------------------------------
        // CAPTAIN
        // ---------------------------------------------------------------------------------------

        /*** Captain name resource id
         * @see Captain
         * */
        const val CAPTAIN_NAME : Int = R.string.captain_name

        /**
         * Captain description resource id
         * @see Captain
         * */
        const val CAPTAIN_DESCRIPTION : Int = R.string.captain_description

        /**
         * Captain team
         * @see Captain
         */
        const val CAPTAIN_TEAM : Int = TEAM_VILLAGE

        /**
         * Captain has a primary ability ?
         * @see Captain
         */
        const val CAPTAIN_CAN_PRIMARY : Boolean = true

        /**
         * Captain has a secondary ability ?
         * @see Captain
         */
        const val CAPTAIN_CAN_SECONDARY : Boolean = false

        /**
         * * Captain primary ability power
         * @see Captain
         * */
        const val CAPTAIN_PRIMARY_POWER : Int = ABILITY_INFINITE

        /**
         * * Captain secondary ability power
         * @see Captain
         * */
        const val CAPTAIN_SECONDARY_POWER : Int = ABILITY_NONE

        // ---------------------------------------------------------------------------------------
        // VILLAGER
        // ---------------------------------------------------------------------------------------

        /*** Villager name resource id
         * @see Villager
         * */
        const val VILLAGER_NAME : Int = R.string.villager_name

        /**
         * Villager description resource id
         * @see Villager
         * */
        const val VILLAGER_DESCRIPTION : Int = R.string.villager_description

        /**
         * Villager team
         * @see Villager
         */
        const val VILLAGER_TEAM : Int = TEAM_VILLAGE

        /**
         * Villager has a primary ability ?
         * @see Villager
         */
        const val VILLAGER_CAN_PRIMARY : Boolean = false

        /**
         * Villager has a secondary ability ?
         * @see Villager
         */
        const val VILLAGER_CAN_SECONDARY : Boolean = false

        /**
         * * Villager primary ability power
         * @see Villager
         * */
        const val VILLAGER_PRIMARY_POWER : Int = ABILITY_NONE

        /**
         * * Villager secondary ability power
         * @see Villager
         * */
        const val VILLAGER_SECONDARY_POWER : Int = ABILITY_NONE

        /**
         * Return a random number in a given interval
         * @param min min number of the interval
         * @param max max number of the interval
         * @return an integer in [min,max[
         */
        fun random(min : Int = 0,max : Int): Int {

            return (Math.random()*max).toInt() + min

        }

    }

}