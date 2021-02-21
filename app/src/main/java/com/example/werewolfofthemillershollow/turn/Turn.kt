package com.example.werewolfofthemillershollow.turn

import android.content.Context
import android.util.Log
import com.example.werewolfofthemillershollow.GameActivity
import com.example.werewolfofthemillershollow.roles.Role
import com.example.werewolfofthemillershollow.settings.App
import com.example.werewolfofthemillershollow.utility.Ability
import com.example.werewolfofthemillershollow.utility.AlertDialog
import com.example.werewolfofthemillershollow.utility.TargetAdapter
import com.example.werewolfofthemillershollow.utility.UsePowerDialog

abstract class Turn<R : Role >(private var gameActivity: GameActivity) {

    private var role : R? = null

    /**
     * Return the instructions said by the game master when the current turn begins.
     * @param context calling context
     * @param list list of current alive players.
     * @return instruction as string
     */
    abstract fun getInstructions(context: Context, list : ArrayList<Role>? = null):String

    /**
     * Add this turn to the list containing game turns [output], if the current role needed for this this object
     * exists in the game [list].
     * @return return true if a Turn was added, otherwise false.
     */
    abstract fun addTurn(output : ArrayList<Turn<*>>, list: ArrayList<Role>, context: Context): Boolean

    /**
     * Make the needed changes to the servantRef.
     */
    abstract fun servant(activity: GameActivity): Int

    /**
     * Returns whether the current role turn is playable or not.
     * * Returning (true) means that the turn will be played normally.
     * * Returning (false) means that the turn will be skipped.
     * @param round current round
     * @param list list of alive players
     */
    abstract fun canPlay(round : Int, list : ArrayList<Role>? = null) : Boolean

    /**
     * Return whether the current role should use his power or not.
     */
    abstract fun shouldUsePower(gameActivity: GameActivity): Boolean

    /**
     * executes onSkip routine for this turn
     * @param activity activity, which should be [GameActivity].
     * @return true if the skip happened, false otherwise.
     */
    open fun onSkip(activity: GameActivity): Boolean{

        if (shouldUsePower(activity)){
            val dialog = AlertDialog(text = com.example.werewolfofthemillershollow.R.string.should_use_power)
            dialog.show(activity.supportFragmentManager,App.TAG_ALERT)
            return false
        } else {
            val onClick = object : AlertDialog.OnClick{
                override fun onClick(alertDialog: AlertDialog) {
                    alertDialog.dismiss()
                    activity.next()
                }

            }
            val dialog = AlertDialog(
                text = com.example.werewolfofthemillershollow.R.string.good_night,
                rightButton = onClick,
                cancelable = false)
            dialog.show(activity.supportFragmentManager,App.TAG_ALERT)
            return true
        }

    }

    /**
     * Returns the name to be displayed.
     */
    open fun getPlayer(list : ArrayList<Role>? = null): String{
        return role?.player!!
    }

    /**
     * Returns the displayed icon of the current role turn.
     */
    open fun getIcon(): Int{
        return role?.icon!!
    }

    /**
     * Return the name of the role to be displayed.
     */
    open fun getRoleToDisplay(context: Context? = null, list : ArrayList<Role>? = null): String{
        return role?.name!!
    }

    /**
     * Return the primary ability of this turn role
     */
    open fun getPrimaryAbility(): Ability? = getRole().primaryAbility

    /**
     * Return the secondary ability of this turn role
     */
    open fun getSecondaryAbility(): Ability? = getRole().secondaryAbility

    /**
     * Return the tertiary ability of this turn role
     */
    open fun getTertiaryAbility(): Ability? = getRole().tertiaryAbility

    /**
     * Return the ability executed on turn start.
     */
    open fun getOnStartAbility() : Ability? = null

    /**
     * Interface used to override the functionality of the fragment UsePowerDialog.
     * @see UsePowerDialog
     */
    open fun getOnClickHandler() : UsePowerDialog.OnClickListener{

        return object : UsePowerDialog.OnClickListener{
            override fun done(
                ability: Ability,
                aliveList: ArrayList<Role>,
                deadList: ArrayList<Role>,
                adapter: TargetAdapter,
                activity: GameActivity,
                dialog: UsePowerDialog?
            ) {
                if (adapter.getTargets().isEmpty()){
                    val alert = AlertDialog(text = com.example.werewolfofthemillershollow.R.string.should_use_power)
                    alert.show(activity.supportFragmentManager,App.TAG_ALERT)
                    return
                }

                Log.d("Role","Turn Class : using secondary ability")

                for(index : Int in adapter.getTargets()){

                    val target : Role = adapter.getList()[index]

                    val i = gameActivity.playerList.indexOf(target)

                    if (i == -1)
                        return

                    ability.use(self = getRole(), role = gameActivity.playerList[i])
                    gameActivity.playerList[i].debug()

                }

                val onClick = object : AlertDialog.OnClick{
                    override fun onClick(alertDialog: AlertDialog) {
                        alertDialog.dismiss()
                        dialog!!.dismiss()
                        activity.next()
                    }
                }

                val goodNightDialog = AlertDialog(
                    text = com.example.werewolfofthemillershollow.R.string.good_night,
                    rightButton = onClick,
                    cancelable = false)
                goodNightDialog.show(activity.supportFragmentManager,App.TAG_ALERT)

            }

            override fun reset(
                aliveList: ArrayList<Role>,
                deadList: ArrayList<Role>,
                adapter: TargetAdapter
            ) {
                adapter.emptyTargets()
            }

        }

    }

