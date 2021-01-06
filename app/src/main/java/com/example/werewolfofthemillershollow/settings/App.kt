package com.example.werewolfofthemillershollow.settings

import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Servant

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
        // ---------------------------------------------------------------------------------------

    }

}