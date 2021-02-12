package com.example.werewolfofthemillershollow.settings

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

        const val continueGame : Int = R.drawable.ic_continue_game
        const val documentation : Int = R.drawable.ic_docs
        const val info : Int = R.drawable.ic_info
        const val back : Int = R.drawable.ic_return
        const val settings : Int = R.drawable.ic_settings
        const val start : Int = R.drawable.ic_start
        const val done : Int = R.drawable.ic_done
        const val roll : Int = R.drawable.ic_flip
        const val bloc : Int = R.drawable.ic_bloc
        const val more : Int = R.drawable.ic_more_options
        const val moon : Int = R.drawable.ic_moon
        const val alert : Int = R.drawable.ic_alert
        const val admin : Int = R.drawable.ic_shield
        const val animal : Int = R.drawable.ic_animal
        const val elixir : Int = R.drawable.ic_elixir
        const val clean : Int = R.drawable.ic_clean
        const val vip : Int = R.drawable.ic_vip
        const val person : Int = R.drawable.ic_person
        const val pack : Int = R.drawable.ic_animal
        const val goal : Int = R.drawable.ic_goal
        const val dead : Int = R.drawable.ic_no_person
        const val talkFirst : Int = R.drawable.ic_talk

        const val noAbility : Int = R.drawable.ic_bloc
        const val servantTake : Int = R.drawable.ic_flip
        const val guardianProtect : Int = R.drawable.ic_shield
        const val packAttack : Int = R.drawable.ic_no_person
        const val fatherInfect : Int = R.drawable.ic_infection
        const val sorcererHeal : Int = R.drawable.ic_health
        const val sorcererKill : Int = R.drawable.ic_no_person
        const val seerSee : Int = R.drawable.ic_eye
        const val knightShield : Int = R.drawable.ic_shield_2
        const val barberKill : Int = R.drawable.ic_no_person
        const val captainChoose : Int = R.drawable.ic_talk

    }

}