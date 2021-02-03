package com.example.werewolfofthemillershollow.utility
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.*
import com.example.werewolfofthemillershollow.settings.Icons

/**
 * Describe a status effect object.
 * @param icon status effect symbolic icon.
 * @param name status effect name.
 * @param description status effect description.
 * @see Role
 */
class StatusEffect(
    icon : Int,
    name : Int,
    description : Int, ) {

    /**
     * Icon representing the status effect.
     */
    private var icon : Int? = 0

    /**
     * Getter for StatusEffects.icon.
     * @return icon res id.
     */
    fun getIcon(): Int{
        return icon!!
    }

    /**
     * Getter for StatusEffects.icon.
     * @param context calling context.
     * @return icon drawable.
     */
    fun getIcon(context: Context): Drawable{
        return ContextCompat.getDrawable(context,icon!!)!!
    }

    /**
     * A descriptive name of the effect.
     */
    private var name : Int? = null

    /**
     * Getter for StatusEffects.name.
     * @return name res id.
     */
    fun getName(): Int{
        return name!!
    }

    /**
     * Getter for StatusEffects.name.
     * @param context calling context.
     * @return name as string.
     */
    fun getName(context: Context): String{
        return context.getString(name!!)
    }

    /**
     * Detailed information about the effect.
     */
    private var description : Int? = null

    /**
     * Getter for StatusEffects.description.
     * @return description res id.
     */
    fun getDescription():Int{
        return description!!
    }

    /**
     * Getter for StatusEffects.description.
     * @param context calling context.
     * @return description as string.
     */
    fun getDescription(context: Context): String{
        return context.getString(description!!)
    }

    init {
        this.icon = icon
        this.name = name
        this.description = description
    }

    companion object{

        /**
         * Return the status effect made by the servant.
         * @see Servant
         * @see StatusEffect
         */
        fun servant(): StatusEffect{
            return StatusEffect(
                icon = Icons.roll,
                name = R.string.servant_effect,
                description = R.string.servant_effect_description
            )
        }

        /**
         * Return the status effect made by the guardian.
         * @see Guardian
         * @see StatusEffect
         */
        fun shield(): StatusEffect{
            return StatusEffect(
                icon = Icons.info,
                name = R.string.guardian_effect,
                description = R.string.guardian_effect_description
            )
        }

        /**
         * Return the status effect made by the father of wolves.
         * @see FatherOfWolves
         * @see StatusEffect
         */
        fun infection(): StatusEffect{
            return StatusEffect(
                icon = Icons.moon,
                name = R.string.infection_effect,
                description = R.string.infection_effect_description
            )
        }

        /**
         * Return the status effect of the captain
         * @see Captain
         * @see StatusEffect
         */
        fun captain(): StatusEffect{
            return StatusEffect(
                icon = Icons.settings,
                name = R.string.captain_effect,
                description = R.string.captain_effect_description
            )
        }

    }
}