    /**
     * Interface used to handle clicking on targets in the fragment UsePowerDialog.
     * @see UsePowerDialog
     * @see TargetAdapter
     */
    open fun getOnTargetHandler() : TargetAdapter.OnClickListener{

        return object : TargetAdapter.OnClickListener{

            override fun onClick(
                ability: Ability,
                position: Int,
                dialog: UsePowerDialog,
                adapter: TargetAdapter
            ) {

                Log.d("TargetAdapter","Clicked on pos $position")

                if (ability.times == App.TARGET_NONE)
                    return

                if (position in adapter.getTargets()){
                    Log.d("TargetAdapter","item $position removed from target list.")
                    adapter.removeTarget(position)
                    if (adapter.getTargets().size == 0){
                        dialog.setCancelState()
                    }
                    Log.d("TargetAdapter","targets = ${adapter.getTargets()}")
                    return
                }

                if (ability.targets == App.TARGET_SINGLE){

                    if (adapter.getTargets().size > 0){
                        adapter.emptyTargets()
                    }

                    adapter.addTarget(position)
                    dialog.setResetState()
                    Log.d("TargetAdapter","item $position added to target list")
                    Log.d("TargetAdapter","targets = ${adapter.getTargets()}")
                    return
                }

                if (ability.targets == App.TARGET_TWO){

                    if (adapter.getTargets().size > 1){
                        adapter.getTargets().removeAt(0)
                    }

                    adapter.addTarget(position)
                    dialog.setResetState()
                    Log.d("TargetAdapter","item $position added to target list")
                    Log.d("TargetAdapter","targets = ${adapter.getTargets()}")
                }

            }

        }

    }

    /**
     * Executes after the role has been initialized in the game activity.
     */
    open fun onStart(activity : GameActivity): Boolean?{
        return false
    }


    /**
     * Interface used to handle clicking on buttons in the fragment UsePowerDialog
     * @see UsePowerDialog
     */
    open fun getOnStartOnClickHandler() : UsePowerDialog.OnClickListener?{
        return getOnClickHandler()
    }

    /**
     * Used to debug the current turn.
     * @param tag custom tag. By default, it is set to "Turn".
     * @param name custom name to be displayed. By default, it is set to [getRoleToDisplay].
     */
    open fun debug(tag : String = "Turn", name : String = getRoleToDisplay(gameActivity,gameActivity.playerList)){

        if (getPrimaryAbility() != null) {
            Log.d(tag,"$name : primary ability -> targets : ${getPrimaryAbility()!!.targets}")
            Log.d(tag,"$name : primary ability -> times : ${getPrimaryAbility()!!.times}")
            Log.d(tag,"$name : primary ability -> target list : ${getPrimaryAbility()!!.targetList(getRole(),gameActivity.playerList)}")
        } else {
            Log.d(tag,"$name : no primary ability")
        }

        if (getSecondaryAbility() != null) {
            Log.d(tag,"$name : secondary ability -> targets : ${getSecondaryAbility()!!.targets}")
            Log.d(tag,"$name : secondary ability -> times : ${getSecondaryAbility()!!.times}")
            Log.d(tag,"$name : secondary ability -> target list : ${getSecondaryAbility()!!.targetList(getRole(),gameActivity.playerList)}")
        } else {
            Log.d(tag,"$name : no secondary ability")
        }

        if (getOnStartAbility() != null) {
            Log.d(tag,"$name : start ability -> targets : ${getOnStartAbility()!!.targets}")
            Log.d(tag,"$name : start ability -> times : ${getOnStartAbility()!!.times}")
            Log.d(tag,"$name : start ability -> target list : ${getOnStartAbility()!!.targetList(getRole(),gameActivity.playerList)}")
        } else {
            Log.d(tag,"$name : no onStart ability")
        }

    }


    /**
     * getter for Turn.role
     */
    fun getRole() : R{
        return role!!
    }

    /**
     * setter for Turn.Role
     * @param role new role
     */
    fun setRole(role : R){
        this.role = role
    }


}