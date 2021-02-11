package com.example.werewolfofthemillershollow.settings

import com.example.werewolfofthemillershollow.R

/**
 * contains settings and constants for the app.
 * Could be accessed globally
 */
abstract class App {

    companion object {

        // TAGs

        /**
         * Alert dialog tag
         */
        const val TAG_ALERT : String = "ALERT_DIALOG"

        // ---------------------------------------------------------------------------------------

        /*** can use ability once*/
        const val ABILITY_ONCE : Int = 1

        /*** cannot use ability*/
        const val ABILITY_NONE : Int = 0

        /*** can use ability an infinite amount of time*/
        const val ABILITY_INFINITE : Int = -1

        /*** identifier for the village team*/
        const val TEAM_VILLAGE : Int = 0

        /*** identifier for the wolves team*/
        const val TEAM_WOLVES : Int = 1

        /*** identifier for the solo team*/
        const val TEAM_SOLO : Int = 2

        /** No target ability.*/
        const val TARGET_NONE : Int = 0

        /** Single target ability. Only one target can be affected.*/
        const val TARGET_SINGLE : Int = 1

        /** Two target ability. Only two targets could be affected.*/
        const val TARGET_TWO : Int = 2

        /** Multiple target ability. More than two targets could be affected.*/
        const val TARGET_MULTIPLE : Int = 100

        // ---------------------------------------------------------------------------------------
        // SERVANT
        // ---------------------------------------------------------------------------------------

        const val SERVANT_NAME : Int = R.string.servant_name
        const val SERVANT_DESCRIPTION : Int = R.string.servant_description
        const val SERVANT_TEAM : Int = TEAM_VILLAGE
        const val SERVANT_ICON : Int = Icons.servantTake
        const val SERVANT_PRIMARY_POWER : Int = ABILITY_ONCE
        const val SERVANT_CAN_PRIMARY : Boolean = true
        const val SERVANT_CAN_SECONDARY : Boolean = false
        const val SERVANT_SECONDARY_POWER : Int = ABILITY_NONE
        const val SERVANT_PRIMARY_TARGET : Int = TARGET_SINGLE
        const val SERVANT_PRIMARY_ICON : Int = Icons.servantTake

        // ---------------------------------------------------------------------------------------
        // GUARDIAN
        // ---------------------------------------------------------------------------------------

        const val GUARDIAN_NAME : Int = R.string.guardian_name
        const val GUARDIAN_DESCRIPTION : Int = R.string.guardian_description
        const val GUARDIAN_TEAM : Int = TEAM_VILLAGE
        const val GUARDIAN_CAN_PRIMARY : Boolean = true
        const val GUARDIAN_CAN_SECONDARY : Boolean = false
        const val GUARDIAN_PRIMARY_POWER : Int = ABILITY_INFINITE
        const val GUARDIAN_SECONDARY_POWER : Int = ABILITY_NONE
        const val GUARDIAN_PRIMARY_TARGET : Int = TARGET_SINGLE
        const val GUARDIAN_ICON : Int = Icons.guardianProtect
        const val GUARDIAN_PRIMARY_ICON : Int = Icons.guardianProtect

        // ---------------------------------------------------------------------------------------
        // WEREWOLF
        // ---------------------------------------------------------------------------------------

        const val WOLF_NAME : Int = R.string.wolf_name
        const val WOLF_DESCRIPTION : Int = R.string.wolf_description
        const val WOLF_TEAM : Int = TEAM_WOLVES
        const val WOLF_CAN_PRIMARY : Boolean = false
        const val WOLF_CAN_SECONDARY : Boolean = false
        const val WOLF_PRIMARY_POWER : Int = ABILITY_NONE
        const val WOLF_SECONDARY_POWER : Int = ABILITY_NONE
        const val WOLF_ICON : Int = Icons.animal

        // ---------------------------------------------------------------------------------------
        // INFECT
        // ---------------------------------------------------------------------------------------

        const val INFECT_NAME : Int = R.string.infect_name
        const val INFECT_DESCRIPTION : Int = R.string.infect_description
        const val INFECT_TEAM : Int = TEAM_WOLVES
        const val INFECT_CAN_PRIMARY : Boolean = true
        const val INFECT_CAN_SECONDARY : Boolean = false
        const val INFECT_PRIMARY_POWER : Int = ABILITY_ONCE
        const val INFECT_SECONDARY_POWER : Int = ABILITY_NONE
        const val INFECT_PRIMARY_TARGET : Int = TARGET_SINGLE
        const val INFECT_ICON : Int = Icons.fatherInfect
        const val INFECT_PRIMARY_ICON = Icons.fatherInfect

        // ---------------------------------------------------------------------------------------
        // SORCERER
        // ---------------------------------------------------------------------------------------

        const val SORCERER_NAME : Int = R.string.sorcerer_name
        const val SORCERER_DESCRIPTION : Int = R.string.sorcerer_description
        const val SORCERER_TEAM : Int = TEAM_VILLAGE
        const val SORCERER_CAN_PRIMARY : Boolean = true
        const val SORCERER_CAN_SECONDARY : Boolean = true
        const val SORCERER_PRIMARY_POWER : Int = ABILITY_ONCE
        const val SORCERER_SECONDARY_POWER : Int = ABILITY_ONCE
        const val SORCERER_PRIMARY_TARGET : Int = TARGET_SINGLE
        const val SORCERER_SECONDARY_TARGET : Int = TARGET_SINGLE
        const val SORCERER_ICON : Int = Icons.elixir
        const val SORCERER_PRIMARY_ICON : Int = Icons.sorcererKill
        const val SORCERER_SECONDARY_ICON : Int = Icons.sorcererHeal


        // ---------------------------------------------------------------------------------------
        // SEER
        // ---------------------------------------------------------------------------------------

        const val SEER_NAME : Int = R.string.seer_name
        const val SEER_DESCRIPTION : Int = R.string.seer_description
        const val SEER_TEAM : Int = TEAM_VILLAGE
        const val SEER_CAN_PRIMARY : Boolean = true
        const val SEER_CAN_SECONDARY : Boolean = false
        const val SEER_PRIMARY_POWER : Int = ABILITY_INFINITE
        const val SEER_SECONDARY_POWER : Int = ABILITY_NONE
        const val SEER_PRIMARY_TARGET : Int = TARGET_SINGLE
        const val SEER_ICON : Int = Icons.seerSee
        const val SEER_PRIMARY_ICON = Icons.seerSee

        // ---------------------------------------------------------------------------------------
        // KNIGHT
        // ---------------------------------------------------------------------------------------

        const val KNIGHT_NAME : Int = R.string.knight_name
        const val KNIGHT_DESCRIPTION : Int = R.string.knight_description
        const val KNIGHT_TEAM : Int = TEAM_VILLAGE
        const val KNIGHT_CAN_PRIMARY : Boolean = true
        const val KNIGHT_CAN_SECONDARY : Boolean = false
        const val KNIGHT_PRIMARY_POWER : Int = ABILITY_ONCE
        const val KNIGHT_SECONDARY_POWER : Int = ABILITY_NONE
        const val KNIGHT_PRIMARY_TARGET : Int = TARGET_SINGLE
        const val KNIGHT_ICON : Int = Icons.knightShield
        const val KNIGHT_PRIMARY_ICON : Int  = Icons.knightShield

        // ---------------------------------------------------------------------------------------
        // BARBER
        // ---------------------------------------------------------------------------------------

        const val BARBER_NAME : Int = R.string.barber_name
        const val BARBER_DESCRIPTION : Int = R.string.barber_description
        const val BARBER_TEAM : Int = TEAM_VILLAGE
        const val BARBER_CAN_PRIMARY : Boolean = true
        const val BARBER_CAN_SECONDARY : Boolean = false
        const val BARBER_PRIMARY_POWER : Int = ABILITY_ONCE
        const val BARBER_SECONDARY_POWER : Int = ABILITY_NONE
        const val BARBER_PRIMARY_TARGET : Int = TARGET_SINGLE
        const val BARBER_ICON : Int = Icons.clean
        const val BARBER_PRIMARY_ICON : Int = Icons.barberKill

        // ---------------------------------------------------------------------------------------
        // CAPTAIN
        // ---------------------------------------------------------------------------------------

        const val CAPTAIN_NAME : Int = R.string.captain_name
        const val CAPTAIN_DESCRIPTION : Int = R.string.captain_description
        const val CAPTAIN_TEAM : Int = TEAM_VILLAGE
        const val CAPTAIN_CAN_PRIMARY : Boolean = true
        const val CAPTAIN_CAN_SECONDARY : Boolean = false
        const val CAPTAIN_PRIMARY_POWER : Int = ABILITY_INFINITE
        const val CAPTAIN_SECONDARY_POWER : Int = ABILITY_NONE
        const val CAPTAIN_PRIMARY_TARGET : Int = TARGET_SINGLE
        const val CAPTAIN_ICON : Int = Icons.vip
        const val CAPTAIN_PRIMARY_ICON : Int = Icons.captainChoose

        // ---------------------------------------------------------------------------------------
        // VILLAGER
        // ---------------------------------------------------------------------------------------

        const val VILLAGER_NAME : Int = R.string.villager_name
        const val VILLAGER_DESCRIPTION : Int = R.string.villager_description
        const val VILLAGER_TEAM : Int = TEAM_VILLAGE
        const val VILLAGER_CAN_PRIMARY : Boolean = false
        const val VILLAGER_CAN_SECONDARY : Boolean = false
        const val VILLAGER_PRIMARY_POWER : Int = ABILITY_NONE
        const val VILLAGER_SECONDARY_POWER : Int = ABILITY_NONE
        const val VILLAGER_ICON : Int = Icons.person

        // ---------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------

        /**
         * Return a random number in a given interval
         * @param min min number of the interval, (0) is the default value
         * @param max max number of the interval
         * @return an integer in [min,max[
         */
        fun random(min : Int = 0, max : Int = 1): Int {

            return (Math.random()*max).toInt() + min

        }

    }

}