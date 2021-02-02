package com.example.werewolfofthemillershollow.turn

import android.content.Context
import com.example.werewolfofthemillershollow.roles.Role
import kotlin.collections.ArrayList

abstract class Turn<R : Role> {

    /**
     * Return the instructions said by the game master when the current turn begins.
     * @param context calling context
     * @param list list of current alive players.
     * @return instruction as string
     */
    abstract fun getInstructions(context: Context, list : ArrayList<Role>? = null):String

    private var role : R? = null

    /**
     * getter for Turn.role
     */
    fun getRole() : R{
        return role!!
    }

    fun setRole(role : R){
        this.role = role
    }

    /**
     * Returns whether the current role turn is playable or not.
     * Returning (true) means that the turn will be played normally.
     * Returning (false) means that the turn will be skipped.
     * @param round current round
     * @param list list of alive players
     */
    abstract fun canPlay(round : Int, list : ArrayList<Role>? = null) : Boolean

    /**
     * Allow the use of the primary ability of the role.
     * @param singleTarget only used when the ability supports only one target.
     * @param multipleTargets only used when the ability supports multiple targets to affect.
     * @return (true) if the target was successfully affected and can use his ability, (false) otherwise.
     */
    abstract fun usePrimary(singleTarget : Role? = null, multipleTargets : ArrayList<Role>? = null): Boolean

    /**
     * Allow the use of the secondary ability of the role.
     * @param singleTarget only used when the ability supports only one target.
     * @param multipleTargets only used when the ability supports multiple targets to affect.
     * @return (true) if the target was successfully affected and can use his ability, (false) otherwise.
     */
    abstract fun useSecondary(singleTarget : Role? = null, multipleTargets : ArrayList<Role>? = null): Boolean

    /**
     * Returns a list of targets
     * @param list of roles to be extracted from.
     */
    fun getTargets(list : ArrayList<Role>) : ArrayList<Role>{

        val output = ArrayList<Role>()

        for (r : Role in list){
            if (role!!.isATarget(r))
                output.add(r)
        }

        return output
    }

}