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

    }

}