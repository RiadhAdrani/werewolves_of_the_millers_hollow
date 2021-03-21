package com.example.werewolfofthemillershollow.settings

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.Role

/**
 * contains settings and constants for the app.
 * Could be accessed globally
 */
abstract class App : AppCompatActivity(){

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
        const val SERVANT_ICON : Int = R.drawable.ic_ww_servant

        // ---------------------------------------------------------------------------------------
        // GUARDIAN
        // ---------------------------------------------------------------------------------------

        const val GUARDIAN_NAME : Int = R.string.guardian_name
        const val GUARDIAN_DESCRIPTION : Int = R.string.guardian_description
        const val GUARDIAN_TEAM : Int = TEAM_VILLAGE
        const val GUARDIAN_ICON : Int = R.drawable.ic_ww_guardian

        // ---------------------------------------------------------------------------------------
        // WEREWOLF
        // ---------------------------------------------------------------------------------------

        const val WOLF_NAME : Int = R.string.wolf_name
        const val WOLF_DESCRIPTION : Int = R.string.wolf_description
        const val WOLF_TEAM : Int = TEAM_WOLVES
        const val WOLF_ICON : Int = R.drawable.ic_ww_wolf

        // ---------------------------------------------------------------------------------------
        // INFECT
        // ---------------------------------------------------------------------------------------

        const val INFECT_NAME : Int = R.string.infect_name
        const val INFECT_DESCRIPTION : Int = R.string.infect_description
        const val INFECT_TEAM : Int = TEAM_WOLVES
        const val INFECT_ICON : Int = R.drawable.ic_ww_infect


        // ---------------------------------------------------------------------------------------
        // SORCERER
        // ---------------------------------------------------------------------------------------

        const val SORCERER_NAME : Int = R.string.sorcerer_name
        const val SORCERER_DESCRIPTION : Int = R.string.sorcerer_description
        const val SORCERER_TEAM : Int = TEAM_VILLAGE
        const val SORCERER_ICON : Int = R.drawable.ic_ww_sorcerer

        // ---------------------------------------------------------------------------------------
        // SEER
        // ---------------------------------------------------------------------------------------

        const val SEER_NAME : Int = R.string.seer_name
        const val SEER_DESCRIPTION : Int = R.string.seer_description
        const val SEER_TEAM : Int = TEAM_VILLAGE
        const val SEER_ICON : Int = R.drawable.ic_ww_seer

        // ---------------------------------------------------------------------------------------
        // KNIGHT
        // ---------------------------------------------------------------------------------------

        const val KNIGHT_NAME : Int = R.string.knight_name
        const val KNIGHT_DESCRIPTION : Int = R.string.knight_description
        const val KNIGHT_TEAM : Int = TEAM_VILLAGE
        const val KNIGHT_ICON : Int = R.drawable.ic_ww_knight

        // ---------------------------------------------------------------------------------------
        // BARBER
        // ---------------------------------------------------------------------------------------

        const val BARBER_NAME : Int = R.string.barber_name
        const val BARBER_DESCRIPTION : Int = R.string.barber_description
        const val BARBER_TEAM : Int = TEAM_VILLAGE
        const val BARBER_ICON : Int = R.drawable.ic_ww_hunter

        // ---------------------------------------------------------------------------------------
        // CAPTAIN
        // ---------------------------------------------------------------------------------------

        const val CAPTAIN_NAME : Int = R.string.captain_name
        const val CAPTAIN_DESCRIPTION : Int = R.string.captain_description
        const val CAPTAIN_TEAM : Int = TEAM_VILLAGE
        const val CAPTAIN_ICON : Int = R.drawable.ic_ww_captain

        // ---------------------------------------------------------------------------------------
        // VILLAGER
        // ---------------------------------------------------------------------------------------

        const val VILLAGER_NAME : Int = R.string.villager_name
        const val VILLAGER_DESCRIPTION : Int = R.string.villager_description
        const val VILLAGER_TEAM : Int = TEAM_VILLAGE
        const val VILLAGER_ICON : Int = R.drawable.ic_ww_villager

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

        /**
         * Return a well formatted string containing the list of players.
         * @param list list of players.
         * @param context calling context.
         */
        fun listToString(list : ArrayList<Role>, context: Context): String{
            var output = ""
            for (role : Role in list){
                if (list.indexOf(role)==0){
                    output += "${role.player} "
                    continue
                }
                if (list.indexOf(role) == list.size-1){
                    output += " ${context.getString(R.string.and)} ${role.player}"
                    continue
                }
                output += ", ${role.player}"
            }
            return output
        }

        /**
         * Make an icon blink indefinitely.
         * @param icon target icon.
         * @param color blinking color.
         * @param duration animation duration in milliseconds.
         */
        fun blink(icon : ImageView, color : Int, duration : Long){

            val animator = ObjectAnimator.ofInt(
                icon,
                "backgroundColor",
                color,
                )

            animator.duration = duration
            animator.setEvaluator(ArgbEvaluator())
            animator.repeatMode = ValueAnimator.REVERSE
            animator.repeatCount = ValueAnimator.INFINITE
            animator.start()

        }

    }

}