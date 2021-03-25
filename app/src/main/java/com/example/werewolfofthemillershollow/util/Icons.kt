package com.example.werewolfofthemillershollow.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.werewolfofthemillershollow.R

/**
 * Allow the access to the database icons.
 */
abstract class Icons {

    companion object{

        /**
         * Return an icon as drawable
         * @param icon drawable id
         * @param context calling context
         * @return drawable
         */
        fun getDrawableIcon(icon : Int,context: Context) : Drawable?{
            return ContextCompat.getDrawable(context,icon)
        }

        const val info : Int = R.drawable.ic_info
        const val settings : Int = R.drawable.ic_settings
        const val start : Int = R.drawable.ic_start
        const val done : Int = R.drawable.ic_done
        const val roll : Int = R.drawable.ic_flip
        const val person : Int = R.drawable.ic_person
        const val goal : Int = R.drawable.ic_goal
        const val dead : Int = R.drawable.ic_no_person
        const val talkFirst : Int = R.drawable.ic_talk
        const val unknown : Int = R.drawable.ic_unknown

        const val discussion = R.drawable.ic_ww_discussion
        const val execute = R.drawable.ic_ww_execute
        const val moon = R.drawable.ic_ww_moon
        const val servant = R.drawable.ic_ww_servant
        const val servantServe = R.drawable.ic_ww_servant_serve
        const val guardian = R.drawable.ic_ww_guardian
        const val guardianProtect = R.drawable.ic_ww_guardian_protect
        const val wolves = R.drawable.ic_ww_wolf
        const val infect = R.drawable.ic_ww_infect
        const val infectInfect = R.drawable.ic_ww_infect_infect
        const val sorcerer = R.drawable.ic_ww_sorcerer
        const val sorcererKill = R.drawable.ic_ww_sorcerer_kill
        const val sorcererRevive = R.drawable.ic_ww_sorcerer_revive
        const val seer = R.drawable.ic_ww_seer
        const val seerSee = R.drawable.ic_ww_seer_see
        const val hunter = R.drawable.ic_ww_hunter
        const val hunterKill = R.drawable.ic_ww_hunter_kill
        const val knight = R.drawable.ic_ww_knight
        const val knightKill = R.drawable.ic_ww_knight_kill
        const val captain = R.drawable.ic_ww_captain
        const val captainDiscuss = R.drawable.ic_ww_captain_discuss
        const val captainKill = R.drawable.ic_ww_captain_kill
        const val captainNew = R.drawable.ic_ww_captain_new
        const val noAbility : Int = R.drawable.ic_bloc

    }

}