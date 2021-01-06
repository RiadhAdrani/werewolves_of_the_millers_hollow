package com.example.werewolfofthemillershollow.settings

import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Servant
import com.example.werewolfofthemillershollow.roles.Guardian
import com.example.werewolfofthemillershollow.roles.Werewolf
import com.example.werewolfofthemillershollow.roles.FatherOfWolves


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
         * @see Guardian
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

    }

}