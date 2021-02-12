package com.example.werewolfofthemillershollow.utility

import android.content.Context
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.settings.Icons

/**
 * Contains an event that happened during the game.
 * @param icon icon to be displayed. By default it is set to "Icons.info"
 * @param message message to be displayed.
 */
class Event(
    private var icon : Int = Icons.info,
    private var message : String) {

    /**
     * getter of the icon.
     */
    fun getIcon(): Int = icon

    /**
     * getter of the message.
     */
    fun getMessage(): String = message

    companion object{

        /**
         * return an event made possible only be the servant.
         * @param context calling context.
         * @param targetRole name of the role.
         * @return event object.
         * @see Event
         */
        fun servant(context: Context, targetRole : String): Event{

            val msg = context.getString(R.string.servant_event) + " " + targetRole + "."
            return Event(icon = Icons.servantTake, message = msg)

        }

        /**
         * return an event describing the death of a player.
         * @param context calling context.
         * @param deadPlayer name of dead player.
         * @return event object.
         * @see Event
         */
        fun died(context: Context, deadPlayer : String): Event{

            val msg = deadPlayer + " " + context.getString(R.string.dead_event)
            return Event(icon = Icons.dead, message = msg)

        }

        /**
         * return an event describing the death of a player.
         * @param context calling context.
         * @param player name of the player.
         * @return event object.
         * @see Event
         */
        fun talkFirst(context: Context, player : String): Event{

            val msg = player + " " + context.getString(R.string.talk_first_event)
            return Event(icon = Icons.talkFirst, message = msg)

        }

        /**
         * return an event describing the seen role during the night.
         * @param context calling context.
         * @param role name of the role.
         * @return event object.
         * @see Event
         */
        fun seen(context: Context, role : String): Event{
            val msg = context.getString(R.string.seen_event) + " " + role + "."
            return Event(icon = Icons.seerSee, message = msg)
        }

    }

}