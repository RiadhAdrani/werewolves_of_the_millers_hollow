package com.example.werewolfofthemillershollow.util
import android.content.Context
import com.example.werewolfofthemillershollow.R
import com.example.werewolfofthemillershollow.roles.*

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
    var icon : Int? = 0

    /**
     * A descriptive name of the effect.
     */
    var name : Int? = null

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
    var description : Int? = null

    init {
        this.icon = icon
        this.name = name
        this.description = description
    }

    companion object{

        /**
         * Return the status effect made by the servantRef.
         * @see Servant
         * @see StatusEffect
         */
        fun servant(): StatusEffect {
            return StatusEffect(
                icon = Icons.servant,
                name = R.string.servant_effect,
                description = R.string.servant_effect_description
            )
        }

        /**
         * Return the status effect made by the guardian.
         * @see Guardian
         * @see StatusEffect
         */
        fun shield(): StatusEffect {
            return StatusEffect(
                icon = Icons.guardianProtect,
                name = R.string.guardian_effect,
                description = R.string.guardian_effect_description
            )
        }

        /**
         * Return the status effect made by the father of wolves.
         * @see FatherOfWolves
         * @see StatusEffect
         */
        fun infection(): StatusEffect {
            return StatusEffect(
                icon = Icons.infectInfect,
                name = R.string.infection_effect,
                description = R.string.infection_effect_description
            )
        }

        /**
         * Return the status effect of the captainRef
         * @see Captain
         * @see StatusEffect
         */
        fun captain(): StatusEffect {
            return StatusEffect(
                icon = Icons.captain,
                name = R.string.captain_effect,
                description = R.string.captain_effect_description
            )
        }

    }
}