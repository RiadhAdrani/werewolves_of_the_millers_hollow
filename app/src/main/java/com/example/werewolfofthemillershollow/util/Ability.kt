package com.example.werewolfofthemillershollow.util

import android.util.Log
import com.example.werewolfofthemillershollow.roles.Role
import java.io.Serializable

/**
 * Class describe ability object.
 * @param specifications indicates the specific usage of this ability.
 * @param times indicates how many times could this ability be used.
 * the value of times can only be on of these values [App.ABILITY_NONE], [App.ABILITY_ONCE] or [App.ABILITY_INFINITE].
 * If the value of [times] is different from the values mentioned above, unexpected errors could happen.
 * @param targets indicates how many players could be targeted with this ability.
 * The value of targets can only be on of these value: [App.TARGET_NONE], [App.TARGET_SINGLE], [App.TARGET_TWO], [App.TARGET_MULTIPLE].
 * If the value of [targets] is different from the values mentioned above, unexpected errors could happen.
 * @param icon ability icon. recommended to get the icon from [Icons]
 * @see Role
 */
class Ability(
    var name : Int,
    private var specifications : Specification,
    var times : Int,
    var targets : Int,
    var icon: Int): Serializable {

    /**
     * Interface for custom ability specification.
     */
    interface Specification : Serializable {

        /**
         * Use the ability.
         * @param role target player.
         * @return true if everything was alright, false if the operation failed for some reason.
         */
        fun use(self : Role, role : Role, list : ArrayList<Role>): Boolean

        /**
         * indicates if the current role could be used at the current state or not.
         */
        fun isUsable(): Boolean

        /**
         * indicates if the specified role is a target or not.
         * @return true or false.
         */
        fun isTarget(self : Role, targetRole : Role): Boolean
    }

    /**
     * allow to use this ability.
     * @param role target player.
     * @return true if everything was alright, false if the operation failed for some reason.
     */
    fun use(self : Role, role : Role, list : ArrayList<Role>): Boolean{
        if (times == App.ABILITY_NONE)
            return false

        if (times == App.ABILITY_ONCE){
            val value = specifications.use(self, role, list)
            Log.d("GameLogs","Ability used on ${role.player}")
            return if (value){
                times = App.ABILITY_NONE
                true
            } else {
                false
            }
        }

        if (times == App.ABILITY_INFINITE)
            return specifications.use(self, role, list)

        return false
    }

    /**
     * indicates if this ability could be used at the moment or not.
     */
    fun isUsable(): Boolean{
        return specifications.isUsable()
    }

    /**
     * indicates if the specified role is a target or not.
     * @param role role to check.
     * @return true or false.
     */
    fun isTarget(self : Role, role : Role): Boolean{
        return specifications.isTarget(self, role)
    }

    /**
     * filter a list of role to suit this ability.
     * @param list list of roles.
     * @return list of filtered roles.
     */
    fun targetList(self: Role, list : ArrayList<Role>): ArrayList<Role>{

        val output = ArrayList<Role>()

        for (role : Role in list){
            if (isTarget(self, role))
                output.add(role)
        }

        return output

    }

}